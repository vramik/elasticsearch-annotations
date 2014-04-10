package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.tools.WrongAnnotatedEmbeddedEntity;
import com.github.holmistr.esannotations.tools.WrongAnnotatedPrimitiveEntity;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests Indexer.
 *
 * @author: Jiří Holuša
 */
public class IndexerTest {

    private Indexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
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
