package io.trishul.test.repository;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.DummyCrudEntityAccessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DummyCrudEntityRepository
        extends JpaRepository<DummyCrudEntity, Long>,
                JpaSpecificationExecutor<DummyCrudEntity>,
                Refresher<DummyCrudEntity, DummyCrudEntityAccessor>,
                ExtendedRepository<Long> {}
