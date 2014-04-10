package com.github.holmistr.esannotations.tools;

import com.github.holmistr.esannotations.indexing.annotations.ContainedIn;
import com.github.holmistr.esannotations.indexing.annotations.DocumentId;
import com.github.holmistr.esannotations.indexing.annotations.Field;
import com.github.holmistr.esannotations.indexing.annotations.Indexed;

import java.util.Collection;
import java.util.Map;

/**
 * Entity for testing purposes.
 *
 * @author Jiří Holuša
 */
@Indexed
public class Address {

    @DocumentId
    private int id;

    @Field
    private String street;

    @Field
    private String city;

    @ContainedIn
    private Person associatedPerson;

    @ContainedIn
    private Collection<Person> associatedPeopleInCollection;

    @ContainedIn
    private Map<?, Person> associatedPeopleInMap;

    @ContainedIn
    private Person[] associatedPeopleInArray;

    public Map<?, Person> getAssociatedPeopleInMap() {
        return associatedPeopleInMap;
    }

    public void setAssociatedPeopleInMap(Map<?, Person> associatedPeopleInMap) {
        this.associatedPeopleInMap = associatedPeopleInMap;
    }

    public Person[] getAssociatedPeopleInArray() {
        return associatedPeopleInArray;
    }

    public void setAssociatedPeopleInArray(Person[] associatedPeopleInArray) {
        this.associatedPeopleInArray = associatedPeopleInArray;
    }

    public Collection<Person> getAssociatedPeopleInCollection() {
        return associatedPeopleInCollection;
    }

    public void setAssociatedPeopleInCollection(Collection<Person> associatedPeopleInCollection) {
        this.associatedPeopleInCollection = associatedPeopleInCollection;
    }

    public Person getAssociatedPerson() {
        return associatedPerson;
    }

    public void setAssociatedPerson(Person associatedPerson) {
        this.associatedPerson = associatedPerson;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
