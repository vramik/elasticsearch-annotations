package com.issuetracker.test.search;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.test.search.tools.PersonWithFieldOnly;
import com.issuetracker.test.search.tools.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TODO: document this
 * @author: Jiří Holuša
 */
public class FieldAnnotationTest {

    private Indexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
    }

    @Test
    public void testBasicIndexation() {
        PersonWithFieldOnly person = TestHelper.createTesterWithFieldOnly();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("name"));
        assertTrue(index.containsKey("id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("id"));
    }

    @Test
    public void testBasicIndexationWithPrefix() {
        PersonWithFieldOnly person = TestHelper.createTesterWithFieldOnly();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.name"));
        assertTrue(index.containsKey("prefix.id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("prefix.name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("prefix.id"));
    }

    @Test
    public void testNullIndexation() {
        PersonWithFieldOnly person = TestHelper.createTesterWithFieldOnly();
        person.setName(null);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("id"));
        assertFalse(index.containsKey("name"));
    }
}
