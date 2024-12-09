package io.trishul.quantity.service.unit.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.trishul.quantity.unit.UnitEntity;

@Repository
@Transactional
public interface QuantityUnitRepository extends JpaRepository<UnitEntity, String>, JpaSpecificationExecutor<UnitEntity> {
    Optional<UnitEntity> findBySymbol(String symbol);
}
