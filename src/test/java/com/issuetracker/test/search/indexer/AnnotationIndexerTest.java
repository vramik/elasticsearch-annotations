package com.issuetracker.test.search.indexer;

import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.test.search.tools.Address;
import com.issuetracker.test.search.tools.Person;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */

public class AnnotationIndexerTest {

    private static final String PERSON_NAME = "Bruce Banner";
    private static final int PERSON_ID = 1;
    private static final String ADDRESS_STREET = "Wall street";
    private static final String ADDRESS_CITY = "New York";

    @Test
    public void testFieldAnnotation() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = createTester();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("name"));
        assertTrue(index.containsKey("id"));

        assertEquals(PERSON_NAME, index.get("name"));
        assertEquals(Integer.toString(PERSON_ID), index.get("id"));
    }

    @Test
    public void testFieldAnnotationWithPrefix() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = createTester();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.name"));
        assertTrue(index.containsKey("prefix.id"));

        assertEquals(PERSON_NAME, index.get("prefix.name"));
        assertEquals(Integer.toString(PERSON_ID), index.get("prefix.id"));
    }

    @Test
    public void testIndexEmbeddedAnnotation() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = createTester();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("address.street"));
        assertTrue(index.containsKey("address.city"));

        assertEquals(ADDRESS_STREET, index.get("address.street"));
        assertEquals(ADDRESS_CITY, index.get("address.city"));
    }

    @Test
    public void testIndexEmbeddedAnnotationWithPrefix() {
        AnnotationIndexer indexer = new AnnotationIndexer();
        Person person = createTester();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexAsMap();

        assertTrue(index.containsKey("prefix.address.street"));
        assertTrue(index.containsKey("prefix.address.city"));

        assertEquals(ADDRESS_STREET, index.get("prefix.address.street"));
        assertEquals(ADDRESS_CITY, index.get("prefix.address.city"));
    }

    private Person createTester() {
        Person person = new Person();
        person.setName(PERSON_NAME);
        person.setId(PERSON_ID);

        Address address = new Address();
        address.setStreet(ADDRESS_STREET);
        address.setCity(ADDRESS_CITY);

        person.setAddress(address);

        return person;
    }


}
