package com.projectmgmt.vt.domain;

/**
 * Interface to be implemented by optimistically locked entities.
 *
 */
public interface Versioned {

  Integer getVersion();

  /**
   * Do not set version if you are not sure - it must be null for a new entity or loaded from the database
   * for a persistent one.
   */
  void setVersion(Integer version);
}
