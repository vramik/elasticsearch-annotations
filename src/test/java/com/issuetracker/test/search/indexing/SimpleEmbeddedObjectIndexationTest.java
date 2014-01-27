package com.issuetracker.test.search.indexing;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
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
public class SimpleEmbeddedObjectIndexationTest {

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
    public void testIndexationWithCycleWithoutDepth() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();

        person.setNotIndexedFriend(person2);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertFalse(index.containsKey("notIndexedFriend.name"));
        assertFalse(index.containsKey("notIndexedFriend.id"));
    }

    @Test
    public void testIndexationWithCycleWithDepth() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();

        person.setIndexedFriend(person2);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertTrue(index.containsKey("indexedFriend.name"));
        assertTrue(index.containsKey("indexedFriend.id"));

        assertEquals(TestHelper.PERSON2_NAME, index.get("indexedFriend.name"));
        assertEquals(Integer.toString(TestHelper.PERSON2_ID), index.get("indexedFriend.id"));
    }

    @Test
    public void testIndexationWithCycleWithDepth2() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();

        person.setIndexedFriendWithAddress(person2);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertTrue(index.containsKey("indexedFriendWithAddress.address.street"));
        assertTrue(index.containsKey("indexedFriendWithAddress.address.city"));

        assertEquals(TestHelper.ADDRESS2_STREET, index.get("indexedFriendWithAddress.address.street"));
        assertEquals(TestHelper.ADDRESS2_CITY, index.get("indexedFriendWithAddress.address.city"));
    }

    @Test
    public void testIndexationWithCycleWithDepth3() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();

        person.setIndexedFriend(person2);
        person.setIndexedFriendWithAddress(person2);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertNotNull(index);
        assertTrue(index.containsKey("indexedFriend.name"));
        assertTrue(index.containsKey("indexedFriend.id"));
        assertFalse(index.containsKey("indexedFriend.address.city"));
        assertFalse(index.containsKey("indexedFriend.address.street"));

        assertEquals(TestHelper.PERSON2_NAME, index.get("indexedFriend.name"));
        assertEquals(Integer.toString(TestHelper.PERSON2_ID), index.get("indexedFriend.id"));

        assertTrue(index.containsKey("indexedFriendWithAddress.address.street"));
        assertTrue(index.containsKey("indexedFriendWithAddress.address.city"));

        assertEquals(TestHelper.ADDRESS2_STREET, index.get("indexedFriendWithAddress.address.street"));
        assertEquals(TestHelper.ADDRESS2_CITY, index.get("indexedFriendWithAddress.address.city"));
    }
}
