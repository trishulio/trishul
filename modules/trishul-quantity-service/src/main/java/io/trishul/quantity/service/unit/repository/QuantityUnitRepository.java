package io.trishul.quantity.service.unit.repository;

import io.trishul.quantity.unit.UnitEntity;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface QuantityUnitRepository
        extends JpaRepository<UnitEntity, String>, JpaSpecificationExecutor<UnitEntity> {
    Optional<UnitEntity> findBySymbol(String symbol);
}
