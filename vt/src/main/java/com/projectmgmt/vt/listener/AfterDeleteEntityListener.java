package com.projectmgmt.vt.listener;




import com.projectmgmt.vt.domain.Entity;

import java.sql.Connection;

/**
 * Defines the contract for handling of entities after they have been deleted or marked as deleted
 * in DB.
 */
public interface AfterDeleteEntityListener<T extends Entity> {

  /**
   * Executes after the object has been deleted or marked as deleted in DB. <p> Modification of the
   * entity state or using {@code EntityManager} is impossible here. Use {@code connection} if you
   * need to make changes in the database.
   *
   * @param entity deleted entity
   * @param connection JDBC connection to the database of the deleted entity
   */
  void onAfterDelete(T entity, Connection connection);
}