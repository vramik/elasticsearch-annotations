package com.issuetracker.test.search.indexing;

import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchClient;
import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchNode;
import com.github.tlrx.elasticsearch.test.support.junit.runners.ElasticsearchRunner;
import com.issuetracker.search.indexing.AnnotationIndexManager;
import com.issuetracker.search.indexing.AnnotationIndexer;
import com.issuetracker.search.indexing.Indexer;
import com.issuetracker.test.search.tools.Address;
import com.issuetracker.test.search.tools.Person;
import com.issuetracker.test.search.tools.TestHelper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jiří Holuša
 */
@RunWith(ElasticsearchRunner.class)
public class AnnotationIndexManagerTest {

    @ElasticsearchNode
    private Node node;

    @ElasticsearchClient
    private Client client;

    private AnnotationIndexManager manager;

    @Before
    public void init() {
        manager = new AnnotationIndexManager(client);
    }

    @Test
    public void testIndexCreation() throws IOException {
        Person person = TestHelper.createTesterWithFieldOnly();

        manager.index(person);

        GetResponse response = client.prepareGet(TestHelper.PERSON_INDEX, TestHelper.PERSON_TYPE, Integer.toString(person.getId()))
                .setFields("id", "name")
                .execute()
                .actionGet();

        assertEquals(person.getName(), response.getField("name").getValue());
        assertEquals(Integer.toString(person.getId()), response.getField("id").getValue());
    }

}
