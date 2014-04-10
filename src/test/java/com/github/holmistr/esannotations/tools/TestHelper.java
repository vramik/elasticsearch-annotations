package com.github.holmistr.esannotations.tools;

/**
 * Helper class for testing. Provied creation of testing objects.
 *
 * @author: Jiří Holuša
 */
public class TestHelper {

    public static final String PERSON_INDEX = "people";
    public static final String PERSON_TYPE = "person";

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
        Address address = createAddress();

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
        Address address = createAddress2();

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
        Address address = createAddress3();

        person.setAddress(address);

        return person;
    }

    public static Address createAddress() {
        Address address = new Address();
        address.setStreet(ADDRESS_STREET);
        address.setCity(ADDRESS_CITY);

        return address;
    }

    public static Address createAddress2() {
        Address address = new Address();
        address.setStreet(ADDRESS2_STREET);
        address.setCity(ADDRESS2_CITY);

        return address;
    }

    public static Address createAddress3() {
        Address address = new Address();
        address.setStreet(ADDRESS3_STREET);
        address.setCity(ADDRESS3_CITY);

        return address;
    }
}
