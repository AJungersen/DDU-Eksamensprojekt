package com.mycompany.ddueksamensprojekt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chris
 */
public class TableViewDispaly extends Product {

    private Integer amount;

    public TableViewDispaly(Integer amount, Product product) {
        super(product);
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
}
