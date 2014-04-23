package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.tools.TestHelper;
import com.github.holmistr.esannotations.tools.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests FieldAnnotationProcessor.
 *
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
        Person person = TestHelper.createTesterWithFieldOnly();

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("name"));
        assertTrue(index.containsKey("id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("id"));
    }

    @Test
    public void testBasicIndexationWithPrefix() {
        Person person = TestHelper.createTesterWithFieldOnly();

        indexer.index(person, "prefix.");
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("prefix.name"));
        assertTrue(index.containsKey("prefix.id"));

        assertEquals(TestHelper.PERSON_NAME, index.get("prefix.name"));
        assertEquals(Integer.toString(TestHelper.PERSON_ID), index.get("prefix.id"));
    }

    @Test
    public void testRenamedField() {
        Person person = TestHelper.createTesterWithFieldOnly();
        person.setRenamedField("renamed value");

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("renamedPrimitive"));
        assertEquals("renamed value", index.get("renamedPrimitive"));
    }

    @Test
    public void testNullIndexation() {
        Person person = TestHelper.createTesterWithFieldOnly();
        person.setName(null);

        indexer.index(person);
        Map<String, String> index = indexer.getIndexOfSingleEntityAsMap();

        assertTrue(index.containsKey("id"));
        assertFalse(index.containsKey("name"));
    }
}
