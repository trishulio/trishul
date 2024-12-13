package io.trishul.crud.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import io.trishul.repo.aggregation.repo.AggregationRepository;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.user.service.user.service.repository.UserRepository;
import io.trishul.user.service.user.service.repository.role.repository.UserRoleRepository;
import io.trishul.user.service.user.service.salutation.repository.UserSalutationRepository;
import io.trishul.repo.aggregation.service.AggregationService;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.user.service.user.service.role.service.UserRoleService;
import io.trishul.user.service.user.service.salutation.service.UserSalutationService;
import io.trishul.tenant.service.service.TenantService;
import io.trishul.user.service.user.service.service.UserService;
import io.trishul.model.util.ThreadLocalUtilityProvider;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.crud.controller.filter.AttributeFilter;

public class ServiceAutoConfigurationTest {
    private ServiceAutoConfiguration serviceAutoConfiguration;

    @BeforeEach
    public void init() {
        this.serviceAutoConfiguration = new ServiceAutoConfiguration();
    }

    @Test
    public void testTenantManagementService_returnsInstanceOfTenantManagementService() {
        final TenantService tenantManagementService = this.serviceAutoConfiguration.tenantManagementService(null, null, null, null, null, null, null);
        assertTrue(tenantManagementService instanceof TenantService);
    }

    @Test
    public void testSupplierService_returnsInstanceOfSupplierServiceImpl() {
        final SupplierService supplierService = this.serviceAutoConfiguration.supplierService(null);
        assertTrue(supplierService instanceof SupplierServiceImpl);
    }

    @Test
    public void testSupplierContactService_returnsInstanceOfSupplierContactServiceImpl() {
        final SupplierContactService supplierContactService = this.serviceAutoConfiguration.supplierContactService(null, null);
        assertTrue(supplierContactService instanceof SupplierContactServiceImpl);
    }

    @Test
    public void testFacilityService_returnsInstanceOfFacilityServiceImpl() {
        final FacilityService facilityService = this.serviceAutoConfiguration.facilityService(null);
        assertTrue(facilityService instanceof FacilityServiceImpl);
    }

    @Test
    public void testInvoiceItemService_ReturnsInstanceOfInvoiceItemService() {
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        this.serviceAutoConfiguration.invoiceItemService(mUtilProvider);
    }

    @Test
    public void testInvoiceService_ReturnsInstanceOfInvoiceService() {
        final InvoiceRepository mInvoiceRepo = mock(InvoiceRepository.class);
        final InvoiceRefresher mInvoiceRefresher = mock(InvoiceRefresher.class);
        final InvoiceItemService mInvoiceItemService = mock(InvoiceItemService.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.invoiceService(mUtilProvider, mInvoiceItemService, mInvoiceRepo, mInvoiceRefresher);
    }

    @Test
    public void testPurchaseOrderService_ReturnsInstanceOfPurchaseOrderService() {
        final PurchaseOrderRepository mInvoiceRepo = mock(PurchaseOrderRepository.class);
        final PurchaseOrderRefresher mPurchaseOrderRefresher = mock(PurchaseOrderRefresher.class);
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.purchaseOrderService(mUtilProvider, mInvoiceRepo, mPurchaseOrderRefresher);
    }

    @Test
    public void testInvoiceStatusService_ReturnsInstanceOfInvoiceStatusService() {
        final InvoiceStatusRepository mRepo = mock(InvoiceStatusRepository.class);
        final InvoiceStatusService service = this.serviceAutoConfiguration.invoiceStatusService(mRepo);

        assertTrue(service instanceof InvoiceStatusService);
    }

    @Test
    public void testAttributeFilter_ReturnsInstanceOfAttributeFilter() {
        new AttributeFilter();
    }

    @Test
    public void testShipmentService_ReturnsInstanceOfShipmentService() {
        final ShipmentRepository mShipmentRepo = mock(ShipmentRepository.class);
        final ShipmentRefresher mShipmentRefresher = mock(ShipmentRefresher.class);
        final MaterialLotService mMaterialLotService = mock(MaterialLotService.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.shipmentService(mUtilProvider, mShipmentRepo, mMaterialLotService, mShipmentRefresher);
    }

    @Test
    public void testMaterialLotService_ReturnsInstanceOfMaterialLotService() {
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        final MaterialLotService service = this.serviceAutoConfiguration.materialLotService(mUtilProvider);

        assertSame(MaterialLotService.class, service.getClass());
    }

    @Test
    public void testUtilityProvider_ReturnsInstanceOfThreadLocalUtilityProvider() {
        final UtilityProvider provider = this.serviceAutoConfiguration.utilityProvider();

        assertSame(ThreadLocalUtilityProvider.class, provider.getClass());
    }

    @Test
    public void testAggregationService_ReturnsInstanceOfAggregationService() {
        final AggregationRepository mAggrRepo = mock(AggregationRepository.class);
        final AggregationService service = this.serviceAutoConfiguration.aggrService(mAggrRepo);

        assertSame(AggregationService.class, service.getClass());
    }

    @Test
    public void testLotInventorySerivce_ReturnsInstanceOfMaterialLotInventoryService() {
        final AggregationService mAggrService = mock(AggregationService.class);
        final LotAggregationService service = this.serviceAutoConfiguration.lotInventoryService(mAggrService);

        assertSame(LotAggregationService.class, service.getClass());
    }

    @Test
    public void testProductService_ReturnsInstanceOfProductService() {
        final ProductRepository productRepositoryMock = mock(ProductRepository.class);
        final ProductCategoryService productCategoryServiceMock = mock(ProductCategoryService.class);
        final ProductMeasureValueService productMeasureValueServiceMock = mock(ProductMeasureValueService.class);
        final MeasureService productMeasureServiceMock = mock(MeasureService.class);

        final ProductService service = this.serviceAutoConfiguration.productService(productRepositoryMock, productCategoryServiceMock, productMeasureValueServiceMock, productMeasureServiceMock);

        assertTrue(service instanceof ProductServiceImpl);
    }

    @Test
    public void testProductCategory_ReturnsInstanceOfProductCategoryService() {
        final ProductCategoryRepository productCategoryRepositoryMock = mock(ProductCategoryRepository.class);
        final ProductCategoryService service = this.serviceAutoConfiguration.productCategoryService(productCategoryRepositoryMock);

        assertTrue(service instanceof ProductCategoryServiceImpl);
    }

    @Test
    public void testMeasureService_ReturnsInstanceOfMeasureService() {
        final MeasureRepository measureRepositoryMock = mock(MeasureRepository.class);
        final MeasureService service = this.serviceAutoConfiguration.measureService(measureRepositoryMock);

        assertTrue(service instanceof MeasureServiceImpl);
    }

    @Test
    public void testProductMeasureValueService_ReturnsInstanceOfProductMeasureValueService() {
        final ProductMeasureValueService service = this.serviceAutoConfiguration.productMeasureValueService();

        assertTrue(service instanceof ProductMeasureValueServiceImpl);
    }

    @Test
    public void testUserRoleService_ReturnsInstanceOfUserRoleService() {
                final UtilityProvider utilProvider = mock(UtilityProvider.class);
        final UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        final Refresher<UserRole, UserRoleAccessor> userRoleRefresher = mock(Refresher.class);

        final UserRoleService service = this.serviceAutoConfiguration.userRoleService(utilProvider, userRoleRepository, userRoleRefresher);

        assertTrue(service instanceof UserRoleService);
    }

    @Test
    public void testUserSalutationService_ReturnsInstanceOfUserSalutationService() {
        final UserSalutationRepository measureRepositoryMock = mock(UserSalutationRepository.class);
        final UserSalutationService service = this.serviceAutoConfiguration.userSalutationService(measureRepositoryMock);

        assertTrue(service instanceof UserSalutationService);
    }

    @Test
    public void testUserService_ReturnsInstanceOfUserService() {
        final UserRepository userRepositoryMock = mock(UserRepository.class);
        final Refresher<User, UserAccessor> userRefresher = mock(Refresher.class);
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        final TenantIaasUserService idpUserService = mock(TenantIaasUserService.class);

        UserService service = this.serviceAutoConfiguration.userService(mUtilProvider, userRepositoryMock, userRefresher, idpUserService);
    }

    @Test
    public void testProcurementService_ReturnsInstanceOfProcurementService() {
        final InvoiceService invoiceServiceMock = mock(InvoiceService.class);
        final ShipmentService shipmentServiceMock = mock(ShipmentService.class);
        final ProcurementRepository procurementRepo = mock(ProcurementRepository.class);
        final ProcurementRefresher procurementRefresher = mock(ProcurementRefresher.class);

        final ProcurementService service = this.serviceAutoConfiguration.procurementService(invoiceServiceMock, shipmentServiceMock, procurementRepo, procurementRefresher);

        assertTrue(service instanceof ProcurementService);
    }

    @Test
    public void testBrewService_ReturnsInstanceOfBrewService() {
        final BrewRepository brewRepositoryMock = mock(BrewRepository.class);
        final BrewRefresher brewRefresherMock = mock(BrewRefresher.class);
        final BrewService service = this.serviceAutoConfiguration.brewService(brewRepositoryMock, brewRefresherMock);

        assertTrue(service instanceof BrewServiceImpl);
    }

    @Test
    public void testBrewTaskService_ReturnsInstanceOfBrewTaskService() {
        final BrewTaskRepository brewTaskRepositoryMock = mock(BrewTaskRepository.class);
        final BrewTaskService service = this.serviceAutoConfiguration.brewTaskService(brewTaskRepositoryMock);

        assertTrue(service instanceof BrewTaskServiceImpl);
    }

    @Test
    public void testBrewStageService_ReturnsInstanceOfBrewStageService() {
        final BrewStageRepository brewStageRepositoryMock = mock(BrewStageRepository.class);
        final BrewStageRefresher brewStageRefresherMock = mock(BrewStageRefresher.class);

        final BrewStageService service = this.serviceAutoConfiguration.brewStageService(brewStageRepositoryMock, brewStageRefresherMock);

        assertTrue(service instanceof BrewStageServiceImpl);
    }

    @Test
    public void testBrewStageStatusService_ReturnsInstanceOfBrewStageStatusService() {
        final BrewStageStatusRepository brewStageStatusRepositoryMock = mock(BrewStageStatusRepository.class);

        final BrewStageStatusService service = this.serviceAutoConfiguration.brewStageStatusService(brewStageStatusRepositoryMock);

        assertTrue(service instanceof BrewStageStatusServiceImpl);
    }

    @Test
    public void testMixtureService_ReturnsInstanceOfMixtureService() {
        final MixtureRepository mixtureRepositoryMock = mock(MixtureRepository.class);
        final MixtureRefresher mixtureRefresher = mock(MixtureRefresher.class);
        final MixtureService service = this.serviceAutoConfiguration.mixtureService(mixtureRepositoryMock, mixtureRefresher);

        assertTrue(service instanceof MixtureServiceImpl);
    }

    @Test
    public void testMaterialPortionService_ReturnsInstanceOfMaterialPortionService() {
        final MixtureMaterialPortionRepository materialPortionRepositoryMock = mock(MixtureMaterialPortionRepository.class);
        final MixtureMaterialPortionRefresher MixtureMaterialPortionRefresher = mock(MixtureMaterialPortionRefresher.class);
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        final StockLotService stockLotServiceMock = mock(StockLotService.class);
        final MixtureMaterialPortionService service = this.serviceAutoConfiguration.mixtureMaterialPortionService(mUtilProvider, materialPortionRepositoryMock, stockLotServiceMock, MixtureMaterialPortionRefresher);

        assertTrue(service instanceof MixtureMaterialPortionServiceImpl);
    }

    @Test
    public void testMixtureRecordingService_ReturnsInstanceOfMixtureRecordingService() {
        final MixtureRecordingRepository mixtureRecordingRepositoryMock = mock(MixtureRecordingRepository.class);
        final MixtureRecordingRefresher mixtureRecordingRefresher = mock(MixtureRecordingRefresher.class);
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        final MixtureRecordingService service = this.serviceAutoConfiguration.mixtureRecordingService(mUtilProvider, mixtureRecordingRepositoryMock, mixtureRecordingRefresher);

        assertTrue(service instanceof MixtureRecordingServiceImpl);
    }

    @Test
    public void testFinishedGoodService_ReturnsInstanceOfFinishedGoodervice() {
        final FinishedGoodLotRepository mFinishedGoodRepo = mock(FinishedGoodLotRepository.class);
        final FinishedGoodLotRefresher mFinishedGoodRefresher = mock(FinishedGoodLotRefresher.class);
        final FinishedGoodLotMaterialPortionService mFgMaterialPortionService = mock(FinishedGoodLotMaterialPortionService.class);
        final FinishedGoodLotMixturePortionService mFgMixturePortionService = mock(FinishedGoodLotMixturePortionService.class);
        final FinishedGoodLotFinishedGoodLotPortionService mFgLotFgLotPortionService = mock(FinishedGoodLotFinishedGoodLotPortionService.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.finishedGoodService(mUtilProvider, mFgMixturePortionService, mFgMaterialPortionService, mFgLotFgLotPortionService, mFinishedGoodRepo, mFinishedGoodRefresher);
    }

    @Test
    public void testFinishedGoodInventoryervice_ReturnsInstanceOfFinishedGoodInventoryService() {
        final AggregationService mAggrService = mock(AggregationService.class);
        final FinishedGoodInventoryRepository mFinishedGoodInventoryRepo = mock(FinishedGoodInventoryRepository.class);

        final FinishedGoodInventoryService service = this.serviceAutoConfiguration.finishedGoodInventoryService(mAggrService, mFinishedGoodInventoryRepo);

        assertTrue(service instanceof FinishedGoodInventoryServiceImpl);
    }
}
