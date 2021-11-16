package com.example.ryannelsonproject4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javax.imageio.IIOException;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public class Store {

        private ArrayList<Order> Orders;
        private ArrayList<Customer> Customers;
        private ArrayList<MerchandiseItem> Stock;
        private double revenue;

        //
        // Constructors
        //
        public Store () {
            Orders = new ArrayList<Order>();
            Customers = new ArrayList<Customer>();
            Stock = new ArrayList<MerchandiseItem>();
            revenue = 0.0;
        }

        //
        // Methods
        //




        public static void main(String[] args) throws IOException
        {
            var comp152Inc = new HelloApplication();
            comp152Inc.runStore();

        }

        /**
         */
        public void runStore() throws IOException
        {
            var inputReader = new Scanner(System.in);
            loadStartingCustomers(inputReader);
            loadStockItems();
            while(true){
                printMainMenu();
                var userChoice = inputReader.nextInt();
                switch (userChoice){
                    case 1:
                        addCustomer(inputReader);
                        break;
                    case 2:
                        var selectedCustomer =selectCustomer(inputReader);
                        if(selectedCustomer.isPresent())
                            manageCustomer(selectedCustomer.get());
                        break;
                    case 3:
                        System.exit(0);
                    case 4:
                        CollectOutstandingBalancesFromPurchaseOrders();
                        break;
                    case 5:
                        System.out.println("Our Company has collected $"+revenue + " in revenue so far");
                        break;
                    default:
                        System.out.println("\n%%%%%%Invalid selection, please choose one of the options from the menu%%%%%%\n");
                }
            }
        }

        private void CollectOutstandingBalancesFromPurchaseOrders() {
            for(var customer: Customers){
                var recentPayment = customer.payOutstandingBalance();
                revenue+= recentPayment;
            }
        }

        private void loadStockItems() {
            List<String> allLines = null;

            try {
                Path itemsFilePath = Paths.get("ItemsForSale.txt");
                allLines = Files.readAllLines(itemsFilePath);
            }
            catch (IOException e){
                System.out.println("Faled to read the items for sale file, Shutting down for now");
                System.exit(-1);
            }
            for(var entry: allLines){
                var entryValues = entry.split(",");
                switch (entryValues[2]){
                    case "1"->  thisItemsType = ItemType.WICFood;
                    case "2" -> thisItemsType= ItemType.Clothing;
                    case "3" -> thisItemsType = ItemType.GeneralMerchadise;
                }
                var price = Double.parseDouble(entryValues[1]);
                var newItem = new MerchandiseItem(entryValues[0], price, thisItemsType);
                Stock.add(newItem);
            }
        }

        private static void printMainMenu() {

            System.out.println("Welcome to the the 1980s Comp152 Store interface, what would you like to do?");
            System.out.println("   [1] Add Customer");
            System.out.println("   [2] Select Customer");
            System.out.println("   [3] Exit the program");
            System.out.println("   [4] Collect outstanding Balances from Purchase Orders");
            System.out.println("   [5] Show total revenue");
            System.out.print("Enter the number of your choice:");
        }




        private void loadStartingCustomers(Scanner inputReader) throws IOException {
            Path fullPathName;
            String filename;
            while(true) {
                System.out.print("Enter the name of the file to load customers:");
                filename = inputReader.nextLine();
                fullPathName = Paths.get(filename);
                if (!Files.exists(fullPathName)){
                    System.out.println("No file with that name, please try again....");
                }
                else
                    break;
            }

            var allLines = Files.readAllLines(fullPathName);
            Customer currentCustomer = null;
            for(var line: allLines){
                var splitLine = line.split(",");
                switch(splitLine[2]){
                    case "R"->{
                        currentCustomer = new ResidentialCustomer(splitLine[0], Integer.parseInt(splitLine[1]));
                    }
                    case "B"->{
                        currentCustomer = new BusinessCustomer(splitLine[0], Integer.parseInt(splitLine[1]));
                    }
                    case "T"->{
                        currentCustomer = new TaxExemptCustomer(splitLine[0], Integer.parseInt(splitLine[1]));
                    }
                    default -> {
                        throw new IOException("Bad file format - invalid customer type specified");
                    }
                }

                Customers.add(currentCustomer);
            }
        }


        /**
         * @param        address
         * @param        cust
         */
        public void makeOrder(ShippingAddress address, Customer cust, Scanner commandLineInput)
        {
            var cart = new ArrayList<MerchandiseItem>();
            System.out.println("Preparing to make order........");
            while(true){
                printStock();
                System.out.print("type the item number for your order. Select a negative number to end.");
                var choice = commandLineInput.nextInt();
                if (choice<0)
                    break;
                if(choice>=Stock.size())//error checking
                    continue;

                cart.add(Stock.get(choice));
            }

            var newOrder = new Order(address,cust,cart);
            Orders.add(newOrder);
            System.out.println(".......New order successfully created");
            revenue +=cust.payForOrder(cart);
            cust.arrangeDelivery();

        }

        private void printStock() {
            var count = 0;
            for(var itemForSale: Stock){
                System.out.println("["+count+"] "+itemForSale.getName()+" cost $"+itemForSale.getPrice());
                count++;
            }
        }


        /**
         */
        public void addCustomer(Scanner inputReader)
        {
            inputReader.nextLine();
            System.out.println("Adding Customer........");
            System.out.print("Enter the new Customers name:");
            var newName = inputReader.nextLine();
            System.out.println("What kind of customer is this? \n[1]Residential\n[2]Business,[3]Tax-exempt");
            var typeNum = inputReader.nextInt();
            switch (typeNum){
                case 1-> {
                    var newCustomer = new ResidentialCustomer(newName);
                    Customers.add(newCustomer);
                }
                case 2-> {
                    var newCustomer = new BusinessCustomer(newName);
                    Customers.add(newCustomer);
                }
                case 3->{
                    var newCustomer = new TaxExemptCustomer(newName);
                    Customers.add(newCustomer);
                }
            }

            System.out.println(".....Finished adding new Customer Record");
        }

        public Optional<Customer> selectCustomer(Scanner reader)
        {
            System.out.print("Enter the ID of the customer to select:");
            var enteredID = reader.nextInt();
            for(var currentCustomer: Customers){
                if(currentCustomer.getCustomerID()==enteredID)
                    return Optional.of(currentCustomer);
            }
            System.out.println("==========================> No customer with customer ID:"+enteredID);
            return Optional.empty();
        }

        public void manageCustomer(Customer selectedCustomer)
        {
            Scanner secondScanner = new Scanner(System.in);
            while(true){
                printCustomerMenu(selectedCustomer.getName());
                var userChoice = secondScanner.nextInt();
                switch (userChoice){
                    case 1 ->addAddress(secondScanner, selectedCustomer);
                    case 2->{
                        ShippingAddress selectedAddress = pickAddress(secondScanner,selectedCustomer);
                        makeOrder(selectedAddress,selectedCustomer, secondScanner);
                    }
                    case 3-> {return;}
                    default->System.out.println("Invalid option selected");
                }
            }
        }

        private ShippingAddress pickAddress(Scanner secondScanner, Customer selectedCustomer) {
            var customerAddresses = selectedCustomer.getAddresses();
            if (customerAddresses.size() ==0){
                System.out.println("This customer has no addresses on file, please add an address");
                addAddress(secondScanner,selectedCustomer);
                return selectedCustomer.getAddresses().get(0);
            }
            var count = 0;

            System.out.println("Please select a shipping address from those the customer has on file");
            for (var address : customerAddresses) {
                System.out.print("[" + count + "]");
                System.out.println(address.toString());
                count++;
            }
            System.out.print("Enter the number of the address for this order:");
            var addressNum = secondScanner.nextInt();
            if (addressNum >= customerAddresses.size()){
                System.out.println("Invalid entry, defaulting to the first address on file...");
                return customerAddresses.get(0);
            }
            else
                return customerAddresses.get(addressNum);
        }

        private void addAddress(Scanner secondScanner, Customer selectedCustomer) {
            System.out.println("Adding new address for "+ selectedCustomer.getName());
            secondScanner.nextLine();
            System.out.print("Enter Address Line 1:");
            var line1 = secondScanner.nextLine();
            System.out.print("Enter Address Line 2 or <enter> if there is none:");
            var line2 = secondScanner.nextLine();
            System.out.print("Enter the address City:");
            var city = secondScanner.nextLine();
            System.out.print("Enter address state:");
            var state = secondScanner.nextLine();
            System.out.print("Enter the postal code:");
            var postCode = secondScanner.nextLine();
            var newAddress  = new ShippingAddress(line1,line2,city,state,postCode);
            selectedCustomer.addAddress(newAddress);
        }

        private void printCustomerMenu(String custName) {
            System.out.println("What do you want to do for Customer " + custName+"?");
            System.out.println("   [1] Add Address to customer");
            System.out.println("   [2] Make an order for the customer");
            System.out.println("   [3] return to the main menu");
            System.out.print("Enter the number of your choice:");
        }


    }
}
