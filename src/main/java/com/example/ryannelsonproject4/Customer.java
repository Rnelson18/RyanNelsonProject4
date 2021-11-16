package com.example.ryannelsonproject4;

import java.util.ArrayList;

public abstract class Customer {


    private ArrayList<ShippingAddress> Addresses;
    private String Name;
    private int customerID;
    private static int nextID = 5000; //all preloaded customers from the text file must have IDs lower than 5000

    public Customer (String Name, int ID) {
        this.Name = Name;
        customerID = ID;
        Addresses = new ArrayList<ShippingAddress>();
    };

    public Customer(String custName)
    {
        Name = custName;
        nextID++;
        customerID = nextID;
        Addresses = new ArrayList<ShippingAddress>();
    }

    public abstract double payForOrder(ArrayList<MerchandiseItem> itemsInOrder);

    public double payOutstandingBalance(){
        return 0.0;
    }

    public void arrangeDelivery(){
        System.out.println("Dilivery for "+ Name + " can be delivered at any time");
    }

    public ArrayList<ShippingAddress> getAddresses () {
        return new ArrayList<ShippingAddress>(Addresses);
    }

    public String getName () {
        return Name;
    }

    public int getCustomerID () {
        return customerID;
    }

    public void addAddress(ShippingAddress newAddress)
    {
        Addresses.add(newAddress);
    }
    @Override
    public String toString()
    {
        return "Customer Name: " + Name +"\nCustomerID: "+customerID + "\nWith "+Addresses.size() + " addresses on file";
    }


}
