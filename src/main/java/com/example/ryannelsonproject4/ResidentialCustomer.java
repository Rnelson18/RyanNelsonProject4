package com.example.ryannelsonproject4;

import java.util.ArrayList;

public class ResidentialCustomer extends Customer{

    public ResidentialCustomer(String nomen, int id){
        super(nomen,id);
    }
    public ResidentialCustomer(String name){
        super(name);
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
        return total;
    }

}
