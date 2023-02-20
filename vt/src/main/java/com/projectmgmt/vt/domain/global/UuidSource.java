package com.projectmgmt.vt.domain.global;

import java.util.UUID;

/**
 * Global interface to create UUIDs.
 * @author Mounir MESSAUDI
 *
 */
public interface UuidSource {

  String NAME = "smartest_UuidSource";

  /**
   * @return new UUID
   */
  UUID createUuid();
}