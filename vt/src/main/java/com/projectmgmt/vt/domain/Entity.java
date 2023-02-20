package com.projectmgmt.vt.domain;


/**
 * Interface to be implemented by domain model objects with identifiers.
 * @param <T> identifier type
 *
 */
public interface Entity<T> extends Instance {
  T getId();
}