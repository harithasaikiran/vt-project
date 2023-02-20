package com.projectmgmt.vt.domain;

import java.util.UUID;

/**
 * Interface to be implemented by entities that have a persistent attribute
 * of {@link UUID} type.
 *
 * @author Mounir MESSAOUDI
 * @version 1.0
 * @created 20-avr.-2018 11:29:28
 */
public interface HasUuid {

  UUID getUuid();
  void setUuid(UUID uuid);

}