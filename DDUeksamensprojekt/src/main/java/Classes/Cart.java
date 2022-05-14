/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.User;
import com.mycompany.ddueksamensprojekt.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author danie
 */
public class Cart {

    int cart_ID;
    private ArrayList<Product> products = new ArrayList<>();

    public Cart(int cart_ID, HashMap<Product, Integer> products) {
        this.cart_ID = cart_ID;
        for (Product p : products.keySet()) {
            for (int i = 0; i < products.get(p); i++) {
                this.products.add(p);
            }
        }
    }

    public Cart(int cart_ID, ArrayList<Product> products) {
        this.cart_ID = cart_ID;
        this.products = products;
    }

    public int getCart_ID() {
        return cart_ID;
    }

    public HashMap<Product, Integer> getProductsAsMap() {
        HashMap<Product, Integer> map = new HashMap<>();

        Comparator<Product> sortIDAcending = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getItem_ID() - o2.getItem_ID();
            }
        };

        products.sort(sortIDAcending);

        int numberOfCourentProduct = 1;
        Product prevItem = new Product();
        int arrayPos = 0;

        for (Product p : products) {
            if (p.getItem_ID() == prevItem.getItem_ID()) {
                numberOfCourentProduct++;
                
                if (arrayPos == (products.size() - 1)) {
                    System.out.println("hi");
                    map.put(p, numberOfCourentProduct);
                }
            } else if(arrayPos > 0){
                map.put(prevItem, numberOfCourentProduct);
                numberOfCourentProduct = 1;
            }

            arrayPos++;
            prevItem = p;
            /*Integer count = map.get(p);
            
            map.put(p, (count == null) ? 1 : count + 1);
             */
 /*if (map.size() > 0) {
                for (Product mp : map.keySet()) {
                    if (p.getItem_ID() == mp.getItem_ID()) {
                        temp.put(mp, map.get(mp) + 1);

                    } else {
                        temp.put(p, 1);
                    }

                }
                map = temp;
            } else {
                map.put(p, 1);
            }*/
        }
        return map;
    }

    public ArrayList<Product> getProductsAsList() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
