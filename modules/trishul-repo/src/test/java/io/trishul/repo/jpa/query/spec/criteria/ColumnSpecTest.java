package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.repo.jpa.query.root.RootUtil;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColumnSpecTest {
    private Root<?> mRoot;
    private RootUtil mRootUtil;

    private ColumnSpec<String> column;

    @BeforeEach
    public void init() {
        mRootUtil = mock(RootUtil.class);
        column = new ColumnSpec<>(mRootUtil, new String[] {"PATH_1", "PATH_2"});
    }

    @Test
    public void testGetExpression_GetsPathWithJoin() {
        Path<?> mPath = mock(Path.class);
        doReturn(mPath).when(mRootUtil).getPath(mRoot, new String[] {"PATH_1", "PATH_2"});

        Expression<?> expression = column.getExpression(mRoot, null, null);

        assertEquals(mPath, expression);
    }
}
