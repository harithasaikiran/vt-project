package com.projectmgmt.vt.domain;


import jakarta.persistence.*;

/**
 * Base class for entities with Integer Identity identifier.
 */
@MappedSuperclass
public abstract class BaseIntIdentityIdEntity extends BaseDbGeneratedIdEntity<Integer> {

  private static final long serialVersionUID = 3083677558630811496L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Integer id;

}