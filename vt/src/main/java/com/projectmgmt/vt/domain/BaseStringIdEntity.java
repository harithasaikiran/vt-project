package com.projectmgmt.vt.domain;


import jakarta.persistence.*;

/**
 * Base class for entities with String identifier.
 * <p>
 * Does not define an identifier field. Inheritors must define a field of type String and add
 * {@link javax.persistence.Id} annotation to it, e.g.
 * <pre>
 *  &#64;Id
 *  &#64;Column(name = "CODE")
 *  protected String code;
 * </pre>
 *
 */
@MappedSuperclass
public abstract class BaseStringIdEntity extends BaseGenericIdEntity<String> {

  private static final long serialVersionUID = -1887225952123433245L;

  @Override
  public abstract String getId();
}