package com.projectmgmt.vt.listener;

import com.projectmgmt.vt.domain.Entity;

/**
 * Defines the contract for handling entities right before they are attached to an EntityManager on
 * merge operation.
 */
public interface BeforeAttachEntityListener<T extends Entity> {

  /**
   * Executes before the object is attached to an EntityManager on merge operation.
   *
   * @param entity detached entity
   */
  void onBeforeAttach(T entity);
}
