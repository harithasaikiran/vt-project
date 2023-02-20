package com.projectmgmt.vt.domain;


import com.projectmgmt.vt.domain.global.UuidProvider;
import com.projectmgmt.vt.domain.util.BeanUtil;
import org.springframework.data.domain.AuditorAware;

import jakarta.persistence.*;
import java.util.Date;

/**
 * A generic entity listener for tracking and logging events related to persistent entities.
 *
 * @author Mounir MESSAOUDI
 */
public class EntityListener {

  @PrePersist
  public void prePersist(Object entity) {
    AuditorAware auditorAware = BeanUtil.getBean(AuditorAware.class);
    Date ts = new Date();

    if (entity instanceof HasUuid) {
      ((HasUuid) entity).setUuid(UuidProvider.createUuid());
    }

    if (entity instanceof Creatable) {
      //@fixme
      ((Creatable) entity).setCreatedBy((String) auditorAware.getCurrentAuditor().orElse("anonymoususer"));
      ((Creatable) entity).setCreateTs(ts);
    }
    if (entity instanceof Updatable) {
      ((Updatable) entity).setUpdateTs(ts);
    }
  }

  protected boolean justDeleted(SoftDelete entity) {
    return entity.isDeleted();
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    AuditorAware auditorAware = BeanUtil.getBean(AuditorAware.class);

    if (!((entity instanceof SoftDelete) && justDeleted((SoftDelete) entity)) && (entity instanceof Updatable)) {
      Updatable updatable = (Updatable) entity;
      //@fixme
      updatable.setUpdatedBy((String) auditorAware.getCurrentAuditor().orElse("anonymoususer"));
      updatable.setUpdateTs(new Date());
    }
  }
}
