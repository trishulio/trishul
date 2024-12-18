package io.trishul.user.service.user.service.salutation.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationDto;
import io.trishul.user.service.user.service.salutation.service.UserSalutationService;

@SuppressWarnings("unchecked")
public class UserSalutationControllerTest {
   private UserSalutationController userSalutationController;

   private UserSalutationService userSalutationService;

   @BeforeEach
   public void init() {
       userSalutationService = mock(UserSalutationService.class);

       userSalutationController = new UserSalutationController(userSalutationService, new AttributeFilter());
   }

   @Test
   public void testGetMeasures() {
       Page<UserSalutation> mPage = new PageImpl<>(List.of(new UserSalutation(1L, "MR", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));

       doReturn(mPage).when(userSalutationService).getSalutations(
           Set.of(1L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<UserSalutationDto> dto = userSalutationController.getSalutations(
               Set.of(1L),
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(1, dto.getTotalPages());
       assertEquals(List.of(new UserSalutationDto(1L, "MR", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), dto.getContent());
   }
}