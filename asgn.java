// NAME: VENKATA SAI SUVVARI
// ROLL: 20CS10067

import java.util.*;

/*

    Assumtions taken while writing the code:

    1. Product is manufactured by a single manufacturer.
    2. When the user enters the name and other elements of an entity, the id and name will be unique.
    3. The customer will always place an order for a single copy of a product.
    4. Product will be entered only after the manufacturer is created.
    5. When a customer orders a product, he will be assigned a shop that was created first (The one that is front in the arraylist of shops and contains the required product).
    6. The id of any entity is never negative.

*/
/*
The required elements or objects for each class.
entities{
    int id;
    sting name;
}
manufacturer{
    products [] list_of_products;
}
product{
    string name_of_manufacturer;
    manufacturer;
}
customers{
    int zipcode;
    products [] list_products;
}
delivery_agent{
    int zipcode;
    int no_of_products;
}
shops_n_warehouses{
    int zipcode;
    tuples of (product, count);`
}
*/

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
        System.out.println((i+1) + ") " + this.id + " " + this.name);
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

        for (int i = 0; i < this.prdcts.size(); i++) {
            prdcts.get(i).print_entities(i);
        }
        System.out.println();
    }
}

class customer extends entities{
    int zipcode;
    ArrayList<product> prdcts_buyed;
    
    customer(){
        this.id = -1;
        this.zipcode = 0;
        this.name = "name";
        this.prdcts_buyed = new ArrayList<product>();
    }
    
    customer(int id, String name, int zipcode){
        this.id = id;
        this.zipcode = zipcode;
        this.name = name;
        this.prdcts_buyed = new ArrayList<product>();
    }

    void add_prdct(product p){ // adds the product to the products list associated with customer
        this.prdcts_buyed.add(p);
    }

    void print_entities(){
        System.out.println("Id: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("The products bought: ");

        for (int i = 0; i < this.prdcts_buyed.size(); i++) {
            prdcts_buyed.get(i).print_entities(i);
        }
    }
}

class orders{
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
        this.zipcode = 0;
        this.inventory = new HashMap<product, Integer>();
        this.ords_to_prcss = new ArrayList<orders>();
    }
    shops(int id, String name, int zip){
        this.id = id;
        this.name = name;
        this.zipcode = zip;
        inventory = new HashMap<>();
    }
    void include_order(orders ord){
        this.ords_to_prcss.add(ord);
    }
    void process_order(ArrayList<delivery_agent> list_dlv, orders ord){
        delivery_agent temp_dl = new delivery_agent();
        temp_dl = ord.dlvr_agnt;
        list_dlv.remove(temp_dl);
        temp_dl.prdcts_dlvr = temp_dl.prdcts_dlvr + 1;
        list_dlv.add(temp_dl);
    }
}

class delivery_agent extends entities{
    int zipcode;
    int prdcts_dlvr;
    delivery_agent(){
        this.id = -1;
        this.name = "name";
        this.zipcode = 0;
        this.prdcts_dlvr = 0;
    }
    delivery_agent(int id, String name, int zip, int dlvrd){
        this.id = id;
        this.name = name;
        this.zipcode = zip;
        this.prdcts_dlvr = dlvrd;
    }
    void print_entities(){
        System.out.println(this.id + " " + this.name + "\n number of products delivered: " + this.prdcts_dlvr);
    }
}

class print_menu{
    void whole_menu(){
        System.out.println("Type 1 to work with entity Manufacture");
        System.out.println("Type 2 to work with entity Customer");
        // System.out.println("Type 3 to work with entity Products");
        System.out.println("Type 3 to work with entity Shops and Warehouses");
        System.out.println("Type 4 to work with entity Delivery Agent");
        System.out.println("Type 0 to Exit");
    }
    void mnfctr_menu(){
        System.out.println("Type 1 to Create a new Manufacturer");
        System.out.println("Type 2 to Delete a Manufacturer");
        System.out.println("Type 3 to Print the details of Manufacturers");
        System.out.println("Type 4 to Print the details of Products manufactured by a Manufacturer");
        System.out.println("Type 5 to add a Product to the Manufacturer");
        System.out.println("Type 6 to delete a Product from the Manufacturer");
    }
    void cstmr_menu(){
        System.out.println("Type 1 to Create a new Customer");
        System.out.println("Type 2 to Delete a Customer");
        System.out.println("Type 3 to Print the details of Customers");
        System.out.println("Type 4 to Print the Purchases made by Customer");
        System.out.println("Type 5 to add an order from Customer"); // This will add a product to the list of products to that customer.
    }
    // void prdcts_menu(){
    //     System.out.println("Type 1 to Create a new Products");
    //     System.out.println("Type 2 to Delete a Products");
    //     System.out.println("Type 3 to Print the details of a Products");
    // }
    void shops_menu(){
        System.out.println("Type 1 to Create a new Shop");
        System.out.println("Type 2 to Delete a Shop");
        System.out.println("Type 3 to Print the details of Shops");
        System.out.println("Type 4 to Print the Inventory a Shop");
        System.out.println("Type 5 to Add a certain number of copies of a Product to a Shop");
        System.out.println("Type 6 to Process an Order from a Shop"); // This will automatically choose a delivery agent and prints out his details
    }
    void dlvry_agnt_menu(){
        System.out.println("Type 1 to Create a new Delivery Agent");
        System.out.println("Type 2 to Delete a Delivery Agent");
        System.out.println("Type 3 to Print the details of Delivery Agents");
    }
}
public class asgn {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n\t\t*********** Welcome to Venkata Sai Enterprizes ***********\n\n");

        print_menu prnt = new print_menu();
        ArrayList<product> list_prdcts = new ArrayList<product>(); // this arraylist store the products that are currently manufactured
        ArrayList<product> list_prdcts2 = new ArrayList<product>(); // this arraylist stores the products that are created till now
        ArrayList<manufacturer> list_mnfctr = new ArrayList<manufacturer>();
        ArrayList<customer> list_cstmr = new ArrayList<customer>();
        ArrayList<shops> list_shops = new ArrayList<shops>();
        ArrayList<delivery_agent> list_dlvry_agnt = new ArrayList<delivery_agent>();

        int id_mnfct, id_prdct, id_cstmr, id_shops, id_dlvry_agnt;

        
        while (true) {
            prnt.whole_menu();
            int in1 = sc.nextInt();
            if (in1 == 0) {
                break;
            }
            switch (in1) {
                case 1->{
                    System.out.println("Enter the operation that you want to do");
                    prnt.mnfctr_menu();
                    int inp2 = sc.nextInt();
                    switch (inp2) {
                        case 1->{
                            System.out.println("Enter the name and id of the manufacturer");
                            String name = sc.next();
                            id_mnfct = sc.nextInt();
                            manufacturer temp = new manufacturer(id_mnfct, name);
                            list_mnfctr.add(temp);
                        }
                        
                        case 2->{
                            System.out.println("Enter the id of manufacturer which you want to delete");
                            manufacturer temp = new manufacturer();

                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }

                            int in3 = sc.nextInt();

                            for (manufacturer i : list_mnfctr) {
                                if (in3 == i.id) { // finding the entire manufacture entity with the given id
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
                        
                        case 3->{
                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }
                        }

                        case 4->{
                            System.out.println("Enter the name of the manufacturer for which you want to see details");
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
                        case 5->{
                            System.out.println("Enter the name of the manufacturer and name and id of the product to be added");
                            String name_mnf, name_pr;
                            name_mnf = sc.next();
                            name_pr = sc.next();
                            id_prdct = sc.nextInt();

                            Boolean b = true;

                            manufacturer temp_mn = new manufacturer();
                            for (manufacturer i : list_mnfctr) {
                                if (i.name.equals(name_mnf)) {
                                    temp_mn = i;
                                    b = false;
                                    break;
                                }
                            }

                            if (b){
                                System.out.println("The entered manufacturer was not created yet...\nCreating a new manufacurer...\nEnter the id of the manufacturer");
                                id_mnfct = sc.nextInt();
                                manufacturer nw_mnfct = new manufacturer(id_mnfct, name_mnf);
                                temp_mn = nw_mnfct;
                                list_mnfctr.add(nw_mnfct);
                            }

                            product temp_pr = new product(id_prdct, name_pr, name_mnf);
                            temp_mn.add_prdct(temp_pr);
                            list_prdcts.add(temp_pr);
                            list_prdcts2.add(temp_pr);
                        }
                        
                        case 6->{
                            System.out.println("Enter the name of the manufacturer and the name of the product that you want to delete");

                            for (manufacturer i : list_mnfctr) {
                                i.print_entities();
                            }

                            String name_mnf, name_pr;
                            name_mnf = sc.next();
                            name_pr = sc.next();
                            manufacturer temp_mn = new manufacturer();
                            for (manufacturer i : list_mnfctr) { // finding the entire manufacure entity with the given name
                                if (i.name.equals(name_mnf)) {
                                    temp_mn = i;
                                    break;
                                }
                            }

                            for (product i : temp_mn.prdcts) {
                                if (i.name.equals(name_pr)) {
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
                
                case 2->{
                    System.out.println("Enter the operation that you wanted to do");
                    prnt.cstmr_menu();
                    int in2 = sc.nextInt();
                    switch (in2) {
                        case 1->{
                            int zip;
                            String name;
                            System.out.println("Enter the name, id and zipcode of the customer");
                            zip = sc.nextInt();
                            id_cstmr = sc.nextInt();
                            name = sc.next();
                            customer temp = new customer(id_cstmr, name, zip);
                            list_cstmr.add(temp);
                        }
                        
                        case 2->{
                            System.out.println("Enter the id of the customer that you want to delete");

                            
                            for (customer i : list_cstmr) {
                                i.print_entities();
                            }
                            
                            int in3 = sc.nextInt();

                            for (customer i : list_cstmr) {
                                if (in3 == i.id) {
                                    i.prdcts_buyed.clear(); // clearing array of the products bought by the customer
                                    list_cstmr.remove(i);
                                    break;
                                }
                            }
                        }

                        case 3->{
                            for (customer iCustomer : list_cstmr) {
                                iCustomer.print_entities();
                            }
                        }

                        case 4->{
                            System.out.println("Enter the name of the customer for whom you want to see the purchases");
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
                        case 5->{
                            System.out.println("Enter your id and zipcode, and the id of the product that you wanted to buy");

                            for (int i = 0; i < list_prdcts.size(); i++) {
                                System.out.println((i+1) + ") " + list_prdcts.get(i).id + " " + list_prdcts.get(i).name);
                            }

                            int id_cst = sc.nextInt();
                            int zip = sc.nextInt();
                            int id_pr = sc.nextInt();

                            customer temp_Cst = new customer();

                            for (customer jCustomer : list_cstmr) {
                                if (id_cst == jCustomer.id) {
                                    temp_Cst = jCustomer;
                                    break;
                                }
                            }

                            System.out.println("Checking if this is product is available in your area...");

                            boolean b1 = true; // for checking if a shop is present with the same zipcode.
                            boolean b2 = true; // for checking if the required product is present in any shop with same zipcode.
                            boolean b3 = true; // for checking if any delivery agent with same zipcode is available.

                            product temp_pr = new product();
                            for (product jProduct : list_prdcts2) {
                                if (jProduct.id == id_pr) {
                                    temp_pr = jProduct;
                                    break;
                                }
                            }

                            int min_dlv = Integer.MAX_VALUE;
                            delivery_agent dlv_temp = new delivery_agent();
                            for (delivery_agent iAgent : list_dlvry_agnt) {
                                if (iAgent.zipcode == temp_Cst.zipcode) {
                                    if (iAgent.prdcts_dlvr < min_dlv) {
                                        min_dlv = iAgent.prdcts_dlvr;
                                        dlv_temp = iAgent;
                                    }
                                    b3 = false;
                                }
                            }

                            if (b3) {
                                System.out.println("Sorry!! No delivery agent is there in your locality, we will try to make sure you won't face this issue again");
                                continue;
                            }

                            shops temp_shop = new shops();
                            for (shops iShops : list_shops) {
                                if (iShops.zipcode == zip && b2) {
                                    temp_shop = iShops;
                                    b1 = false;
                                    b2 = temp_shop.inventory.containsKey(temp_pr);
                                    if (b2) {
                                        b2 = false;
                                        break;
                                    }
                                }
                            }
                            if (b1) {
                                System.out.println("Sorry!! There is no Shop or Warehouse present in your locality");
                                continue;
                            }
                            else if (b2) {
                                System.out.println("Sorry!! The product is not available in any shop in your locality, we try to correct this issue");
                                continue;
                            }

                            orders temp_ord = new orders();
                            temp_ord.dlvr_agnt = dlv_temp;
                            temp_ord.prdt_to_dlvr = temp_pr;
                            temp_shop.include_order(temp_ord);
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
        System.out.println("************ Thank You - Visit Again **************");
        sc.close();
    }
}
