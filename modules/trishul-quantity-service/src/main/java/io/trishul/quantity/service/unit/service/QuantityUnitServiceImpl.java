package io.trishul.quantity.service.unit.service;

import static io.trishul.repo.jpa.repository.service.RepoService.pageRequest;

import java.util.Set;
import java.util.SortedSet;

import javax.measure.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import io.trishul.quantity.unit.QuantityUnitMapper;
import io.trishul.quantity.unit.UnitEntity;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;

@Transactional
public class QuantityUnitServiceImpl implements QuantityUnitService {
    private final QuantityUnitMapper quantityUnitMapper = QuantityUnitMapper.INSTANCE;

    private final QuantityUnitRepository quantityUnitRepository;

    public QuantityUnitServiceImpl(QuantityUnitRepository quantityUnitRepository) {
        this.quantityUnitRepository = quantityUnitRepository;
    }

    @Override
    public Page<UnitEntity> getUnits(Set<String> symbols, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Specification<UnitEntity> spec = WhereClauseBuilder
                .builder()
                .in(UnitEntity.FIELD_SYMBOL, symbols)
                .build();
        Page<UnitEntity> units = quantityUnitRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return units;
    }

    @Override
    public Unit<?> get(String symbol) {
        UnitEntity unit = quantityUnitRepository.findBySymbol(symbol).orElse(null);

        return quantityUnitMapper.fromEntity(unit);
    }
}
