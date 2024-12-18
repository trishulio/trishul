package io.trishul.quantity.management.service.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.quantity.service.unit.service.QuantityUnitService;
import io.trishul.quantity.unit.UnitEntity;
import io.trishul.quantity.unit.dto.UnitDto;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class QuantityUnitControllerTest {
    private QuantityUnitController quantityUnitController;

    private QuantityUnitService quantityUnitService;

    @BeforeEach
    public void init() {
        quantityUnitService = mock(QuantityUnitService.class);

        quantityUnitController =
                new QuantityUnitController(quantityUnitService, new AttributeFilter());
    }

    @Test
    public void testGetUnits() {
        Page<UnitEntity> mPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));

        doReturn(mPage)
                .when(quantityUnitService)
                .getUnits(Set.of("g"), new TreeSet<>(List.of("id")), true, 1, 10);

        PageDto<UnitDto> dto =
                quantityUnitController.getUnits(
                        Set.of("g"), new TreeSet<>(List.of("id")), true, 1, 10);

        assertEquals(1, dto.getTotalPages());
        assertEquals(List.of(new UnitDto("g")), dto.getContent());
    }
}
