package io.trishul.ai.service.speech.model.repository;

import io.trishul.ai.speech.model.AiSpeechConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiSpeechConfigRepository extends JpaRepository<AiSpeechConfig, Long>,
    JpaSpecificationExecutor<AiSpeechConfig>, ExtendedRepository<Long> {
}
