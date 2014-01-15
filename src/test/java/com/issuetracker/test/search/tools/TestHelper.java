package com.issuetracker.test.search.tools;

/**
 * @author: Jiří Holuša
 */
public class TestHelper {

    public static final String PERSON_NAME = "Bruce Banner";
    public static final int PERSON_ID = 1;
    public static final String ADDRESS_STREET = "Wall street";
    public static final String ADDRESS_CITY = "New York";

    public static Person createTester() {
        Person person = new Person();
        person.setName(PERSON_NAME);
        person.setId(PERSON_ID);

        Address address = new Address();
        address.setStreet(ADDRESS_STREET);
        address.setCity(ADDRESS_CITY);

        person.setAddress(address);

        return person;
    }


}
