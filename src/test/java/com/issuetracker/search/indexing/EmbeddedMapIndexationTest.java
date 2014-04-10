package com.issuetracker.search.indexing;

import com.issuetracker.search.commons.CyclicIndexationException;
import com.issuetracker.search.tools.Person;
import com.issuetracker.search.tools.TestHelper;
import com.issuetracker.search.tools.Address;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests EmbeddedMapProcessor.
 *
 * @author: Jiří Holuša
 */
public class EmbeddedMapIndexationTest {

    private Indexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
    }

    @Test
    public void testBasicIndexation() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Address address = TestHelper.createAddress();
        Address address2 = TestHelper.createAddress2();

        Map<Integer, Address> addressMap = new HashMap<Integer, Address>();
        addressMap.put(0, address);
        addressMap.put(1, address2);

        person.setAddressMap(addressMap);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("addressMap.street"));
        assertTrue(index.containsKey("addressMap.city"));

        assertTrue(index.get("addressMap.street").contains(TestHelper.ADDRESS_STREET));
        assertTrue(index.get("addressMap.street").contains(TestHelper.ADDRESS2_STREET));
        assertTrue(index.get("addressMap.city").contains(TestHelper.ADDRESS_CITY));
        assertTrue(index.get("addressMap.city").contains(TestHelper.ADDRESS2_CITY));
    }

    @Test(expected = CyclicIndexationException.class)
    public void testUnlimitedDepthNotIndexingCycle() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Person person3 = TestHelper.createTester3WithFieldOnly();

        Map<Integer, Person> personMap = new HashMap<Integer, Person>();
        personMap.put(0, person2);
        personMap.put(1, person3);

        person.setNotIndexedPersonMap(personMap);

        indexer.index(person);
    }

    @Test
    public void testIndexingCyclicWithDepth() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();
        Person person3 = TestHelper.createTester3WithEmbedded();

        Map<Integer, Person> personMap = new HashMap<Integer, Person>();
        personMap.put(0, person2);
        personMap.put(1, person3);

        person.setIndexedPersonMap(personMap);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("indexedPersonMap.name"));
        assertTrue(index.containsKey("indexedPersonMap.id"));
        assertFalse(index.containsKey("indexedPersonMap.address.city"));
        assertFalse(index.containsKey("indexedPersonMap.address.street"));

        assertTrue(index.get("indexedPersonMap.name").contains(TestHelper.PERSON2_NAME));
        assertTrue(index.get("indexedPersonMap.name").contains(TestHelper.PERSON3_NAME));
        assertTrue(index.get("indexedPersonMap.id").contains(Integer.toString(TestHelper.PERSON2_ID)));
        assertTrue(index.get("indexedPersonMap.id").contains(Integer.toString(TestHelper.PERSON3_ID)));
    }
}
