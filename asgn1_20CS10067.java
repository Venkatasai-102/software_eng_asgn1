// NAME: VENKATA SAI SUVVARI
// ROLL: 20CS10067

import java.util.*;

/*

    Assumtions taken while writing the code:

    1.  Product is manufactured by a single manufacturer.
    2.  When the user enters the name and other elements of an entity, the id and name will be unique.
    3.  The customer will always place an order for a single copy of a product.
    4.  Product will be entered only after the manufacturer is created, else the manufacturer will be created instantaneously and add product to it.
    5.  When a customer orders a product, he will be assigned a shop that was created first and has the product available (The one that is front in the 
        arraylist of shops and contains the required product).
    6.  The id and zipcode of any entity is never negative.
    7.  When user asks for process order, the first pending order will be processed.
    8.  The delivery agent with lowest deliveries is assigned a new delivery according to unprocessed orders, i.e., when a customer adds product successfully the 
        count of number of deliveries done increases.
*** 9.  There are no spaces in name of any entity.(**** IMPORTANT ****)
    10. Customer's order will be confirmed only if there is an available shop with the required product and delivery agent is present in locality, otherwise,
        customer will be promted a message saying that he can't order that.
    11. If there one to be deleted, enter some random number to continue for the further operations.
    12. User enters the id for every entity he creates.

*/

// this class is inherited by other classes for the elements id and name
class entities{
    int id;
    String name;
}

class product extends entities{ // assuming that a product is made by a single manufacturer

    String mnfctr;
    product(){
        this.id = -1;
        this.name = "name";
        this.mnfctr = "mnfctr";
    }
    product(int id, String name, String mnfctr){
        this.id = id;
        this.mnfctr = mnfctr;
        this.name = name;
    }
    void print_entities(int i){
        System.out.println((i+1) + ") " + this.id + " " + this.name); // prints in the format of "s.no) id name"
    }
    
    void print_prpty(){
        System.out.println(this.id + " " + this.name); // prints id and name
    }
}

class manufacturer extends entities{

    ArrayList<product> prdcts;
    
    manufacturer(){
        this.id = -1;
        this.name = "mnfctr";
        this.prdcts = new ArrayList<product>();
    }
    manufacturer(int id, String name){
        this.id = id;
        this.name = name;
        this.prdcts = new ArrayList<product>();
    }
    void add_prdct(product p){ // adds the products to the products list associated with the manufacturer
        this.prdcts.add(p);
    }
    void print_entities(){
        System.out.println("Id: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("The products manufactured: ");

        for (int i = 0; i < this.prdcts.size(); i++) { // prints products manufactured by that particular manufacturer currently. 
            prdcts.get(i).print_entities(i);
        }
        System.out.println();
    }
}

class customer extends entities{
    int zipcode;
    ArrayList<product> prdcts_bought;
    
    customer(){
        this.id = -1;
        this.zipcode = -1;
        this.name = "name";
        this.prdcts_bought = new ArrayList<product>();
    }
    
    customer(int id, String name, int zipcode){
        this.id = id;
        this.zipcode = zipcode;
        this.name = name;
        this.prdcts_bought = new ArrayList<product>();
    }

    void add_prdct(product p, delivery_agent temp_dl, ArrayList<delivery_agent> list_dlv){ // adds the product to the products list associated with customer
        
        this.prdcts_bought.add(p);
        // updating the number of products delivered by a delivery agent
        list_dlv.remove(temp_dl);
        temp_dl.prdcts_dlvr = temp_dl.prdcts_dlvr + 1;
        list_dlv.add(temp_dl);
    }

    void print_entities(){
        System.out.println("Id: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("The products bought: ");

        for (int i = 0; i < this.prdcts_bought.size(); i++) {
            prdcts_bought.get(i).print_entities(i);
        }
        System.out.println();
    }
}

class orders{ // this is for storing the orders to be processed by a shop
    product prdt_to_dlvr;
    delivery_agent dlvr_agnt;
    orders(){
        this.prdt_to_dlvr = new product();
        this.dlvr_agnt = new delivery_agent();
    }
    orders(product pr, delivery_agent dlv){
        this.prdt_to_dlvr = pr;
        this.dlvr_agnt = dlv;
    }
}

class shops extends entities{
    int zipcode;
    HashMap<product, Integer> inventory;
    ArrayList<orders> ords_to_prcss;

    shops(){
        this.id = -1;
        this.name = "name";
        this.zipcode = -1;
        this.inventory = new HashMap<product, Integer>();
        this.ords_to_prcss = new ArrayList<orders>();
    }
    shops(int id, String name, int zip){
        this.id = id;
        this.name = name;
        this.zipcode = zip;
        inventory = new HashMap<>();
        this.ords_to_prcss = new ArrayList<orders>();
    }
    void print_entities(ArrayList<shops> list_sh){ // prints shops name and its properties
        int i = 1;
        for (shops iShops1 : list_sh) {
            System.out.println(i + ") " + iShops1.id + " " + iShops1.name + " " + iShops1.zipcode + "\nThe inventory of shop:");
            for (Map.Entry<product, Integer> i_inv : iShops1.inventory.entrySet()) {
                System.out.print("Product: ");
                i_inv.getKey().print_prpty();
                System.out.println("Copies: " + i_inv.getValue());
            }
            i ++;
        }
        System.out.println();
    }
    void include_order(orders ord){ // when a customer orders a product in this shop, it will get added to the shop's list of orders to be processed
        this.ords_to_prcss.add(ord);
    }
    void process_order(){ // this processes the first order pending in it's list of orders to be processed
        if (this.ords_to_prcss.size() == 0) {
            System.out.println("Well Done!! There are no processes to be processed!\n");
            return;
        }
        orders ord = this.ords_to_prcss.get(0);

        this.ords_to_prcss.remove(ord);

        int num_copies = this.inventory.get(ord.prdt_to_dlvr) - 1; // the copies in the inventory of the shop is reduced

        if (num_copies == 0) {
            this.inventory.remove(ord.prdt_to_dlvr); // if the number of copies of the given product becomes 0, remove it from the shop's list of available products
        }
        else{
            this.inventory.put(ord.prdt_to_dlvr, num_copies); // else update the number of copies in the inventory of shop
        }

        
    }
    void add_prdcts(product p, int num_copies){ // the new products of required number of copies will be added to the shop from the manufacturer
        boolean b1 = this.inventory.containsKey(p);
        if (b1) {
            num_copies = num_copies + this.inventory.get(p);
        }
        this.inventory.put(p, num_copies);
    }
}

class delivery_agent extends entities{
    int zipcode;
    int prdcts_dlvr;
    delivery_agent(){
        this.id = -1;
        this.name = "name";
        this.zipcode = -1;
        this.prdcts_dlvr = 0;
    }
    delivery_agent(int id, String name, int zip){
        this.id = id;
        this.name = name;
        this.zipcode = zip;
        this.prdcts_dlvr = 0;
    }
    void print_entities(){
        System.out.println("Id: " + this.id + "\nName: " + this.name + "\nNumber of products delivered: " + this.prdcts_dlvr);
    }
}

class print_menu{ // prints menu for required entity when called
    void whole_menu(){
        System.out.println("\nType 1 to work with entity Manufacturer");
        System.out.println("Type 2 to work with entity Customer");
        System.out.println("Type 3 to work with entity Shops and Warehouses");
        System.out.println("Type 4 to work with entity Delivery Agent");
        System.out.println("Type 0 to Exit\n");
    }
    void mnfctr_menu(){
        System.out.println("\nType 1 to Create a new Manufacturer");
        System.out.println("Type 2 to Delete a Manufacturer");
        System.out.println("Type 3 to Print the details of Manufacturers");
        System.out.println("Type 4 to Print the details of Products manufactured by a Manufacturer");
        System.out.println("Type 5 to add a Product to the Manufacturer");
        System.out.println("Type 6 to delete a Product from the Manufacturer\n");
    }
    void cstmr_menu(){
        System.out.println("\nType 1 to Create a new Customer");
        System.out.println("Type 2 to Delete a Customer");
        System.out.println("Type 3 to Print the details of Customers");
        System.out.println("Type 4 to Print the Purchases made by Customer");
        System.out.println("Type 5 to add an order from Customer\n"); // This will add a product to the list of products to that customer.
    }
    void shops_menu(){
        System.out.println("\nType 1 to Create a new Shop");
        System.out.println("Type 2 to Delete a Shop");
        System.out.println("Type 3 to Print the details of Shops");
        System.out.println("Type 4 to Print the Inventory a Shop");
        System.out.println("Type 5 to Add a certain number of copies of a Product to a Shop");
        System.out.println("Type 6 to Process an Order from a Shop\n"); // This will automatically choose a delivery agent.
    }
    void dlvry_agnt_menu(){
        System.out.println("\nType 1 to Create a new Delivery Agent");
        System.out.println("Type 2 to Delete a Delivery Agent");
        System.out.println("Type 3 to Print the details of Delivery Agents\n");
    }
}

public class asgn1_20CS10067 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n\t\t***********  Welcome to Venkata Sai Medical Shop  ***********\n\n");

        print_menu prnt = new print_menu();
        ArrayList<product> list_prdcts = new ArrayList<product>(); // this arraylist store the products that are currently manufactured
        ArrayList<product> list_prdcts2 = new ArrayList<product>(); // this arraylist stores the products that are created till now
        ArrayList<manufacturer> list_mnfctr = new ArrayList<manufacturer>(); // this arraylist stores the manufacturers those are currently active
        ArrayList<customer> list_cstmr = new ArrayList<customer>(); // this arraylist stores the customers those are currently active
        ArrayList<shops> list_shops = new ArrayList<shops>(); // this arraylist stores the shops those are currently working
        ArrayList<delivery_agent> list_dlvry_agnt = new ArrayList<delivery_agent>(); // this arraylist stores the delivery agents currently working

        int id_mnfct, id_prdct, id_cstmr, id_shops, id_dlvry_agnt;

        
        while (true) { // this while loop runs till the user enters 0
            prnt.whole_menu();
            int in1 = sc.nextInt();
            if (in1 == 0) {
                break;
            }

            switch (in1) {
                case 1->{ // working with manufacturer
                    System.out.println("\nEnter the operation that you want to do");
                    prnt.mnfctr_menu();
                    int inp2 = sc.nextInt();
                    switch (inp2) {
                        case 1->{ // adding a new manufacturer
                            System.out.println("\nEnter the name and id of the manufacturer");
                            String name = sc.next();
                            id_mnfct = sc.nextInt();

                            boolean b1 = false;
                            
                            for (manufacturer iManufacturer1 : list_mnfctr){
                                if (list_mnfctr.size() != 0 && iManufacturer1.id == id_mnfct) {
                                    System.out.println("\nThe manufacturer with id already exist, you can't create this manufacturer again!!\n");
                                    b1 = true;
                                    break;
                                }
                            }

                            if(b1) { // if manufacturer already exist.
                                continue;
                            }

                            manufacturer temp = new manufacturer(id_mnfct, name);
                            list_mnfctr.add(temp);
                            System.out.println("\n\t\t\t*** New Manufacturer created Successfully ***\n");
                        }
                        
                        case 2->{ // delete a manufacturer
                            System.out.println("\nEnter the id of manufacturer which you want to delete");
                            manufacturer temp = new manufacturer();

                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }

                            int in3 = sc.nextInt();

                            for (manufacturer i : list_mnfctr) {
                                if (in3 == i.id) { // finding the entire manufacture entity list with the given id
                                    temp = i;
                                    break;
                                }
                            }

                            for (product i : temp.prdcts) {
                                list_prdcts.remove(i); // removing the products from product arraylist in which the currently manufactured products are present
                            }

                            temp.prdcts.clear(); // clearing the products array in the given manufacturer
                            list_mnfctr.remove(temp); // removing the manufacturer to be deleted from the manufacture arraylist
                        }
                        
                        case 3->{ // printing the details of all manufacturers
                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }
                        }

                        case 4->{ // printing the details of a particular manufacturer
                            System.out.println("\nEnter the name of the manufacturer for which you want to see details");
                            for (manufacturer i : list_mnfctr) {
                                System.out.println(i.name);
                            }

                            String name = sc.next();

                            for (manufacturer i : list_mnfctr) {
                                if (name.equals(i.name)) {
                                    i.print_entities();
                                    break;
                                }
                            }
                        }
                        case 5->{ // to add a new product to the manufacturer
                            System.out.println("\nEnter the id of the manufacturer and name and id of the product to be added!!");
                            String name_mnf, name_pr;

                            System.out.println("The id and name of the manufacturers present are: ");
                            int k = 1;
                            for (manufacturer iManufacturer1 : list_mnfctr) {
                                System.out.println(k + ") " + iManufacturer1.id + "  " + iManufacturer1.name);
                                k ++;
                            }

                            id_mnfct = sc.nextInt();
                            name_pr = sc.next();
                            id_prdct = sc.nextInt();

                            boolean b1 = false;

                            for (product iProduct2 : list_prdcts2) {
                                if (list_prdcts2.size() != 0 && iProduct2.id == id_prdct) {
                                    b1 = true;
                                    break;
                                }
                            }

                            if(b1) {
                                System.out.println("\nThe product has been manufactured by another manufacuter, so you can't add this product\n");
                                continue;
                            }

                            Boolean b = true;

                            manufacturer temp_mn = new manufacturer();
                            for (manufacturer i : list_mnfctr) {
                                if (i.id == id_mnfct) {
                                    temp_mn = i;
                                    b = false;
                                    break;
                                }
                            }

                            if (b){
                                System.out.println("\nThe entered manufacturer was not created yet...\nCreating a new manufacurer...\nEnter the name of the manufacturer\n");
                                name_mnf = sc.next();
                                manufacturer nw_mnfct = new manufacturer(id_mnfct, name_mnf);
                                temp_mn = nw_mnfct;
                                list_mnfctr.add(nw_mnfct);
                            }

                            product temp_pr = new product(id_prdct, name_pr, temp_mn.name);
                            temp_mn.add_prdct(temp_pr);
                            list_prdcts.add(temp_pr); // adding the new product to the arraylist in which currently active products are stored
                            list_prdcts2.add(temp_pr); // adding the new product to the arraylist in which all products are stored
                            System.out.println("\n\n\t\t\t*** Successfully created a new product and added it to the manufacturer ***\n");
                        }
                        
                        case 6->{ // delete a product from the manufacturer
                            System.out.println("\nEnter the id of the manufacturer and the id of the product that you want to delete");

                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }

                            id_mnfct = sc.nextInt();
                            id_prdct = sc.nextInt();
                            manufacturer temp_mn = new manufacturer();
                            for (manufacturer i : list_mnfctr) { // finding the entire manufacure entity list with the given name
                                if (i.id == id_mnfct) {
                                    temp_mn = i;
                                    break;
                                }
                            }

                            for (product i : temp_mn.prdcts) { // removing the products from products list storing the currently active products
                                if (i.id == id_prdct) {
                                    temp_mn.prdcts.remove(i);
                                    list_prdcts.remove(i);
                                    break;
                                }
                            }
                        }

                        default->{
                            System.out.println("Invalid Input");
                        }
                    }
                }
                
                case 2->{ // working with customer
                    System.out.println("\nEnter the operation that you wanted to do");
                    prnt.cstmr_menu();
                    int in2 = sc.nextInt();
                    switch (in2) {
                        case 1->{ // creating a new customer
                            int zip;
                            String name;
                            System.out.println("\nEnter the name, id and zipcode of the customer");
                            name = sc.next();
                            id_cstmr = sc.nextInt();
                            zip = sc.nextInt();

                            boolean b1 = false;

                            for (customer iCustomer : list_cstmr) {
                                if (list_cstmr.size() != 0 && iCustomer.id == id_cstmr) {
                                    b1 = true;
                                    break;
                                }
                            }
                            
                            if (b1) {
                                System.out.println("\nThere is a customer with same id, you can't enter this again!!\n");
                                continue;
                            }
                            customer temp = new customer(id_cstmr, name, zip);
                            list_cstmr.add(temp);
                            System.out.println("\n\t\t\t*** New Customer created Successfully ***\n");
                        }
                        
                        case 2->{ // deleting a customer
                            System.out.println("\nEnter the id of the customer that you want to delete");

                            
                            for (customer i : list_cstmr) {
                                i.print_entities();
                            }
                            
                            int in3 = sc.nextInt();

                            for (customer i : list_cstmr) {
                                if (in3 == i.id) {
                                    i.prdcts_bought.clear(); // clearing array of the products bought by the customer
                                    list_cstmr.remove(i);
                                    break;
                                }
                            }
                        }

                        case 3->{ // printing details of all customers
                            for (customer iCustomer : list_cstmr) {
                                iCustomer.print_entities();
                            }
                        }

                        case 4->{ // to check the purchases of a paticular customer
                            System.out.println("\nEnter the name of the customer for whom you want to see the purchases");
                            for (customer iCustomer : list_cstmr) {
                                System.out.println(iCustomer.name);
                            }

                            String name = sc.next();

                            for (customer iCustomer : list_cstmr) {
                                if (name.equals(iCustomer.name)) {
                                    iCustomer.print_entities();
                                    break;
                                }
                            }
                        }

                        case 5->{ // add a product to the customer
                            System.out.println("\nEnter your id and the id of the product that you wanted to buy");


                            for (int i = 0; i < list_prdcts2.size(); i++) {
                                System.out.println((i+1) + ") " + list_prdcts.get(i).id + " " + list_prdcts.get(i).name);
                            }

                            int id_cst = sc.nextInt();
                            int id_pr = sc.nextInt();

                            customer temp_Cst = new customer();

                            for (customer jCustomer : list_cstmr) { // getting the customer entity
                                if (id_cst == jCustomer.id) {
                                    temp_Cst = jCustomer;
                                    break;
                                }
                            }

                            System.out.println("\n\nChecking if this is product is available in your area...\n\n");

                            boolean b1 = true; // for checking if a shop is present with the same zipcode.
                            boolean b2 = true; // for checking if the required product is present in any shop with same zipcode.
                            boolean b3 = true; // for checking if any delivery agent with same zipcode is available.

                            product temp_pr = new product();
                            for (product jProduct : list_prdcts2) { // getting the product entity from the product arraylist storing all the products.
                                if (jProduct.id == id_pr) {
                                    temp_pr = jProduct;
                                    break;
                                }
                            }

                            int min_dlv = Integer.MAX_VALUE;
                            delivery_agent dlv_temp = new delivery_agent();
                            
                            // checking if a delivery agent is present with same zipcode, if yes, then storing the delivery agent with least number of deliveries
                            for (delivery_agent iAgent : list_dlvry_agnt) { 
                                if (iAgent.zipcode == temp_Cst.zipcode) {
                                    if (iAgent.prdcts_dlvr < min_dlv) {
                                        min_dlv = iAgent.prdcts_dlvr;
                                        dlv_temp = iAgent;
                                    }
                                    b3 = false;
                                }
                            }

                            if (b3) { // if no delivery agent
                                System.out.println("\nSorry!! No delivery agent is there in your locality, we will try to make sure you won't face this issue again\n");
                                continue;
                            }

                            shops temp_shop = new shops();
                            for (shops iShops : list_shops) { // checking the shop that is enrolled first and has the product
                                if (iShops.zipcode == temp_Cst.zipcode) {
                                    temp_shop = iShops;
                                    b1 = false;
                                    if (temp_shop.inventory.containsKey(temp_pr)) {
                                        b2 = false;
                                        break;
                                    }
                                }
                            }

                            if (b1) { // if no shop with same zipcode is present
                                System.out.println("\nSorry!! There is no Shop or Warehouse present in your locality\n");
                                continue;
                            }
                            else if (b2) { // if product does not exist in any local shop
                                System.out.println("\nSorry!! The product is not available in any shop in your locality, we try to correct this issue\n");
                                continue;
                            }

                            temp_Cst.add_prdct(temp_pr, dlv_temp, list_dlvry_agnt);
                            orders temp_ord = new orders();
                            temp_ord.dlvr_agnt = dlv_temp;
                            temp_ord.prdt_to_dlvr = temp_pr;
                            temp_shop.include_order(temp_ord);
                            System.out.println("\n\n*** Product added to bought list Successfully ***\n\n");
                        }
                        default->{
                            System.out.println("Invalid input");
                        }
                    }
                }

                case 3->{ // working with the shops
                    System.out.println("\nEnter the operation that you wanted to do");
                    prnt.shops_menu();
                    int in2 = sc.nextInt();
                    switch (in2) {
                        case 1->{ // adding a new shop
                            System.out.println("\nEnter id, name and zipcode of the shop");
                            id_shops = sc.nextInt();
                            String name = sc.next();
                            int zip = sc.nextInt();

                            boolean b1 = false;

                            for (shops iShops1 : list_shops){
                                if (list_shops.size() != 0 && iShops1.id == id_shops) {
                                    System.out.println("\n\nThere is a shop with same id, you can't enter this again!!\n\n");
                                    b1 = true;
                                    break;
                                }
                            }

                            if (b1) {
                                continue;
                            }

                            shops temp_sh = new shops(id_shops, name, zip);
                            list_shops.add(temp_sh);
                            System.out.println("\n\n\t\t\t*** New Shop created Successfully ***\n\n");
                        }
                        
                        case 2->{ // deleting a shop
                            shops temp_shops = new shops();
                            System.out.println("\nEnter the id of the shop you want to delete");
                            temp_shops.print_entities(list_shops);

                            int id_sh = sc.nextInt();

                            for (shops iShops : list_shops) {
                                if (iShops.id == id_sh) {
                                    temp_shops = iShops;
                                    break;
                                }
                            }

                            temp_shops.ords_to_prcss.clear();
                            temp_shops.inventory.clear();
                            list_shops.remove(temp_shops);
                        }

                        case 3->{ // printing details of all shops
                            shops temp_sh = new shops();
                            temp_sh.print_entities(list_shops);
                        }

                        case 4->{ // printing inventory of a particular shop
                            System.out.println("\nEnter the id of the shop for which you want to print the list of inventory");

                            System.out.println("The list of shops: ");
                            for (shops iShops1 : list_shops){
                                System.out.println(iShops1.id + "  " + iShops1.name);
                            }
                            
                            int id_sh = sc.nextInt();
                            shops temp_sh = new shops();

                            for (shops iShops : list_shops) {
                                if (id_sh == iShops.id) {
                                    temp_sh = iShops;
                                    break;
                                }
                            }

                            System.out.println("\nThe inventory of the shop is:");

                            for (Map.Entry<product, Integer> i_inv : temp_sh.inventory.entrySet()) {
                                System.out.print("Product: ");
                                i_inv.getKey().print_prpty();
                                System.out.println("Copies: " + i_inv.getValue());
                            }
                            System.out.println();
                        }

                        case 5->{ // adding some number of copies of a particular product to a particular shop
                            shops temp_sh = new shops();
                            product temp_pr = new product();

                            System.out.println("\nEnter the id of the shop for which you want to enter the product, id of the product and number of copies of that product");

                            int k = 0;
                            System.out.println("Printing the available products to be added: ");
                            for (product iProduct1 : list_prdcts){
                                iProduct1.print_entities(k);
                                k ++;
                            }

                            System.out.println("\n\nThe list of shops along with their inventories are: ");
                            temp_sh.print_entities(list_shops);

                            int id_sh = sc.nextInt();
                            int id_pr = sc.nextInt();
                            int copies = sc.nextInt();

                            for (shops iShops : list_shops) {
                                if (id_sh == iShops.id) {
                                    temp_sh = iShops;
                                    break;
                                }
                            }

                            boolean b1 = true;
                            for (product i_pr : list_prdcts) {
                                if (id_pr == i_pr.id) {
                                    temp_pr = i_pr;
                                    b1 = false;
                                    break;
                                }
                            }

                            if (b1){
                                System.out.println("\n\nThe required product is not being manufactured!! You can't add it to shops\n\n");
                                continue;
                            }
                            temp_sh.add_prdcts(temp_pr, copies);
                        }

                        case 6->{ // process the first pending order of a particular shop
                            shops temp_sh = new shops();

                            System.out.println("\nEnter the id of the shop for which you want to process the first order present in its pending delivery list");
                            temp_sh.print_entities(list_shops);

                            int id_sh = sc.nextInt();
                            for (shops iShops : list_shops) {
                                if (iShops.id == id_sh) {
                                    temp_sh = iShops;
                                    break;
                                }
                            }

                            temp_sh.process_order();
                        }
                        default->{
                            System.out.println("Invalid input");
                        }
                    }
                }

                case 4->{ // working with delivery agent
                    System.out.println("\nEnter the operation that you wanted to do");
                    prnt.dlvry_agnt_menu();
                    int in2 = sc.nextInt();
                    switch (in2) {
                        case 1->{ // adding a new delivery agent
                            System.out.println("\nEnter the id, name and zipcode of the delivery agent");
                            id_dlvry_agnt = sc.nextInt();
                            String name = sc.next();
                            int zip = sc.nextInt();
                            
                            boolean b1 = false;
                            for (delivery_agent iAgent1 : list_dlvry_agnt) {
                                if (list_dlvry_agnt.size() != 0 && iAgent1.id == id_dlvry_agnt) {
                                    System.out.println("\n\nThere is a delivery agent with same id, So you can't add\n\n");
                                    b1 = true;
                                    break;
                                }
                            }

                            if (b1) {
                                continue;
                            }
                            delivery_agent temp_agnt = new delivery_agent(id_dlvry_agnt, name, zip);
                            list_dlvry_agnt.add(temp_agnt);
                            System.out.println("\n\n\t\t\t*** New Delivery agent created Sucessfully ***\n\n");
                        }
                        
                        case 2->{ // deleting a delivery agent
                            System.out.println("\nEnter the id of the delivery agent whom you want to delete");
                            for (delivery_agent iAgent : list_dlvry_agnt) {
                                iAgent.print_entities();
                            }

                            int id_dl = sc.nextInt();

                            delivery_agent temp_dlv = new delivery_agent();

                            for (delivery_agent iAgent : list_dlvry_agnt) {
                                if (id_dl == iAgent.id) {
                                    temp_dlv = iAgent;
                                }
                            }

                            list_dlvry_agnt.remove(temp_dlv);
                        }

                        case 3->{ // printing deltails of all delivery agent
                            for (delivery_agent iAgent : list_dlvry_agnt) {
                                iAgent.print_entities();
                            }
                        }

                        default->{
                            System.out.println("Invalid input");
                        }
                    }
                }

                default->{
                    System.out.println("Invalid Input");
                }
            }
        }
        System.out.println("\n\n\t\t************ Thank You - Visit Again **************");
        sc.close();
    }
}
