package io.trishul.ai.service.chat.model.repository;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatModelConfigRepository extends JpaRepository<AiChatModelConfig, Long>,
    JpaSpecificationExecutor<AiChatModelConfig>, ExtendedRepository<Long> {
  @Override
  @Query("select count(a) > 0 from ai_chat_model_config a where a.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from ai_chat_model_config a where a.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
