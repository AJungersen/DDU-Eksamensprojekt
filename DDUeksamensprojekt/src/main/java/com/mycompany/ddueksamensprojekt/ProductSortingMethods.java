/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import com.mycompany.ddueksamensprojekt.Product;
import java.util.Comparator;

/**
 *
 * @author Christoffer
 */
public class ProductSortingMethods {
    private String name;
    Comparator<Product> comparator;

    public ProductSortingMethods(String name, Comparator<Product> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public String getName() {
        return name;
    }

    public Comparator<Product> getComparator() {
        return comparator;
    }
}
