package com.projectmgmt.vt.domain;

// import com.smartest.connectedwell.core.converter.AbstractEnumConverter;

import java.util.Arrays;

public enum TimeUnits implements EnumClass<Character> {

  SECONDS('s'),
  MINUTES('m'),
  HOURS('H');

  private char id;

  TimeUnits(char id) {
    this.id = id;
  }

  @Override
  public Character getId() {
    return id;
  }

  public static TimeUnits getEnumById(char key) {
    return Arrays.stream(values()).filter(e -> e.getId().equals(key)).findFirst().orElse(null);
  }

}

