package com.projectmgmt.vt.domain;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class AbstractInstance {

}
