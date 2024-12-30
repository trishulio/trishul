package io.trishul.repo.jpa.query.join.joiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import jakarta.persistence.criteria.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JpaJoinerLocalCacheTest {
  private JpaJoinerLocalCache cache;

  @BeforeEach
  public void init() {
    this.cache = new JpaJoinerLocalCache();
  }

  @Test
  public <X, Y> void testGet_CallsSupplier_WhenCacheIsMissing() {
    Key<X, Y> mKey = mock(Key.class);
    Path<X> mPath = mock(Path.class);
    Supplier<Path<X>> mSupplier = mock(Supplier.class);
    doReturn(mPath).when(mSupplier).get();

    Path<X> path = this.cache.get(mKey, mSupplier);

    assertEquals(mPath, path);
    verify(mSupplier, times(1)).get();
  }

  @Test
  public <X, Y> void testGet_CallsSupplierOnceOnlyAndServesSubsequentCallsFromCache_WhenCacheIsPresent() {
    Key<X, Y> mKey = mock(Key.class);
    Path<X> mPath = mock(Path.class);
    Supplier<Path<X>> mSupplier = mock(Supplier.class);
    doReturn(mPath).when(mSupplier).get();

    this.cache.get(mKey, mSupplier);
    Path<X> path = this.cache.get(mKey, mSupplier);

    assertEquals(mPath, path);
    verify(mSupplier, times(1)).get();
  }

  @Test
  public <X, Y> void testJoin_ReturnsCachedJoinFromLocalThread()
      throws InterruptedException, ExecutionException {
    Key<X, Y> mKey = mock(Key.class);
    Path<X> mPath = mock(Path.class);
    Supplier<Path<X>> mSupplier = mock(Supplier.class);
    doReturn(mPath).when(mSupplier).get();

    CompletableFuture<Path<X>> op1
        = CompletableFuture.supplyAsync(() -> this.cache.get(mKey, mSupplier));
    CompletableFuture<Path<X>> op2
        = CompletableFuture.supplyAsync(() -> this.cache.get(mKey, mSupplier));

    op1.thenAccept(path -> assertEquals(mPath, path));
    op2.thenAccept(path -> assertEquals(mPath, path));

    CompletableFuture.allOf(op1, op2).thenRun(() -> verify(mSupplier, times(2)).get()).get();
  }

  @Test
  public <X, Y> void testJoin_ReturnsCachedJoinFromLocalThread_Multithreaded()
      throws InterruptedException {
    Key<X, Y> mKey = mock(Key.class);
    Path<X> mPath = mock(Path.class);
    Supplier<Path<X>> mSupplier = mock(Supplier.class);
    doReturn(mPath).when(mSupplier).get();

    new Thread(() -> {
      Path<X> path = this.cache.get(mKey, mSupplier);
      assertEquals(mPath, path);
    }).start();

    new Thread(() -> {
      Path<X> path = this.cache.get(mKey, mSupplier);
      assertEquals(mPath, path);
    }).start();

    Thread.sleep(1000);

    verify(mSupplier, times(2)).get();
  }
}
