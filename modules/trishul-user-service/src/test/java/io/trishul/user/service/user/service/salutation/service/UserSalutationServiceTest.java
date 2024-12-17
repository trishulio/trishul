package io.trishul.user.service.user.service.salutation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserSalutationServiceTest {
    private UserSalutationService userSalutationService;

    private UserSalutationRepository userSalutationRepository;

    @BeforeEach
    public void init() {
        userSalutationRepository = Mockito.mock(UserSalutationRepository.class);
        userSalutationService = new UserSalutationService(userSalutationRepository);
    }

    @Test
    public void testGetSalutations_returnsSalutations() throws Exception {
        Page<UserSalutation> expectedSalutationsPage = new PageImpl<>(List.of(new UserSalutation(1L, "MR", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));

        final ArgumentCaptor<Specification<UserSalutation>> specificationCaptor = ArgumentCaptor.forClass(Specification.class);

        when(userSalutationRepository.findAll(specificationCaptor.capture(), eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, new String[] {"id"}))))).thenReturn(expectedSalutationsPage);

        Page<UserSalutation> actualSalutationsPage = userSalutationService.getSalutations(null, new TreeSet<>(List.of("id")), true, 0, 100);

        assertEquals(List.of(new UserSalutation(1L, "MR", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), actualSalutationsPage.getContent());

        // TODO: Pending testing for the specification
        // specificationCaptor.getValue();
    }

    @Test
    public void testUserSalutationService_classIsTransactional() throws Exception {
        Transactional transactional = userSalutationService.getClass().getAnnotation(Transactional.class);

        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }

    @Test
    public void testUserSalutationService_methodsAreNotTransactional() throws Exception {
        Method[] methods = userSalutationService.getClass().getMethods();
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
