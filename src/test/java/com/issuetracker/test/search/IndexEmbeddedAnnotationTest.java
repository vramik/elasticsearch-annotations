package com.issuetracker.test.search;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.test.search.tools.PersonWithIndexEmbedded;
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
        PersonWithIndexEmbedded person = TestHelper.createTesterWithEmbedded();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("address.street"));
        assertTrue(index.containsKey("address.city"));

        assertEquals(TestHelper.ADDRESS_STREET, index.get("address.street"));
        assertEquals(TestHelper.ADDRESS_CITY, index.get("address.city"));
    }

    @Test
    public void testBasicIndexationWithPrefix() {
        PersonWithIndexEmbedded person = TestHelper.createTesterWithEmbedded();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.address.street"));
        assertTrue(index.containsKey("prefix.address.city"));

        assertEquals(TestHelper.ADDRESS_STREET, index.get("prefix.address.street"));
        assertEquals(TestHelper.ADDRESS_CITY, index.get("prefix.address.city"));
    }

    @Test
    public void testNullIndexation() {
        PersonWithIndexEmbedded person = TestHelper.createTesterWithEmbedded();
        person.setAddress(null);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertFalse(index.containsKey("address.street"));
        assertFalse(index.containsKey("address.city"));
    }

    @Test
    public void testIndexationWithCycle() {
        PersonWithIndexEmbedded person = TestHelper.createTesterWithEmbedded();
        PersonWithIndexEmbedded person2 = TestHelper.createTester2WithEmbedded();

        person.setFriend(person2);
        person2.setFriend(person);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertTrue(index.containsKey("friend.name"));
        assertTrue(index.containsKey("friend.id"));

        assertEquals(TestHelper.PERSON_NAME2, index.get("friend.name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID2), index.get("friend.id"));

        System.out.println(index);
    }
}
