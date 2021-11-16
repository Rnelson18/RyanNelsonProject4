package com.example.ryannelsonproject4;
import java.util.ArrayList;
    public class Order {


        private ShippingAddress destination;
        private Customer orderedBy;
        ArrayList<MerchandiseItem> cartForOrder;

        public Order(ShippingAddress dest, Customer cust, ArrayList<MerchandiseItem> itemsOrdered)
        {
            destination = dest;
            orderedBy = cust;
            cartForOrder = itemsOrdered;
        }

        public String getDestination()
        {
            return destination.toString();
        }

        public String getOrderer()
        {
            return orderedBy.toString();
        }





    }
}
