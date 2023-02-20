package com.projectmgmt.vt.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Base class for entities with Integer identifier.
 */
@MappedSuperclass
public abstract class BaseIntegerIdEntity extends BaseGenericIdEntity<Integer> {

  private static final long serialVersionUID = 1748237513475338490L;

  @Id
  @Column(name = "ID")
  protected Integer id;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }
}