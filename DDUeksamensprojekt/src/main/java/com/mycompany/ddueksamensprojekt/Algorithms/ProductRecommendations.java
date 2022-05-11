/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt.Algorithms;

import Classes.Cart;
import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductScore;
import Classes.Purchase;
import com.mycompany.ddueksamensprojekt.App;
import java.util.ArrayList;
import repository.StoreDatabaseMethods;

/**
 *
 * @author danie
 */
public class ProductRecommendations {
    public ArrayList<Product> getBestProduct(Product userProduct, int size) throws Exception{
        StoreDatabaseMethods sdm = new StoreDatabaseMethods();
        ArrayList<Product> products = sdm.getAllProducts();
        ArrayList<ProductScore> rankedList = new ArrayList();
        ArrayList<Product> returnList = new ArrayList();
        System.out.println("products size er" + products.size());
        for(Product I: products){
            if(rankedList.isEmpty()){
                rankedList.add(new ProductScore(I,(getFitnessOff(I,userProduct))));
            } else {
            if(I.getItem_ID() != userProduct.getItem_ID()){
                ProductScore PS = new ProductScore(I,(getFitnessOff(I,userProduct)));
                for(int i = 0; i < rankedList.size(); i++){
                    if(PS.getScore() >= rankedList.get(i).getScore()){
                        rankedList.add(i, new ProductScore(PS.getI(),PS.getScore()));
                        break;
                    }
                }
            }
            }
        }System.out.println(rankedList.size());
        for(int i = 0; i < size; i++){
            returnList.add(rankedList.get(i).getI());
        }
        return returnList;
    }
    public float getFitnessOff(Product I, Product U) throws Exception{
        StoreDatabaseMethods sdm = new StoreDatabaseMethods();
        ArrayList<Purchase> userCarts = sdm.getAllUsersPurchases(App.getLoggedInUser().getUser_ID());
        ArrayList<Purchase> allCarts = sdm.getAllPurchase();
        int h = 0;
        int tot = 0;
        float uf = 0;
        float af = 0;
        if(userCarts.size() != 0){
            for(Purchase C: userCarts){
                tot += 1;
                if(C.getPurchasedProducts().containsKey(I) && C.getPurchasedProducts().containsKey(U)){
                h += 1;
                }
            }
        uf += h/tot;
        }
        h = 0;
        tot = 0;
        if(allCarts.size() != 0){
        for(Purchase C: allCarts){
            tot += 1;
            if(C.getPurchasedProducts().containsKey(I) && C.getPurchasedProducts().containsKey(U)){
                h += 1;
            }
        }
        af += h/tot;
        }
        // 10 skal erstattes af variabel
        float score = 10*uf+10*af;
        return score;
    }
}