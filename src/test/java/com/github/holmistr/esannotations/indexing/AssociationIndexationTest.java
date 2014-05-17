package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.tools.Person;
import com.github.holmistr.esannotations.tools.TestHelper;
import com.github.holmistr.esannotations.tools.Address;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for indexation of associations.
 *
 * @author: Jiří Holuša
 */
public class AssociationIndexationTest {

    private AnnotationIndexer indexer;

    @Before
    public void init() {
        indexer = new AnnotationIndexer();
    }

    @Test
    public void testBasicIndexation() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Address address = TestHelper.createAddress();
        address.setStreet("modifiedStreet");

        person.setAddress(address);
        address.setAssociatedPerson(person);

        indexer.index(address);
        Map<Object, Map<String, Map<String, Object>>> index = indexer.getCompleteIndexChanges();

        assertTrue(index.containsKey(address));
        assertTrue(index.get(address).get("source").containsKey("street"));
        assertEquals("modifiedStreet", index.get(address).get("source").get("street"));

        assertTrue(index.containsKey(person));
        assertTrue(index.get(person).get("source").containsKey("address.street"));
        assertEquals("modifiedStreet", index.get(person).get("source").get("address.street"));
    }

    @Test
    public void testAssociationsInCollection() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Address address = TestHelper.createAddress();
        address.setStreet("modifiedStreet");

        person.setAddress(address);
        person2.setAddress(address);

        Collection<Person> collection = new ArrayList<Person>();
        collection.add(person);
        collection.add(person2);
        address.setAssociatedPeopleInCollection(collection);

        indexer.index(address);
        Map<Object, Map<String, Map<String, Object>>> index = indexer.getCompleteIndexChanges();

        assertTrue(index.get(address).get("source").containsKey("street"));
        assertEquals("modifiedStreet", index.get(address).get("source").get("street"));

        assertTrue(index.containsKey(person));
        assertTrue(index.containsKey(person2));
        assertTrue(index.get(person).get("source").containsKey("address.street"));
        assertTrue(index.get(person2).get("source").containsKey("address.street"));
        assertEquals("modifiedStreet", index.get(person).get("source").get("address.street"));
        assertEquals("modifiedStreet", index.get(person2).get("source").get("address.street"));
    }

    @Test
    public void testAssociationsInMap() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Address address = TestHelper.createAddress();
        address.setStreet("modifiedStreet");

        person.setAddress(address);
        person2.setAddress(address);

        Map<Integer, Person> map = new HashMap<Integer, Person>();
        map.put(0, person);
        map.put(1, person2);
        address.setAssociatedPeopleInMap(map);

        indexer.index(address);
        Map<Object, Map<String, Map<String, Object>>> index = indexer.getCompleteIndexChanges();

        assertTrue(index.get(address).get("source").containsKey("street"));
        assertEquals("modifiedStreet", index.get(address).get("source").get("street"));

        assertTrue(index.containsKey(person));
        assertTrue(index.containsKey(person2));
        assertTrue(index.get(person).get("source").containsKey("address.street"));
        assertTrue(index.get(person2).get("source").containsKey("address.street"));
        assertEquals("modifiedStreet", index.get(person).get("source").get("address.street"));
        assertEquals("modifiedStreet", index.get(person2).get("source").get("address.street"));
    }

    @Test
    public void testAssociationsInArray() {
        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester2WithFieldOnly();
        Address address = TestHelper.createAddress();
        address.setStreet("modifiedStreet");

        person.setAddress(address);
        person2.setAddress(address);

        Person[] array = new Person[2];
        array[0] = person;
        array[1] = person2;
        address.setAssociatedPeopleInArray(array);

        indexer.index(address);
        Map<Object, Map<String, Map<String, Object>>> index = indexer.getCompleteIndexChanges();

        assertTrue(index.get(address).get("source").containsKey("street"));
        assertEquals("modifiedStreet", index.get(address).get("source").get("street"));

        assertTrue(index.containsKey(person));
        assertTrue(index.containsKey(person2));
        assertTrue(index.get(person).get("source").containsKey("address.street"));
        assertTrue(index.get(person2).get("source").containsKey("address.street"));
        assertEquals("modifiedStreet", index.get(person).get("source").get("address.street"));
        assertEquals("modifiedStreet", index.get(person2).get("source").get("address.street"));
    }


}
