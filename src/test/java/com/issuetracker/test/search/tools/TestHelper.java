package com.issuetracker.test.search.tools;

/**
 * @author: Jiří Holuša
 */
public class TestHelper {

    public static final String PERSON_NAME = "Bruce Banner";
    public static final int PERSON_ID = 1;
    public static final String ADDRESS_STREET = "Wall street";
    public static final String ADDRESS_CITY = "New York";

    public static final String PERSON_NAME2 = "Bruce Wayne";
    public static final int PERSON_ID2 = 2;
    public static final String ADDRESS_STREET2 = "Bat street";
    public static final String ADDRESS_CITY2 = "Gotham city";

    public static PersonWithFieldOnly createTesterWithFieldOnly() {
        PersonWithFieldOnly person = new PersonWithFieldOnly();
        person.setName(PERSON_NAME);
        person.setId(PERSON_ID);

        return person;
    }

    public static PersonWithIndexEmbedded createTesterWithEmbedded() {
        PersonWithIndexEmbedded person = new PersonWithIndexEmbedded();
        person.setName(PERSON_NAME);
        person.setId(PERSON_ID);

        Address address = new Address();
        address.setStreet(ADDRESS_STREET);
        address.setCity(ADDRESS_CITY);

        person.setAddress(address);

        return person;
    }

    public static PersonWithFieldOnly createTester2WithFieldOnly() {
        PersonWithFieldOnly person = new PersonWithFieldOnly();
        person.setName(PERSON_NAME2);
        person.setId(PERSON_ID2);

        return person;
    }

    public static PersonWithIndexEmbedded createTester2WithEmbedded() {
        PersonWithIndexEmbedded person = new PersonWithIndexEmbedded();
        person.setName(PERSON_NAME2);
        person.setId(PERSON_ID2);

        Address address = new Address();
        address.setStreet(ADDRESS_STREET2);
        address.setCity(ADDRESS_CITY2);

        person.setAddress(address);

        return person;
    }


}
