package com.issuetracker.test.search.indexing;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
import com.issuetracker.search.indexing.commons.CyclicIndexationException;
import com.issuetracker.test.search.tools.Address;
import com.issuetracker.test.search.tools.Person;
import com.issuetracker.test.search.tools.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

/**
 * @author: Jiří Holuša
 */
public class EmbeddedCollectionIndexationTest {

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

        Collection<Address> addressCollection = new ArrayList<Address>();
        addressCollection.add(address);
        addressCollection.add(address2);

        person.setAddressCollection(addressCollection);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("addressCollection.street"));
        assertTrue(index.containsKey("addressCollection.city"));

        assertTrue(index.get("addressCollection.street").contains(TestHelper.ADDRESS_STREET));
        assertTrue(index.get("addressCollection.street").contains(TestHelper.ADDRESS2_STREET));
        assertTrue(index.get("addressCollection.city").contains(TestHelper.ADDRESS_CITY));
        assertTrue(index.get("addressCollection.city").contains(TestHelper.ADDRESS2_CITY));
    }

    @Test(expected = CyclicIndexationException.class)
    public void testUnlimitedDepthNotIndexingCycle() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Person person3 = TestHelper.createTester3WithFieldOnly();

        Collection<Person> personCollection = new ArrayList<Person>();
        personCollection.add(person2);
        personCollection.add(person3);

        person.setNotIndexedPersonCollection(personCollection);

        indexer.index(person);
    }

    @Test
    public void testIndexingCyclicWithDepth() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();
        Person person3 = TestHelper.createTester3WithEmbedded();

        Collection<Person> personCollection = new ArrayList<Person>();
        personCollection.add(person2);
        personCollection.add(person3);

        person.setIndexedPersonCollection(personCollection);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("indexedPersonCollection.name"));
        assertTrue(index.containsKey("indexedPersonCollection.id"));
        assertFalse(index.containsKey("indexedPersonCollection.address.city"));
        assertFalse(index.containsKey("indexedPersonCollection.address.street"));

        assertTrue(index.get("indexedPersonCollection.name").contains(TestHelper.PERSON2_NAME));
        assertTrue(index.get("indexedPersonCollection.name").contains(TestHelper.PERSON3_NAME));
        assertTrue(index.get("indexedPersonCollection.id").contains(Integer.toString(TestHelper.PERSON2_ID)));
        assertTrue(index.get("indexedPersonCollection.id").contains(Integer.toString(TestHelper.PERSON3_ID)));
    }
}
