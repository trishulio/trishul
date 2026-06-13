package io.trishul.ai.service.tool.model.repository;

import io.trishul.ai.tool.model.AiTool;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AiToolRepository extends JpaRepository<AiTool, Long>,
    JpaSpecificationExecutor<AiTool>, ExtendedRepository<Long> {
  @Override
  @Query("select count(a) > 0 from ai_tool a where a.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from ai_tool a where a.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
