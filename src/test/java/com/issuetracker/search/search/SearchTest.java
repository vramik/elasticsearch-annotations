package com.issuetracker.search.search;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author: Jiří Holuša
 */
public class SearchTest {

    @Test
    public void testSomething() {
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        Map<String, Object> source = new HashMap<>();
        source.put("key", "Beer is the most suitable drink in the world. Beer rules.");
        source.put("secondKey", "how about having this value?");

        IndexResponse response = client.prepareIndex("index", "type", "1")
                .setSource(source)
                .execute()
                .actionGet();

        source = new HashMap<>();
        source.put("key", "completely different piece of shit");
        source.put("secondKey", "would you like to have a beer?");

        response = client.prepareIndex("index", "type", "2")
                .setSource(source)
                .execute()
                .actionGet();

        source = new HashMap<>();
        source.put("key", "no idea what I'm doing");
        source.put("secondKey", "Have you ever had sex with elephant and drunk beer in it?");

        response = client.prepareIndex("index", "type", "3")
                .setSource(source)
                .execute()
                .actionGet();

        client.close();
    }

    @Test
    public void testBasic() throws IOException {
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        SearchResponse response = client.prepareSearch("index")
                .setQuery(QueryBuilders.queryString("beer").field("secondKey", 2.0f))
                .setTypes("type")
                .execute()
                .actionGet();

        System.out.println(response.toString());
        System.out.println(response.getHits().getAt(0).getSource());

        client.close();
    }

}
