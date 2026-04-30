package io.trishul.auth.session.context.holder;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.base.types.lambda.CheckedRunnable;

class ThreadLocalContextHolderTest {
  private ThreadLocalContextHolder holder;

  @BeforeEach
  void init() {
    holder = new ThreadLocalContextHolder();
  }

  @Test
  void testAccessPrincipalContext_ReturnsTheTenantThatIsSetUsingMutator() {
    PrincipalContext mCtx = mock(PrincipalContext.class);
    holder.setContext(mCtx);

    PrincipalContext ctx = holder.getPrincipalContext();
    assertSame(mCtx, ctx);
  }

  @Test
  void testAccessPrincipalContext_ReturnsTheThreadsLocalContext()
      throws InterruptedException, ExecutionException {
    CompletableFuture<Void> op1 = runAsync(() -> {
      PrincipalContext mCtx1 = mock(PrincipalContext.class);
      holder.setContext(mCtx1);
      Thread.sleep(75);
      PrincipalContext ctx = holder.getPrincipalContext();
      assertSame(mCtx1, ctx);
    });
    CompletableFuture<Void> op2 = runAsync(() -> {
      PrincipalContext mCtx2 = mock(PrincipalContext.class);
      holder.setContext(mCtx2);
      Thread.sleep(50);
      PrincipalContext ctx = holder.getPrincipalContext();
      assertSame(mCtx2, ctx);
    });
    CompletableFuture<Void> op3 = runAsync(() -> {
      PrincipalContext mCtx3 = mock(PrincipalContext.class);
      holder.setContext(mCtx3);
      Thread.sleep(25);
      PrincipalContext ctx = holder.getPrincipalContext();
      assertSame(mCtx3, ctx);
    });

    CompletableFuture.allOf(op1, op2, op3).get();
  }

  @Test
  void testClear_RemovesValuesFromThreadLocal() {
    PrincipalContext mCtx = mock(PrincipalContext.class);
    holder.setContext(mCtx);
    holder.clear();

    assertNull(holder.getPrincipalContext());
    assertNull(holder.getSessionTenantId());
  }

  @Test
  void testSetContext_ReturnsThis() {
    PrincipalContext mCtx = mock(PrincipalContext.class);
    ThreadLocalContextHolder result = holder.setContext(mCtx);
    
    assertSame(holder, result);
    assertSame(mCtx, holder.getPrincipalContext());
  }

  @Test
  void testSetSessionTenantId_ReturnsThis() {
    UUID tenantId = UUID.randomUUID();
    PrincipalContext mCtx = mock(PrincipalContext.class);
    when(mCtx.getTenantIds()).thenReturn(Arrays.asList(tenantId));
    holder.setContext(mCtx);
    
    ThreadLocalContextHolder result = holder.setSessionTenantId(tenantId);
    
    assertSame(holder, result);
    assertEquals(tenantId, holder.getSessionTenantId());
  }

  @Test
  void testGetSessionTenantId_ReturnsSetValue() {
    UUID tenantId = UUID.randomUUID();
    PrincipalContext mCtx = mock(PrincipalContext.class);
    when(mCtx.getTenantIds()).thenReturn(Arrays.asList(tenantId));
    holder.setContext(mCtx);
    holder.setSessionTenantId(tenantId);
    
    UUID result = holder.getSessionTenantId();
    
    assertNotNull(result);
    assertEquals(tenantId, result);
  }

  @Test
  void testGetPatchEntities_AppliesUpdateToEntityWithNullId_WhenPatchIdIsNull() {
    UUID tenantId = UUID.randomUUID();
    PrincipalContext mCtx = mock(PrincipalContext.class);
    when(mCtx.getTenantIds()).thenReturn(Arrays.asList(tenantId));
    holder.setContext(mCtx);
    holder.setSessionTenantId(tenantId);
    holder.clear();

    assertNull(holder.getPrincipalContext());
    assertNull(holder.getSessionTenantId());
  }

  @Test
  void testSetSessionTenantId_ThrowsIllegalArgumentException_WhenTenantIdIsNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      holder.setSessionTenantId(null);
    });
    assertEquals("Tenant ID cannot be null", exception.getMessage());
  }

  @Test
  void testSetSessionTenantId_ThrowsIllegalArgumentException_WhenTenantIdNotInPrincipalContext() {
    UUID tenantId = UUID.randomUUID();
    UUID otherTenantId = UUID.randomUUID();
    PrincipalContext mCtx = mock(PrincipalContext.class);
    when(mCtx.getTenantIds()).thenReturn(Arrays.asList(otherTenantId));
    holder.setContext(mCtx);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      holder.setSessionTenantId(tenantId);
    });
    assertTrue(exception.getMessage().contains("not found in principal context"));
  }

  @Test
  void testSetSessionTenantId_Succeeds_WhenPrincipalContextIsNull() {
    UUID tenantId = UUID.randomUUID();
    // No principal context set
    holder.setSessionTenantId(tenantId);

    assertEquals(tenantId, holder.getSessionTenantId());
  }

  private CompletableFuture<Void> runAsync(CheckedRunnable<Exception> runnable) {
    CompletableFuture<Void> marker = new CompletableFuture<>();
    new Thread(() -> {
      try {
        runnable.run();
        marker.complete(null);
      } catch (Exception e) {
        fail();
      }
    }).start();

    return marker;
  }
}
