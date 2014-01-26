package com.issuetracker.test.search;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.test.search.tools.Person;
import com.issuetracker.test.search.tools.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jiří Holuša
 */
public class IndexEmbeddedAnnotationTest {

    private Indexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
    }

    @Test
    public void testBasicIndexation() {
        Person person = TestHelper.createTesterWithEmbedded();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("address.street"));
        assertTrue(index.containsKey("address.city"));

        assertEquals(TestHelper.ADDRESS_STREET, index.get("address.street"));
        assertEquals(TestHelper.ADDRESS_CITY, index.get("address.city"));
    }

    @Test
    public void testBasicIndexationWithPrefix() {
        Person person = TestHelper.createTesterWithEmbedded();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.address.street"));
        assertTrue(index.containsKey("prefix.address.city"));

        assertEquals(TestHelper.ADDRESS_STREET, index.get("prefix.address.street"));
        assertEquals(TestHelper.ADDRESS_CITY, index.get("prefix.address.city"));
    }

    @Test
    public void testNullIndexation() {
        Person person = TestHelper.createTesterWithEmbedded();
        person.setAddress(null);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertFalse(index.containsKey("address.street"));
        assertFalse(index.containsKey("address.city"));
    }

    @Test
    public void testIndexationWithCycle() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();

        person.setFriend(person2);
        person2.setFriend(person);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertTrue(index.containsKey("friend.name"));
        assertTrue(index.containsKey("friend.id"));

        assertEquals(TestHelper.PERSON2_NAME, index.get("friend.name"));
        assertEquals(Integer.toString(TestHelper.PERSON2_ID), index.get("friend.id"));
    }
}
