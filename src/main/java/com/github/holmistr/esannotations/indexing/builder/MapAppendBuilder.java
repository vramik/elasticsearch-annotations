package com.github.holmistr.esannotations.indexing.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Builder interface. The put creates new entry if the key
 * doesn't exist, otherwise it appends to the current value.
 *
 * @author Jiří Holuša
 */
public class MapAppendBuilder implements Builder {

    private Map<String, StringBuilder> builder = new HashMap<String, StringBuilder>();

    @Override
    public void put(String key, String value) {
        if(!builder.containsKey(key)) {
            builder.put(key, new StringBuilder());
        }

        StringBuilder previousValue = builder.get(key);
        if(previousValue.length() != 0) {
            previousValue.append(' ');
        }
        builder.put(key, previousValue.append(value));
    }

    @Override
    public Map<String, String> getIndexAsMap() {
        Map<String, String> index = new HashMap<String, String>();
        Set<String> keys = builder.keySet();

        for(String key: keys) {
            index.put(key, builder.get(key).toString());
        }

        return index;
    }
}
