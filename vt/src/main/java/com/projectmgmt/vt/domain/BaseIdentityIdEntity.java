package com.projectmgmt.vt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.util.Objects;

@MappedSuperclass
@JsonIgnoreProperties({"id"})
public class BaseIdentityIdEntity extends BaseGenericIdEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
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
