/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt.Algorithms;

import Classes.Product;
import Classes.ProductScore;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class ProductRecommendations {
    public ArrayList<Product> getBestProduct(Product userProduct){
        ArrayList<Product> products = getListOfProducts();
        ArrayList<ProductScore> rankedList = new ArrayList();
        ArrayList<Product> returnList = new ArrayList();
        for(Product I: products){
            if(rankedList.isEmpty()){
                rankedList.add(new ProductScore(I,(getFitnessOff(I,userProduct))));
            } else {
            if(I.getProductID != userProduct.getProductID){
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
    public float getFitnessOff(Product I, Product U){
        
    }
}
