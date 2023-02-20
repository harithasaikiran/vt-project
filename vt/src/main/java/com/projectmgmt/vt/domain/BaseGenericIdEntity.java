package com.projectmgmt.vt.domain;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseGenericIdEntity<T> extends AbstractInstance implements Entity<T> {

  private static final long serialVersionUID = -8400641366148656528L;

  @Transient
  protected boolean __new = true;

  @Transient
  protected boolean __detached;

  protected transient boolean __managed;

  @Transient
  protected boolean __removed;

  public abstract void setId(T id);

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    if (getId() != null && ((BaseGenericIdEntity) other).getId() != null) {
      return Objects.equals(getId(), ((BaseGenericIdEntity) other).getId());
    }

    return super.equals(other);
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : super.hashCode();
  }

  @Override
  public String toString() {
    String state = "";
    if (__new) {
      state += "new,";
    }
    if (__managed) {
      state += "managed,";
    }
    if (__detached) {
      state += "detached,";
    }
    if (__removed) {
      state += "removed,";
    }
    if (state.length() > 0) {
      state = state.substring(0, state.length() - 1);
    }
    return getClass().getName() + "-" + getId() + " [" + state + "]";
  }

}
