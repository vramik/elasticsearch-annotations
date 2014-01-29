package com.issuetracker.test.search.indexing;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
import com.issuetracker.test.search.tools.NotAnnotatedEntity;
import com.issuetracker.test.search.tools.WrongAnnotatedEmbeddedEntity;
import com.issuetracker.test.search.tools.WrongAnnotatedPrimitiveEntity;
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

    @Test(expected = IllegalArgumentException.class)
    public void testPrimitiveFieldAnnotatedWithIndexedEmbedded() {
        WrongAnnotatedPrimitiveEntity wrongAnnotatedPrimitiveEntity = new WrongAnnotatedPrimitiveEntity();
        indexer.index(wrongAnnotatedPrimitiveEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCustomClassAnnotatedWithField() {
        WrongAnnotatedEmbeddedEntity wrongAnnotatedEmbeddedEntity = new WrongAnnotatedEmbeddedEntity();
        indexer.index(wrongAnnotatedEmbeddedEntity);
    }
}
