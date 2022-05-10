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
import java.util.ArrayList;
import repository.StoreDatabaseMethods;

/**
 *
 * @author danie
 */
public class ProductRecommendations {
    public ArrayList<Product> getBestProduct(Product userProduct) throws Exception{
        StoreDatabaseMethods sdm = new StoreDatabaseMethods();
        ArrayList<Product> products = sdm.getAllProducts();
        ArrayList<ProductScore> rankedList = new ArrayList();
        ArrayList<Product> returnList = new ArrayList();
        for(Product I: products){
            if(rankedList.isEmpty()){
                rankedList.add(new ProductScore(I,(getFitnessOff(I,userProduct))));
            } else {
            if(I.getItem_ID() != userProduct.getItem_ID()){
                ProductScore PS = new ProductScore(I,(getFitnessOff(I,userProduct)));
                for(ProductScore P: rankedList){
                    if(PS.getScore() > P.getScore()){
                        rankedList.add(new ProductScore(PS.getI(),PS.getScore()));
                        break;
                    }
                }}
            }
        }
        for(int i = 0; i < 5; i++){
            returnList.add(rankedList.get(i).getI());
        }
        return returnList;
    }
    public float getFitnessOff(Product I, Product U) throws Exception{
        StoreDatabaseMethods sdm = new StoreDatabaseMethods();
        ArrayList<Purchase> userCarts = sdm.getAllUserPurchase();
        ArrayList<Purchase> allCarts = sdm.getAllPurchase();
        int h = 0;
        int tot = 0;
        for(Purchase C: userCarts){
            tot += 1;
            if(C.getPurchasedProducts().containsKey(I) && C.getPurchasedProducts().containsKey(U)){
                h += 1;
            }
        }
        float uf = h/tot;
        h = 0;
        tot = 0;
        for(Purchase C: allCarts){
            tot += 1;
            if(C.getPurchasedProducts().containsKey(I) && C.getPurchasedProducts().containsKey(U)){
                h += 1;
            }
        }
        float af = h/tot;
        // 10 skal erstattes af variabel
        float score = 10*uf+10*af;
        return score;
    }
}