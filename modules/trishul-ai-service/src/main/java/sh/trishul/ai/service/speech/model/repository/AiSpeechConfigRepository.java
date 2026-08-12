package sh.trishul.ai.service.speech.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sh.trishul.ai.speech.model.AiSpeechConfig;
import sh.trishul.repo.jpa.repository.ExtendedRepository;

@Repository
public interface AiSpeechConfigRepository extends JpaRepository<AiSpeechConfig, Long>,
    JpaSpecificationExecutor<AiSpeechConfig>, ExtendedRepository<Long> {
  @Override
  @Query("select count(a) > 0 from ai_speech_config a where a.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from ai_speech_config a where a.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
