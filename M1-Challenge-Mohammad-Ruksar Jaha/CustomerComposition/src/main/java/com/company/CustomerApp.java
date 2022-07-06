package com.company;



public class CustomerApp {

    public static void main(String[] args) {

        Customer customer = new Customer();

        customer.setFirstName("John");
        customer.setLastName("Hunter");
        customer.setEmailId("johnhunter@yahoo.com");
        customer.setPhoneNumber("444-444-4444");
        customer.setRewardsMember(true);

        Address address = new Address();

        address.setStreet1("1234 NE 128th St");
        address.setCity("Intellij");
        address.setState("Java");
        address.setZipCode("01234");

        customer.setBillingAddress(address);

        Address address1 = new Address();

        address1.setStreet1("2345 SE 182nd St");
        address1.setCity("Salem");
        address1.setState("Washington");
        address1.setZipCode("09876");

        customer.setShippingAddress(address1);



        System.out.println(customer);
        System.out.println(customer.isRewardsMember());





    }
}
