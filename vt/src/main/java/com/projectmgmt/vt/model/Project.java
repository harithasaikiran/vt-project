package com.projectmgmt.vt.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.projectmgmt.vt.domain.BaseIdentityIdEntity;
import com.projectmgmt.vt.domain.HasUuid;
import com.projectmgmt.vt.views.Views.Public;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project extends BaseIdentityIdEntity implements HasUuid, Serializable {
	private static final long serialVersionUID = 443780532833750860L;

	public Project(String title, String description, boolean b) {
		super();
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Column(name = "uuid", unique = true)
	@JsonView(Public.class)
	private UUID uuid;

	@Column(name = "name", nullable = false)
	@JsonView(Public.class)
	private String name;

	@Column(name = "title")
	@JsonView(Public.class)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	@JsonView(Public.class)
	private String description;

	@Column(name = "published")
	@JsonView(Public.class)
	private boolean published;

	public Project() {

	}
}
