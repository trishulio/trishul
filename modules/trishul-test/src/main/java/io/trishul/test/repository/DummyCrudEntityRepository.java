package io.trishul.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import io.trishul.test.model.DummyCrudEntity;

@Repository
public interface DummyCrudEntityRepository extends JpaRepository<DummyCrudEntity, Long>,
    JpaSpecificationExecutor<DummyCrudEntity>, ExtendedRepository<Long> {
  @Override
  @Query("select count(d) > 0 from dummycrudentity d where d.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from dummycrudentity d where d.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
