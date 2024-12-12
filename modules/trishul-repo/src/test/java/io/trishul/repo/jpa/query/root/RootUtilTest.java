package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RootUtilTest {
    class Layer0 {
        private Layer1 layer1;
    }

    class Layer1 {
        private Layer2 layer2;
    }

    class Layer2 {
        private Layer3 layer3;
    }

    class Layer3 {
    }

    private From<?, Layer0> mRoot;

    private From<?, Layer1> mLayer1;
    private From<?, Layer2> mLayer2;
    private From<?, Layer3> mLayer3;

    Path<?> mPath0;
    Path<?> mPath1;
    Path<?> mPath2;
    Path<?> mPath3;

    private RootUtil rootUtil;
    private JpaJoiner mJpaJoiner;

    @BeforeEach
    public void init() {
        mJpaJoiner = mock(CriteriaJoinAnnotationJoiner.class);
        rootUtil = new RootUtil(mJpaJoiner);

        mPath0 = mock(From.class);
        mPath1 = mock(From.class);
        mPath2 = mock(From.class);
        mPath3 = mock(From.class);

        mRoot = mock(From.class);
        doReturn(mPath0).when(mJpaJoiner).get(mRoot, "get");
        doReturn(mPath0).when(mRoot).get("get");

        mLayer1 = mock(From.class);
        doReturn(mLayer1).when(mJpaJoiner).join(mRoot, "layer1");
        doReturn(mPath1).when(mJpaJoiner).get(mLayer1, "get");

        mLayer2 = mock(From.class);
        doReturn(mLayer2).when(mJpaJoiner).join(mLayer1, "layer2");
        doReturn(mPath2).when(mJpaJoiner).get(mLayer2, "get");

        mLayer3 = mock(From.class);
        doReturn(mLayer3).when(mJpaJoiner).join(mLayer2, "layer3");
        doReturn(mPath3).when(mJpaJoiner).get(mLayer3, "get");
    }

    @Test
    public void testGetPath_ReturnsDeepPathOnJoin_WhenPathIsNotNull() {
        assertEquals(mPath0, rootUtil.getPath(mRoot, new String[] { "get" }));
        assertEquals(mPath1, rootUtil.getPath(mRoot, new String[] { "layer1", "get" }));
        assertEquals(mPath2, rootUtil.getPath(mRoot, new String[] { "layer1", "layer2", "get" }));
        assertEquals(mPath3, rootUtil.getPath(mRoot, new String[] { "layer1", "layer2", "layer3", "get" }));
    }

    @Test
    public void testGetPath_ThrowException_WhenPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPath(mRoot, null));
    }

    @Test
    public void testGetPath_ThrowException_WhenPathIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPath(mRoot, new String[] {}));
    }
}
