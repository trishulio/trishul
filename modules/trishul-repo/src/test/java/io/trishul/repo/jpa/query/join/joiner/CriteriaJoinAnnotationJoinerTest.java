package io.trishul.repo.jpa.query.join.joiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.model.base.entity.CriteriaJoin;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CriteriaJoinAnnotationJoinerTest {
  class Entity {
    @JoinColumn
    @CriteriaJoin(type = JoinType.LEFT)
    private Child left;

    @JoinColumn
    @CriteriaJoin(type = JoinType.RIGHT)
    private Child right;

    @JoinColumn
    @CriteriaJoin(type = JoinType.INNER)
    private Child inner;

    @JoinColumn
    @CriteriaJoin
    private Child difault;

    @ManyToOne
    private Child manyToOne;
    @OneToMany
    private Child oneToMany;
    @Embedded
    private Child embedded;
    @JoinColumn
    private Child joinColumn;

    @SuppressWarnings("unused")
    private Child get;
  }

  class Child {
  }

  private JpaJoiner cjProcessor;

  private From<?, Entity> mEntity;

  @BeforeEach
  public void init() {
    mEntity = mock(From.class);
    doReturn(Entity.class).when(mEntity).getJavaType();

    this.cjProcessor = new CriteriaJoinAnnotationJoiner();
  }

  @Test
  public void testJoin_PerformsALeftJoin_WhenAnnotationValueIsLeft() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("left", JoinType.LEFT);

    From<?, ?> join = this.cjProcessor.join(mEntity, "left");

    assertEquals(mJoin, join);
  }

  @Test
  public void testJoin_PerformsARightJoin_WhenAnnotationValueIsRight() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("right", JoinType.RIGHT);

    From<?, ?> join = this.cjProcessor.join(mEntity, "right");

    assertEquals(mJoin, join);
  }

  @Test
  public void testJoin_PerformsAInnerJoin_WhenAnnotationValueIsInner() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("inner", JoinType.INNER);

    From<?, ?> join = this.cjProcessor.join(mEntity, "inner");

    assertEquals(mJoin, join);
  }

  @Test
  public void testJoin_PerformsAInnerJoin_WhenAnnotationValueIsNotProvided() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("difault", JoinType.INNER);

    From<?, ?> join = this.cjProcessor.join(mEntity, "difault");

    assertEquals(mJoin, join);
  }

  @Test
  public void testJoin_PerformsAInnerJoinOperation_WhenAnnotationIsMissing() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("get", JoinType.INNER);

    From<?, ?> join = this.cjProcessor.join(mEntity, "get");

    assertEquals(mJoin, join);
  }

  @Test
  public void testGet_DelegatesToJoinMethod_WhenEntityIsManyToOne() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("manyToOne", JoinType.INNER);

    Path<?> join = this.cjProcessor.get(mEntity, "manyToOne");
    assertEquals(mJoin, join);
  }

  @Test
  public void testGet_DelegatesToJoinMethod_WhenEntityIsOnetoMany() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("oneToMany", JoinType.INNER);

    Path<?> join = this.cjProcessor.get(mEntity, "oneToMany");
    assertEquals(mJoin, join);
  }

  @Test
  public void testGet_DelegatesToJoinMethod_WhenEntityIsEmbedded() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("embedded", JoinType.INNER);

    Path<?> join = this.cjProcessor.get(mEntity, "embedded");
    assertEquals(mJoin, join);
  }

  @Test
  public void testGet_DelegatesToJoinMethod_WhenEntityIsJoinColumn() {
    Join<?, ?> mJoin = mock(Join.class);
    doReturn(mJoin).when(mEntity).join("joinColumn", JoinType.INNER);

    Path<?> join = this.cjProcessor.get(mEntity, "joinColumn");
    assertEquals(mJoin, join);
  }

  @Test
  public void testGet_PerformsGetOnEntity_WhenEntityIsBasic() {
    Path<?> mJoin = mock(Path.class);
    doReturn(mJoin).when(mEntity).get("get");

    Path<?> join = this.cjProcessor.get(mEntity, "get");
    assertEquals(mJoin, join);
  }
}
