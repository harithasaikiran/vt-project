package com.projectmgmt.vt.listener;

import com.projectmgmt.vt.domain.Entity;
import jakarta.persistence.EntityManager;


/**
 * Defines the contract for handling of entities before they have been deleted or marked as deleted
 * in DB.
 */
public interface BeforeDeleteEntityListener<T extends Entity> {

  /**
   * Executes before the object has been deleted or marked as deleted in DB.
   *
   * @param entity deleted entity instance
   * @param entityManager EntityManager that owns the entity instance
   */
  void onBeforeDelete(T entity, EntityManager entityManager);
}