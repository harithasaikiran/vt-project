package com.projectmgmt.vt.domain;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Base class for entities with UUID identifier. <p> Inherit from it if you need an entity without
 * optimistic locking, create, update and soft deletion info.
 */
@MappedSuperclass
public abstract class BaseUuidEntity extends BaseGenericIdEntity<UUID> implements HasUuid {

  private static final long serialVersionUID = -2217624132287086972L;

  @Id
  @Column(name = "id")
  protected UUID id;

  public BaseUuidEntity() {
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public UUID getUuid() {
    return id;
  }

  public void setUuid(UUID uuid) {
    this.id = uuid;
  }
}
