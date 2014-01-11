package com.issuetracker.test.search.indexer;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.api.Indexer;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */

public class AnnotationIndexerTest {

    @Test
    public void testIndexer() throws IOException {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Tester tester = new Tester();
        tester.setName("TestName");
        tester.setId(1);

        XContentBuilder builder = jsonBuilder().startObject();
        indexer.setBuilder(builder);

        indexer.index(tester);
        builder.endObject();

        System.out.println(builder.string());
        builder.close();
    }
}
