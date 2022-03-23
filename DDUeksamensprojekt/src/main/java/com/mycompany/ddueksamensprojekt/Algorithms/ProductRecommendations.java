/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt.Algorithms;

import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class ProductRecommendations {
    public ArrayList<Item> getBestItem(Item userItem){
        ArrayList<Item> items = getListOfItems();
        ArrayList<Item> rankedList = new ArrayList();
        for(Item I: items){
            if(I.getItemID != userItem.getItemID){
                I.setScore(getFitnessOff(I,userItem));
                
            }
        }
    }
    public float getFitnessOff(Item I, Item U){
        
    }
}
