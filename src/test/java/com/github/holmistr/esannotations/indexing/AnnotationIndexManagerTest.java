package com.github.holmistr.esannotations.indexing;

import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchClient;
import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchNode;
import com.github.tlrx.elasticsearch.test.support.junit.runners.ElasticsearchRunner;
import com.github.holmistr.esannotations.tools.TestHelper;
import com.github.holmistr.esannotations.tools.Person;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

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
