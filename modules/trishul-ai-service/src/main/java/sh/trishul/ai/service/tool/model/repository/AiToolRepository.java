package sh.trishul.ai.service.tool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sh.trishul.ai.tool.model.AiTool;
import sh.trishul.repo.jpa.repository.ExtendedRepository;

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
