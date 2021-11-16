package com.example.ryannelsonproject4;

import java.util.ArrayList;

public class TaxExemptCustomer extends Customer{

    public TaxExemptCustomer(String Name, int id){
        super(Name,id);
    }

    public TaxExemptCustomer(String name){
        super(name);
    }

    @Override
    public double payForOrder(ArrayList<MerchandiseItem> itemsInOrder){
        var total = 0.0;
        for (var item : itemsInOrder){
            total += item.getPrice(); //with no tax this is the easy one.
        }
        return total;
    }

    @Override
    public void arrangeDelivery(){
        System.out.println("Please contact "+ getName() + " on the day of delivery");
    }
}
