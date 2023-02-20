package com.projectmgmt.vt.listener;

import com.projectmgmt.vt.domain.Entity;

import java.sql.Connection;

/**
 * Defines the contract for handling entities after they have been inserted into DB.
 */
public interface AfterInsertEntityListener<T extends Entity> {

  /**
   * Executes after the object has been inserted into DB. <p> Modification of the entity state or
   * using {@code EntityManager} is impossible here. Use {@code connection} if you need to make
   * changes in the database.
   *
   * @param entity inserted entity
   * @param connection JDBC connection to the database with the inserted entity
   */
  void onAfterInsert(T entity, Connection connection);
}