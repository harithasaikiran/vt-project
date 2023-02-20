package com.projectmgmt.vt.domain.global;

import java.util.UUID;


public class JavaUuidSource implements UuidSource {

  @Override
  public UUID createUuid() {
    return UUID.randomUUID();
  }
}
