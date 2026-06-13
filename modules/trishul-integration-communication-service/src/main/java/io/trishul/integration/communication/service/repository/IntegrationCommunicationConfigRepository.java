package io.trishul.integration.communication.service.repository;

import io.trishul.integration.communication.model.IntegrationCommunicationConfig;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IntegrationCommunicationConfigRepository
    extends JpaRepository<IntegrationCommunicationConfig, Long>,
    JpaSpecificationExecutor<IntegrationCommunicationConfig>, ExtendedRepository<Long> {
  @Override
  @Query("select count(i) > 0 from integrationCommunicationConfig i where i.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from integrationCommunicationConfig i where i.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
