package com.projectmgmt.vt.sys.listener;


import com.projectmgmt.vt.listener.*;

public enum EntityListenerType {

  BEFORE_DETACH(BeforeDetachEntityListener.class),
  BEFORE_ATTACH(BeforeAttachEntityListener.class),
  BEFORE_INSERT(BeforeInsertEntityListener.class),
  AFTER_INSERT(AfterInsertEntityListener.class),
  BEFORE_UPDATE(BeforeUpdateEntityListener.class),
  AFTER_UPDATE(AfterUpdateEntityListener.class),
  AFTER_DELETE(AfterDeleteEntityListener.class),
  BEFORE_DELETE(BeforeDeleteEntityListener.class);

  private final Class listenerInterface;

  private EntityListenerType(Class listenerInterface) {
    this.listenerInterface = listenerInterface;
  }

  public Class getListenerInterface() {
    return listenerInterface;
  }
}
