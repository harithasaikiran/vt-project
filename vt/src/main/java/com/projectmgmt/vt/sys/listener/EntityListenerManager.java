package com.projectmgmt.vt.sys.listener;

import com.projectmgmt.vt.annotation.Listeners;
import com.projectmgmt.vt.domain.Entity;
import com.projectmgmt.vt.listener.*;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This bean allows to register and fire entity listeners. <p>Usually entity listeners are
 * registered declaratively with {@code @Listeners} annotation on entity class. Methods {@link
 * #addListener(Class, Class)} and {@link #addListener(Class, String)} allow to add listeners
 * dynamically, e.g. to an entity from a base project.
 */
@Component(EntityListenerManager.NAME)
public class EntityListenerManager {

  public static final String NAME = "sys_EntityListenerManager";

  protected static class Key {

    private final Class entityClass;
    private final EntityListenerType type;

    public Key(Class entityClass, EntityListenerType type) {
      this.entityClass = entityClass;
      this.type = type;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Key key = (Key) o;

      if (!entityClass.equals(key.entityClass)) {
        return false;
      }
      if (type != key.type) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result;
      result = entityClass.hashCode();
      result = 31 * result + type.hashCode();
      return result;
    }
  }

  protected static class ListenerExecution {

    private final Entity entity;
    private final EntityListenerType type;

    public ListenerExecution(Entity entity, EntityListenerType type) {
      this.entity = entity;
      this.type = type;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ListenerExecution that = (ListenerExecution) o;
      return entity == that.entity && type == that.type;
    }

    @Override
    public int hashCode() {
      return Objects.hash(entity, type);
    }

    @Override
    public String toString() {
      return type + ": " + entity;
    }
  }

  private static final Logger log = LoggerFactory.getLogger(EntityListenerManager.class);

  @Autowired
  protected EntityManager entityManager;

  protected ApplicationContext applicationContext;

  protected Map<Key, List> cache = new ConcurrentHashMap<>();

  protected Map<Class<? extends Entity>, Set<String>> dynamicListeners = new ConcurrentHashMap<>();

  protected ReadWriteLock lock = new ReentrantReadWriteLock();

  protected volatile boolean enabled = true;

  protected ThreadLocal<List<ListenerExecution>> threadLocalExecutions = new ThreadLocal<>();

  /**
   * Register an entity listener by its class. The listener instance will be instantiated as a plain
   * object.
   *
   * @param entityClass entity
   * @param listenerClass listener class
   */
  public void addListener(Class<? extends Entity> entityClass, Class<?> listenerClass) {
    lock.writeLock().lock();
    try {
      Set<String> set = dynamicListeners.get(entityClass);
      if (set == null) {
        set = new HashSet<>();
        dynamicListeners.put(entityClass, set);
      }
      set.add(listenerClass.getName());

      cache.clear();
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * Unregister an entity listener.
   *
   * @param entityClass entity
   * @param listenerClass listener class
   */
  public void removeListener(Class<? extends Entity> entityClass, Class<?> listenerClass) {
    lock.writeLock().lock();
    try {
      Set<String> set = dynamicListeners.get(entityClass);
      if (set != null) {
        set.remove(listenerClass.getName());
      }

      cache.clear();
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * Register an entity listener which is a ManagedBean.
   *
   * @param entityClass entity
   * @param listenerBeanName listener bean name
   */
  public void addListener(Class<? extends Entity> entityClass, String listenerBeanName) {
    lock.writeLock().lock();
    try {
      Set<String> set = dynamicListeners.get(entityClass);
      if (set == null) {
        set = new HashSet<>();
        dynamicListeners.put(entityClass, set);
      }
      set.add(listenerBeanName);

      cache.clear();
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * Unregister an entity listener.
   *
   * @param entityClass entity
   * @param listenerBeanName listener bean name
   */
  public void removeListener(Class<? extends Entity> entityClass, String listenerBeanName) {
    lock.writeLock().lock();
    try {
      Set<String> set = dynamicListeners.get(entityClass);
      if (set != null) {
        set.remove(listenerBeanName);
      }

      cache.clear();
    } finally {
      lock.writeLock().unlock();
    }
  }

  @SuppressWarnings("unchecked")
  public void fireListener(Entity entity, EntityListenerType type, String storeName) {
    if (!enabled) {
      return;
    }

    List listeners = getListener(entity.getClass(), type);
    if (listeners.isEmpty()) {
      return;
    }

    // check if a listener for this instance is already executed
    List<ListenerExecution> executions = threadLocalExecutions.get();
    if (executions == null) {
      executions = new ArrayList<>();
      threadLocalExecutions.set(executions);
    }
    ListenerExecution execution = new ListenerExecution(entity, type);
    if (executions.contains(execution)) {
      return;
    } else {
      executions.add(execution);
    }

    try {
      Session hibernateSession = entityManager.unwrap(Session.class);
      for (Object listener : listeners) {
        switch (type) {
          case BEFORE_DETACH:
            logExecution(type, entity);
            ((BeforeDetachEntityListener) listener).onBeforeDetach(entity, entityManager);
            break;
          case BEFORE_ATTACH:
            logExecution(type, entity);
            ((BeforeAttachEntityListener) listener).onBeforeAttach(entity);
            break;
          case BEFORE_INSERT:
            logExecution(type, entity);
            ((BeforeInsertEntityListener) listener).onBeforeInsert(entity, entityManager);
            break;
          case AFTER_INSERT:
            logExecution(type, entity);

            hibernateSession.doWork(new org.hibernate.jdbc.Work() {

              @Override
              public void execute(Connection connection) throws SQLException {
                // do whatever you need to do with the connection
                ((AfterInsertEntityListener) listener).onAfterInsert(entity, connection);
              }
            });
            break;
          case BEFORE_UPDATE:
            logExecution(type, entity);
            ((BeforeUpdateEntityListener) listener).onBeforeUpdate(entity, entityManager);
            break;
          case AFTER_UPDATE:
            logExecution(type, entity);

            hibernateSession.doWork(new org.hibernate.jdbc.Work() {

              @Override
              public void execute(Connection connection) throws SQLException {
                // do whatever you need to do with the connection
                ((AfterUpdateEntityListener) listener).onAfterUpdate(entity, connection);
              }
            });
            break;
          case BEFORE_DELETE:
            logExecution(type, entity);
            ((BeforeDeleteEntityListener) listener).onBeforeDelete(entity, entityManager);
            break;
          case AFTER_DELETE:
            logExecution(type, entity);
            hibernateSession.doWork(new org.hibernate.jdbc.Work() {

              @Override
              public void execute(Connection connection) throws SQLException {
                // do whatever you need to do with the connection
                ((AfterDeleteEntityListener) listener).onAfterDelete(entity, connection);
              }
            });
            break;
          default:
            throw new UnsupportedOperationException("Unsupported EntityListenerType: " + type);
        }
      }
    } finally {
      executions.remove(execution);
      if (executions.isEmpty()) {
        threadLocalExecutions.remove();
      }
    }
  }

  public void enable(boolean enable) {
    this.enabled = enable;
  }

  protected void logExecution(EntityListenerType type, Entity entity) {
    if (log.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Executing ").append(type).append(" entity listener for ")
          .append(entity.getClass().getName()).append(" id=").append(entity.getId());
      /*
      if (type != EntityListenerType.BEFORE_DETACH && type != EntityListenerType.BEFORE_ATTACH) {
        Set<String> dirty = entityManager.getMetamodel()..getDirtyFields(entity);
        if (!dirty.isEmpty()) {
          sb.append(", changedProperties: ");
          for (Iterator<String> it = dirty.iterator(); it.hasNext(); ) {
            String field = it.next();
            sb.append(field);
            if (it.hasNext())
              sb.append(",");
          }
        }
      }*/
      log.debug(sb.toString());
    }
  }

  protected List<?> getListener(Class<? extends Entity> entityClass, EntityListenerType type) {
    Key key = new Key(entityClass, type);

    lock.readLock().lock();
    try {
      if (!cache.containsKey(key)) {
        List listeners = findListener(entityClass, type);
        cache.put(key, listeners);
        return listeners;
      } else {
        return cache.get(key);
      }
    } finally {
      lock.readLock().unlock();
    }
  }

  protected List<?> findListener(Class<? extends Entity> entityClass, EntityListenerType type) {
    log.trace("get listener {} for class {}", type, entityClass.getName());
    List<String> names = getDeclaredListeners(entityClass);
    if (names.isEmpty()) {
      log.trace("no annotations, exiting");
      return Collections.emptyList();
    }

    List<Object> result = new ArrayList<>();
    for (String name : names) {
      if (applicationContext.containsBean(name)) {
        Object bean = applicationContext.getBean(name);
        log.trace("listener bean found: {}", bean);
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(bean.getClass());
        for (Class intf : interfaces) {
          if (intf.equals(type.getListenerInterface())) {
            log.trace("listener implements {}", type.getListenerInterface());
            result.add(bean);
          }
        }
      } else {
        try {
          Class aClass = Thread.currentThread().getContextClassLoader().loadClass(name);
          log.trace("listener class found: {}", aClass);
          List<Class<?>> interfaces = ClassUtils.getAllInterfaces(aClass);
          for (Class intf : interfaces) {
            if (intf.equals(type.getListenerInterface())) {
              log.trace("listener implements {}", type.getListenerInterface());
              result.add(aClass.newInstance());
            }
          }
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(String.format(
              "Unable to create entity listener %s for %s because there is no bean or class with such name",
              name, entityClass.getName()));

        } catch (IllegalAccessException | InstantiationException e) {
          throw new RuntimeException("Unable to instantiate an Entity Listener", e);
        }
      }
    }
    return result;
  }

  protected List<String> getDeclaredListeners(Class<? extends Entity> entityClass) {
    List<String> listeners = new ArrayList<>();

    List<Class<?>> superclasses = ClassUtils.getAllSuperclasses(entityClass);
    Collections.reverse(superclasses);
    for (Class superclass : superclasses) {
      Set<String> set = dynamicListeners.get(superclass);
      if (set != null) {
        listeners.addAll(set);
      }

      Listeners annotation = (Listeners) superclass.getAnnotation(Listeners.class);
      if (annotation != null) {
        listeners.addAll(Arrays.asList(annotation.value()));
      }
    }

    Set<String> set = dynamicListeners.get(entityClass);
    if (set != null) {
      listeners.addAll(set);
    }

    Listeners annotation = entityClass.getAnnotation(Listeners.class);
    if (annotation != null) {
      listeners.addAll(Arrays.asList(annotation.value()));
    }

    return listeners;
  }
}