package com.projectmgmt.vt.domain;


import java.util.Date;

/**
 * Interface to be implemented by entities that contain information about who updated them and
 * when.
 *
 * @author Mounir MESSAOUDI
 * @version 1.0
 * @created 20-avr.-2018 11:29:28
 */
public interface Updatable {

  Date getUpdateTs();

  void setUpdateTs(Date updateTs);

  String getUpdatedBy();

  void setUpdatedBy(String updatedBy);
}