package com.projectmgmt.vt.listener;


import com.projectmgmt.vt.domain.Entity;

import java.sql.Connection;

/**
 * Defines the contract for handling of entities after they have been updated in DB.
 */
public interface AfterUpdateEntityListener<T extends Entity> {

  /**
   * Executes after the object has been updated in DB. <p> Modification of the entity state or using
   * {@code EntityManager} is impossible here. Use {@code connection} if you need to make changes in
   * the database.
   *
   * @param entity updated entity
   * @param connection JDBC connection to the database with the updated entity
   */
  void onAfterUpdate(T entity, Connection connection);
}