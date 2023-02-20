package com.projectmgmt.vt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Base class for entities with Long identifier.
 */
@MappedSuperclass
@JsonIgnoreProperties({"id"})
public abstract class BaseLongIdEntity extends BaseGenericIdEntity<Long> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseLongIdEntity that = (BaseLongIdEntity) o;
    if (this.id == null || that.id == null)
      return false;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
