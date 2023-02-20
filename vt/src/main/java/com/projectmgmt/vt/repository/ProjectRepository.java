package com.projectmgmt.vt.repository;

import com.projectmgmt.vt.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByPublished(boolean published);

  Optional<Project> findByUuid(UUID uuid);

  List<Project> findByTitleContaining(String title);
}
