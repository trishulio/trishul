package sh.trishul.integration.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sh.trishul.integration.model.Integration;
import sh.trishul.repo.jpa.repository.ExtendedRepository;

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
