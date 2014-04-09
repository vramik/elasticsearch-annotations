package com.issuetracker.search.search;

import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchClient;
import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchNode;
import com.github.tlrx.elasticsearch.test.support.junit.runners.ElasticsearchRunner;
import com.issuetracker.search.indexing.AnnotationIndexManager;
import com.issuetracker.search.tools.Person;
import com.issuetracker.search.tools.TestHelper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jiří Holuša
 */
@RunWith(ElasticsearchRunner.class)
public class SearchManagerTest {

    @ElasticsearchNode
    private Node node;

    @ElasticsearchClient
    private Client client;

    private SearchManager searchManager;
    private AnnotationIndexManager indexManager;

    @Before
    public void init() {
        searchManager = new SearchManager(client);
        indexManager = new AnnotationIndexManager(client);

        Person person = TestHelper.createTesterWithFieldOnly();
        Person person2 = TestHelper.createTester3WithFieldOnly();

        indexManager.index(person, true);
        indexManager.index(person2, true);
    }

    @Test
    public void testBasicSearch() {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", TestHelper.PERSON_NAME);

        SearchResponse response = client.prepareSearch(TestHelper.PERSON_INDEX)
                .setTypes(TestHelper.PERSON_TYPE)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();

        List<Person> persons = searchManager.search(response, Person.class);

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals(TestHelper.PERSON_NAME, persons.get(0).getName());
        assertEquals(TestHelper.PERSON_ID, persons.get(0).getId());
    }

    @Test
    public void testBasicGet() {
        GetResponse response = client.prepareGet(TestHelper.PERSON_INDEX, TestHelper.PERSON_TYPE, Integer.toString(TestHelper.PERSON3_ID))
                .execute()
                .actionGet();

        Person person = searchManager.get(response, Person.class);

        assertNotNull(person);
        assertEquals(TestHelper.PERSON3_ID, person.getId());
        assertEquals(TestHelper.PERSON3_NAME, person.getName());
        assertTrue(person.getAddress() == null);
    }
}