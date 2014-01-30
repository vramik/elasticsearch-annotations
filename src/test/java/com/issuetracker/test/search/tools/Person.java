package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;

import java.util.Collection;
import java.util.Map;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
@Indexed
public class Person {

    @Field
    private String name;

    @DocumentId
    @Field
    private int id;

    @IndexEmbedded
    private Address address;

    @IndexEmbedded
    private Address backupAddress;

    @IndexEmbedded
    private Address addressAsAssociation;

    @IndexEmbedded
    private Person notIndexedFriend;

    @IndexEmbedded(depth = 1)
    private Person indexedFriend;

    @IndexEmbedded(depth = 2)
    private Person indexedFriendWithAddress;

    @IndexEmbedded
    private Collection<Address> addressCollection;

    @IndexEmbedded
    private Collection<Person> notIndexedPersonCollection;

    @IndexEmbedded(depth = 1)
    private Collection<Person> indexedPersonCollection;

    @IndexEmbedded
    private Address[] addressArray;

    @IndexEmbedded
    private Person[] notIndexedPersonArray;

    @IndexEmbedded(depth = 1)
    private Person[] indexedPersonArray;

    @IndexEmbedded
    private Map<Integer, Address> addressMap;

    @IndexEmbedded
    private Map<Integer, Person> notIndexedPersonMap;

    @IndexEmbedded(depth = 1)
    private Map<Integer, Person> indexedPersonMap;

    public Address getAddressAsAssociation() {
        return addressAsAssociation;
    }

    public void setAddressAsAssociation(Address addressAsAssociation) {
        this.addressAsAssociation = addressAsAssociation;
    }

    public Map<Integer, Address> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<Integer, Address> addressMap) {
        this.addressMap = addressMap;
    }

    public Map<Integer, Person> getNotIndexedPersonMap() {
        return notIndexedPersonMap;
    }

    public void setNotIndexedPersonMap(Map<Integer, Person> notIndexedPersonMap) {
        this.notIndexedPersonMap = notIndexedPersonMap;
    }

    public Map<Integer, Person> getIndexedPersonMap() {
        return indexedPersonMap;
    }

    public void setIndexedPersonMap(Map<Integer, Person> indexedPersonMap) {
        this.indexedPersonMap = indexedPersonMap;
    }

    public Person[] getIndexedPersonArray() {
        return indexedPersonArray;
    }

    public void setIndexedPersonArray(Person[] indexedPersonArray) {
        this.indexedPersonArray = indexedPersonArray;
    }

    public Person[] getNotIndexedPersonArray() {
        return notIndexedPersonArray;
    }

    public void setNotIndexedPersonArray(Person[] notIndexedPersonArray) {
        this.notIndexedPersonArray = notIndexedPersonArray;
    }

    public Address[] getAddressArray() {
        return addressArray;
    }

    public void setAddressArray(Address[] addressArray) {
        this.addressArray = addressArray;
    }

    public Collection<Person> getIndexedPersonCollection() {
        return indexedPersonCollection;
    }

    public void setIndexedPersonCollection(Collection<Person> indexedPersonCollection) {
        this.indexedPersonCollection = indexedPersonCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
    }

    public Collection<Person> getNotIndexedPersonCollection() {
        return notIndexedPersonCollection;
    }

    public void setNotIndexedPersonCollection(Collection<Person> notIndexedPersonCollection) {
        this.notIndexedPersonCollection = notIndexedPersonCollection;
    }

    public Collection<Address> getAddressCollection() {
        return addressCollection;
    }

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
