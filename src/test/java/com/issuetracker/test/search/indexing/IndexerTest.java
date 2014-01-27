package com.issuetracker.test.search.indexing;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
import com.issuetracker.test.search.tools.NotAnnotatedEntity;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: Jiří Holuša
 */
public class IndexerTest {

    private Indexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEntityAnnotatedWithIndexed() {
        NotAnnotatedEntity notAnnotatedEntity = new NotAnnotatedEntity();
        indexer.index(notAnnotatedEntity);
    }
}
