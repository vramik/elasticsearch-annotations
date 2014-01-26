package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
@Indexed
public class Person {

    @Field
    private String name;

    @Field
    private int id;

    @IndexEmbedded
    private Address address;

    @IndexEmbedded
    private Person friend;

    private List<Person> preferredFriends;

    private Set<Person> relatives;

    private Map<String, Person> friendByNicknames;

    public List<Person> getPreferredFriends() {
        return preferredFriends;
    }

    public void setPreferredFriends(List<Person> preferredFriends) {
        this.preferredFriends = preferredFriends;
    }

    public Set<Person> getRelatives() {
        return relatives;
    }

    public void setRelatives(Set<Person> relatives) {
        this.relatives = relatives;
    }

    public Map<String, Person> getFriendByNicknames() {
        return friendByNicknames;
    }

    public void setFriendByNicknames(Map<String, Person> friendByNicknames) {
        this.friendByNicknames = friendByNicknames;
    }

    public Person getFriend() {
        return friend;
    }

    public void setFriend(Person friend) {
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
