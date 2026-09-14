package sh.trishul.user.service.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.model.OwnedEntity;

class OwnerEntityListenerTest {

  private ContextHolder mockContextHolder;
  private PrincipalContext mockPrincipal;
  private OwnerEntityListener listener;

  @BeforeEach
  void setUp() {
    mockContextHolder = mock(ContextHolder.class);
    mockPrincipal = mock(PrincipalContext.class);
    when(mockPrincipal.getUsername()).thenReturn("testuser@example.com");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);

    OwnerEntityListener.setContextHolder(mockContextHolder);
    listener = new OwnerEntityListener();
  }

  @AfterEach
  void tearDown() {
    OwnerEntityListener.setContextHolder(null);
  }

  @Test
  void testPrePersist_WhenOwnerUsernameNull_SetsUsername() {
    TestOwnedEntity entity = new TestOwnedEntity();
    listener.prePersist(entity);

    assertEquals("testuser@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenOwnerUsernameBlank_SetsUsername() {
    TestOwnedEntity entity = new TestOwnedEntity();
    entity.setOwnerUsername("   ");
    listener.prePersist(entity);

    assertEquals("testuser@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenOwnerUsernameAlreadySet_DoesNotOverwrite() {
    TestOwnedEntity entity = new TestOwnedEntity();
    entity.setOwnerUsername("original@example.com");
    listener.prePersist(entity);

    assertEquals("original@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPreUpdate_WhenOwnerUsernameNull_SetsUsername() {
    TestOwnedEntity entity = new TestOwnedEntity();
    listener.preUpdate(entity);

    assertEquals("testuser@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPreUpdate_WhenOwnerUsernameAlreadySet_DoesNotOverwrite() {
    TestOwnedEntity entity = new TestOwnedEntity();
    entity.setOwnerUsername("original@example.com");
    listener.preUpdate(entity);

    assertEquals("original@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenNonOwnedEntity_DoesNothing() {
    Object entity = new Object();
    listener.prePersist(entity);
    // No exception thrown - passes
  }

  @Test
  void testPreUpdate_WhenNonOwnedEntity_DoesNothing() {
    Object entity = new Object();
    listener.preUpdate(entity);
    // No exception thrown - passes
  }

  @Test
  void testPrePersist_WhenContextHolderNull_DoesNotSetUsername() {
    OwnerEntityListener.setContextHolder(null);
    OwnerEntityListener uninitializedListener = new OwnerEntityListener();

    TestOwnedEntity entity = new TestOwnedEntity();
    uninitializedListener.prePersist(entity);

    assertNull(entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenNoPrincipal_DoesNotSetUsername() {
    when(mockContextHolder.getPrincipalContext()).thenReturn(null);

    TestOwnedEntity entity = new TestOwnedEntity();
    listener.prePersist(entity);

    assertNull(entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenPrincipalUsernameNull_DoesNotSetUsername() {
    when(mockPrincipal.getUsername()).thenReturn(null);

    TestOwnedEntity entity = new TestOwnedEntity();
    listener.prePersist(entity);

    assertNull(entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenPrincipalUsernameBlank_DoesNotSetUsername() {
    when(mockPrincipal.getUsername()).thenReturn("   ");

    TestOwnedEntity entity = new TestOwnedEntity();
    listener.prePersist(entity);

    assertNull(entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenInstanceConstructorUsed_SetsUsername() {
    OwnerEntityListener.setContextHolder(null);
    OwnerEntityListener instanceListener = new OwnerEntityListener(mockContextHolder);

    TestOwnedEntity entity = new TestOwnedEntity();
    instanceListener.prePersist(entity);

    assertEquals("testuser@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenInstanceConstructorWithNull_FallsBackToStatic() {
    OwnerEntityListener instanceListener = new OwnerEntityListener(null);

    TestOwnedEntity entity = new TestOwnedEntity();
    instanceListener.prePersist(entity);

    assertEquals("testuser@example.com", entity.getOwnerUsername());
  }

  @Test
  void testPrePersist_WhenBothInstanceAndStaticContextHolderNull_DoesNotSetUsername() {
    OwnerEntityListener.setContextHolder(null);
    OwnerEntityListener instanceListener = new OwnerEntityListener(null);

    TestOwnedEntity entity = new TestOwnedEntity();
    instanceListener.prePersist(entity);

    assertNull(entity.getOwnerUsername());
  }

  private static final class TestOwnedEntity implements OwnedEntity {
    private String ownerUsername;

    @Override
    public String getOwnerUsername() {
      return ownerUsername;
    }

    @Override
    public void setOwnerUsername(String ownerUsername) {
      this.ownerUsername = ownerUsername;
    }
  }
}
