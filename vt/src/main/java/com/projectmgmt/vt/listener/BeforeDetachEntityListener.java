package com.projectmgmt.vt.listener;


import com.projectmgmt.vt.domain.Entity;
import jakarta.persistence.EntityManager;


/**
 * Defines the contract for handling entities right before they are detached from an EntityManager
 * on transaction commit.
 */
public interface BeforeDetachEntityListener<T extends Entity> {

  /**
   * Executes before the object is detached from an EntityManager on transaction commit.
   *
   * @param entity entity instance in managed state
   * @param entityManager EntityManager that owns the entity instance
   */
  void onBeforeDetach(T entity, EntityManager entityManager);
}