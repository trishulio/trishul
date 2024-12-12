package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class QuantityUnitControllerTest {
   private QuantityUnitController quantityUnitController;

   private QuantityUnitService quantityUnitService;

   @BeforeEach
   public void init() {
       quantityUnitService = mock(QuantityUnitService.class);

       quantityUnitController = new QuantityUnitController(quantityUnitService, new AttributeFilter());
   }

   @Test
   public void testGetUnits() {
       Page<UnitEntity> mPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));

       doReturn(mPage).when(quantityUnitService).getUnits(
           Set.of("g"),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<UnitDto> dto = quantityUnitController.getUnits(
               Set.of("g"),
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(1, dto.getTotalPages());
       assertEquals(List.of(new UnitDto("g")), dto.getContent());
   }
}