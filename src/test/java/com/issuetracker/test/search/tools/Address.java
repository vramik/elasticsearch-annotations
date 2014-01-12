package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.Indexed;

/**
 * Created with IntelliJ IDEA.
 * User: Jirka
 * Date: 12.1.14
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
@Indexed
public class Address {

    @Field
    private String street;

    @Field
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
