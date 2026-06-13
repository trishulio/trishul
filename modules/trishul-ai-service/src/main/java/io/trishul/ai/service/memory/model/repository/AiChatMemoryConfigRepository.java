package io.trishul.ai.service.memory.model.repository;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatMemoryConfigRepository extends JpaRepository<AiChatMemoryConfig, Long>,
    JpaSpecificationExecutor<AiChatMemoryConfig>, ExtendedRepository<Long> {
  @Override
  @Query("select count(a) > 0 from ai_chat_memory_config a where a.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from ai_chat_memory_config a where a.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
