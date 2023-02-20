package com.projectmgmt.vt.listener;

import com.projectmgmt.vt.domain.Entity;
import jakarta.persistence.EntityManager;;



/**
 * Defines the contract for handling of entities before they have been updated in DB.
 */
public interface BeforeUpdateEntityListener<T extends Entity> {

  /**
   * Executes before the object has been updated in DB.
   *
   * @param entity updated entity instance
   * @param entityManager EntityManager that owns the entity instance
   */
  void onBeforeUpdate(T entity, EntityManager entityManager);
}