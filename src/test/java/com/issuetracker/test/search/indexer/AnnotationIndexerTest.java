package com.issuetracker.test.search.indexer;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Tester;
import com.issuetracker.search.indexing.api.Indexer;
import org.junit.Test;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */

public class AnnotationIndexerTest {

    @Test
    public void testIndexer() {
        Indexer indexer = new AnnotationIndexer();
        Tester tester = new Tester();

        indexer.index(tester);

    }
}
