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
    private Address backupAddress;

    @IndexEmbedded
    private Person notIndexedFriend;

    @IndexEmbedded(depth = 1)
    private Person indexedFriend;

    @IndexEmbedded(depth = 2)
    private Person indexedFriendWithAddress;

    private List<Person> preferredFriends;

    private Set<Person> relatives;

    private Map<String, Person> friendByNicknames;

    public Address getBackupAddress() {
        return backupAddress;
    }

    public void setBackupAddress(Address backupAddress) {
        this.backupAddress = backupAddress;
    }

    public Person getIndexedFriendWithAddress() {
        return indexedFriendWithAddress;
    }

    public void setIndexedFriendWithAddress(Person indexedFriendWithAddress) {
        this.indexedFriendWithAddress = indexedFriendWithAddress;
    }

    public Person getIndexedFriend() {
        return indexedFriend;
    }

    public void setIndexedFriend(Person indexedFriend) {
        this.indexedFriend = indexedFriend;
    }

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

    public Person getNotIndexedFriend() {
        return notIndexedFriend;
    }

    public void setNotIndexedFriend(Person notIndexedFriend) {
        this.notIndexedFriend = notIndexedFriend;
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
