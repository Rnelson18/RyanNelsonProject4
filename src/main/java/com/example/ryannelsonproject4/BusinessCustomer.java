package com.example.ryannelsonproject4;

import java.util.ArrayList;

public class BusinessCustomer extends Customer{
    private double purchaseOrderBalance;

    public BusinessCustomer(String Name, int ID){
        super(Name,ID);
        purchaseOrderBalance = 0;
    }

    public BusinessCustomer(String name){
        super(name);
        purchaseOrderBalance = 0;
    }

    @Override
    public double payForOrder(ArrayList<MerchandiseItem> itemsInCart){
        var total = 0.0;
        for(var item : itemsInCart){
            switch (item.getTaxibleType()){
                case WICFood -> {
                    total += item.getPrice();
                }
                case GeneralMerchadise -> {
                    var price = item.getPrice();
                    var tax = price*0.0625;
                    total+=price+tax;
                }
                case Clothing -> {
                    var price = item.getPrice();
                    var tax = 0.0;
                    if(price>175){
                        var taxablePrice = price-175;
                        tax = taxablePrice*0.0625;
                    }
                    total += price+tax;
                }
            }
        }
        purchaseOrderBalance += total;
        return 0.0;
    }

    @Override
    public double payOutstandingBalance(){
        if(purchaseOrderBalance > 1000)
            purchaseOrderBalance = purchaseOrderBalance*.95; //apply the discount if applicable
        var payvalue = purchaseOrderBalance;
        purchaseOrderBalance = 0;
        return payvalue;
    }

    @Override
    public void arrangeDelivery(){
        System.out.println("All Deliveries for "+ getName() + " must be from 9am to 5pm Monday through Friday");
    }

}
