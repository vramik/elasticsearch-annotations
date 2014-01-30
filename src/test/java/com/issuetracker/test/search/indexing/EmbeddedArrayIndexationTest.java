package com.issuetracker.test.search.indexing;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
import com.issuetracker.search.indexing.commons.CyclicIndexationException;
import com.issuetracker.test.search.tools.Address;
import com.issuetracker.test.search.tools.Person;
import com.issuetracker.test.search.tools.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jiří Holuša
 */
public class EmbeddedArrayIndexationTest {

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

        Address[] addressArray = new Address[2];
        addressArray[0] = address;
        addressArray[1] = address2;

        person.setAddressArray(addressArray);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("addressArray.street"));
        assertTrue(index.containsKey("addressArray.city"));

        assertTrue(index.get("addressArray.street").contains(TestHelper.ADDRESS_STREET));
        assertTrue(index.get("addressArray.street").contains(TestHelper.ADDRESS2_STREET));
        assertTrue(index.get("addressArray.city").contains(TestHelper.ADDRESS_CITY));
        assertTrue(index.get("addressArray.city").contains(TestHelper.ADDRESS2_CITY));
    }

    @Test(expected = CyclicIndexationException.class)
    public void testUnlimitedDepthNotIndexingCycle() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Person person3 = TestHelper.createTester3WithFieldOnly();

        Person[] personArray = new Person[2];
        personArray[0] = person2;
        personArray[1] = person3;

        person.setNotIndexedPersonArray(personArray);

        indexer.index(person);
    }

    @Test
    public void testIndexingCyclicWithDepth() {
        Person person = TestHelper.createTesterWithEmbedded();
        Person person2 = TestHelper.createTester2WithEmbedded();
        Person person3 = TestHelper.createTester3WithEmbedded();

        Person[] personArray = new Person[2];
        personArray[0] = person2;
        personArray[1] = person3;

        person.setIndexedPersonArray(personArray);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("indexedPersonArray.name"));
        assertTrue(index.containsKey("indexedPersonArray.id"));
        assertFalse(index.containsKey("indexedPersonArray.address.city"));
        assertFalse(index.containsKey("indexedPersonArray.address.street"));

        assertTrue(index.get("indexedPersonArray.name").contains(TestHelper.PERSON2_NAME));
        assertTrue(index.get("indexedPersonArray.name").contains(TestHelper.PERSON3_NAME));
        assertTrue(index.get("indexedPersonArray.id").contains(Integer.toString(TestHelper.PERSON2_ID)));
        assertTrue(index.get("indexedPersonArray.id").contains(Integer.toString(TestHelper.PERSON3_ID)));
    }
}
