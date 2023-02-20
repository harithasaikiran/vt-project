package com.projectmgmt.vt.domain;

import java.util.Date;

/**
 * Interface to be implemented by entities that contain information about who created them and when.
 */
public interface Creatable {
  Date getCreateTs();

  void setCreateTs(Date date);

  String getCreatedBy();

  void setCreatedBy(String createdBy);
}