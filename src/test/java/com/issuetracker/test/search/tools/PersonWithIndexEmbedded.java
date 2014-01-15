package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */
@Indexed
public class PersonWithIndexEmbedded {

    @Field
    private String name;

    @Field
    private int id;

    @IndexEmbedded
    private Address address;

    @IndexEmbedded
    private PersonWithIndexEmbedded friend;

    public PersonWithIndexEmbedded getFriend() {
        return friend;
    }

    public void setFriend(PersonWithIndexEmbedded friend) {
        this.friend = friend;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", address=" + address +
                '}';
    }
}
