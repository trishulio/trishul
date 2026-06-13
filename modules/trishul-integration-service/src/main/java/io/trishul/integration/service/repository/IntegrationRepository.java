package io.trishul.integration.service.repository;

import io.trishul.integration.model.Integration;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IntegrationRepository extends JpaRepository<Integration, Long>,
    JpaSpecificationExecutor<Integration>, ExtendedRepository<Long> {
  @Override
  @Query("select count(i) > 0 from integration i where i.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from integration i where i.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
