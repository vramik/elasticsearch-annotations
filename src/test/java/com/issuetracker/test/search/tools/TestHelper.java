package com.issuetracker.test.search.tools;

import java.util.*;

/**
 * @author: Jiří Holuša
 */
public class TestHelper {

    public static final String PERSON_NAME = "Bruce Banner";
    public static final String PERSON_NICK = "Hulk";
    public static final int PERSON_ID = 1;
    public static final String ADDRESS_STREET = "Wall street";
    public static final String ADDRESS_CITY = "New York";

    public static final String PERSON2_NAME = "Bruce Wayne";
    public static final String PERSON2_NICK = "Batman";
    public static final int PERSON2_ID = 2;
    public static final String ADDRESS2_STREET = "Bat street";
    public static final String ADDRESS2_CITY = "Gotham city";

    public static final String PERSON3_NAME = "Clark Kent";
    public static final String PERSON3_NICK = "Superman";
    public static final int PERSON3_ID = 3;
    public static final String ADDRESS3_STREET = "Super street";
    public static final String ADDRESS3_CITY = "Washington";

    public static Person createTesterWithFieldOnly() {
        Person person = new Person();
        person.setName(PERSON_NAME);
        person.setId(PERSON_ID);

        return person;
    }

    public static Person createTesterWithEmbedded() {
        Person person = createTesterWithFieldOnly();

        Address address = new Address();
        address.setStreet(ADDRESS_STREET);
        address.setCity(ADDRESS_CITY);

        person.setAddress(address);

        return person;
    }

    public static Person createTester2WithFieldOnly() {
        Person person = new Person();
        person.setName(PERSON2_NAME);
        person.setId(PERSON2_ID);

        return person;
    }

    public static Person createTester2WithEmbedded() {
        Person person = createTester2WithFieldOnly();

        Address address = new Address();
        address.setStreet(ADDRESS2_STREET);
        address.setCity(ADDRESS2_CITY);

        person.setAddress(address);

        return person;
    }

    public static Person createTester3WithFieldOnly() {
        Person person = new Person();
        person.setName(PERSON3_NAME);
        person.setId(PERSON3_ID);

        return person;
    }

    public static Person createTester3WithEmbedded() {
        Person person = createTester3WithFieldOnly();

        Address address = new Address();
        address.setStreet(ADDRESS3_STREET);
        address.setCity(ADDRESS3_CITY);

        person.setAddress(address);

        return person;
    }

    public Person createPersonWithCollections() {
        Person person = createTesterWithEmbedded();
        Person person2 = createTester2WithEmbedded();
        Person person3 = createTester3WithEmbedded();

        List<Person> preferredFriends = new ArrayList<Person>();
        preferredFriends.add(person2);
        preferredFriends.add(person3);
        person.setPreferredFriends(preferredFriends);

        Person person4 = createTester2WithEmbedded();
        person4.setId(4);
        Person person5 = createTester3WithEmbedded();
        person5.setId(5);

        Set<Person> relatives = new HashSet<Person>();
        relatives.add(person4);
        relatives.add(person5);
        person.setRelatives(relatives);

        Person person6 = createTester2WithEmbedded();
        person6.setId(6);
        Person person7 = createTester3WithEmbedded();
        person7.setId(7);

        Map<String, Person> friendByNicknames = new HashMap<String, Person>();
        friendByNicknames.put(PERSON2_NICK, person6);
        friendByNicknames.put(PERSON3_NICK, person7);
        person.setFriendByNicknames(friendByNicknames);

        return person;
    }

}
