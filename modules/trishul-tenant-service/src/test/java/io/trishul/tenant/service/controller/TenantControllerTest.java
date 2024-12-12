package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddTenantDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.dto.UpdateTenantDto;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.service.impl.TenantService;

public class TenantControllerTest {
    private TenantController controller;

    private CrudControllerService<
                UUID,
                Tenant,
                BaseTenant,
                UpdateTenant,
                TenantDto,
                AddTenantDto,
                UpdateTenantDto
            > mCrudController;

    private TenantService mService;

    @BeforeEach
    public void init() {
        this.mCrudController = mock(CrudControllerService.class);
        this.mService = mock(TenantService.class);
        this.controller = new TenantController(mCrudController, mService);
    }

    @Test
    public void testGetAllTenant_ReturnsDtosFromController() throws MalformedURLException {
        doReturn(new PageImpl<>(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))))).when(mService).getAll(
            Set.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
            Set.of("T1"),
            Set.of(new URL("http://localhost/")),
            true,
            new TreeSet<>(List.of("id")),
            true,
            1,
            10
        );
        doReturn(new PageDto<>(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")))), Set.of(""));

        PageDto<TenantDto> page = this.controller.getAll(
            Set.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
            Set.of("T1"),
            Set.of(new URL("http://localhost/")),
            true,
            new TreeSet<>(List.of("id")),
            true,
            1,
            10,
            Set.of("")
        );

        PageDto<TenantDto> expected = new PageDto<>(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))), 1, 1);
        assertEquals(expected, page);
    }

    @Test
    public void testGetTenant_ReturnsDtoFromController() {
        doReturn(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))).when(mCrudController).get(UUID.fromString("00000000-0000-0000-0000-000000000001"), Set.of(""));

        TenantDto dto = this.controller.getTenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), Set.of(""));

        TenantDto expected = new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertEquals(expected, dto);
    }

    @Test
    public void testDeleteTenants_ReturnsDeleteCountFromController() {
        doReturn(1L).when(mCrudController).delete(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        assertEquals(1L, this.controller.deleteTenants(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"))));
    }

    @Test
    public void testAddTenants_AddsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001")))).when(mCrudController).add(List.of(new AddTenantDto()));

        List<TenantDto> dtos = this.controller.addTenant(List.of(new AddTenantDto()));

        assertEquals(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))), dtos);
    }

    @Test
    public void testUpdateTenants_PutsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001")))).when(mCrudController).put(List.of(new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))));

        List<TenantDto> dtos = this.controller.updateTenant(List.of(new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))));

        assertEquals(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))), dtos);
    }

    @Test
    public void testPatchTenants_PatchToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001")))).when(mCrudController).patch(List.of(new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))));

        List<TenantDto> dtos = this.controller.patchTenant(List.of(new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))));

        assertEquals(List.of(new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"))), dtos);
    }
}
