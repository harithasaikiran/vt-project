package com.projectmgmt.vt.listener;


import com.projectmgmt.vt.domain.Entity;
import jakarta.persistence.EntityManager;


/**
 * Defines the contract for handling entities before they have been inserted into DB.
 *
 */
public interface BeforeInsertEntityListener<T extends Entity> {

  /**
   * Executes before the object has been inserted into DB.
   *
   * @param entity        inserted entity instance
   * @param entityManager EntityManager that owns the entity instance
   */
  void onBeforeInsert(T entity, EntityManager entityManager);
}