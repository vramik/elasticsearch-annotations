package com.issuetracker.search.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO: document this
 *
 * @author Jiří Holuša
 */
public class SearchManager {

    private Client client;

    public SearchManager(Client client) {
        this.client = client;
    }

    public <T> List<T> search(SearchResponse searchResponse, Class<T> clazz) {
        List<T> result = new ArrayList<T>();



        return result;
    }

    public Client getClient() {
        return client;
    }
}
