package com.issuetracker.test.search;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.test.search.tools.Person;
import com.issuetracker.test.search.tools.TestHelper;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jiří Holuša
 */
public class FieldAnnotationTest {

    @Test
    public void testFieldAnnotation() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = TestHelper.createTester();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("name"));
        assertTrue(index.containsKey("id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("id"));
    }

    @Test
    public void testFieldAnnotationWithPrefix() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = TestHelper.createTester();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.name"));
        assertTrue(index.containsKey("prefix.id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("prefix.name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("prefix.id"));
    }
}
