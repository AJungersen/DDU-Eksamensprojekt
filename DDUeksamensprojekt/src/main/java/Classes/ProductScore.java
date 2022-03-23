/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Product;

/**
 *
 * @author danie
 */
public class ProductScore {
    Product I;
    float score;

    public ProductScore(Product I, float score) {
        this.I = I;
        this.score = score;
    }

    public Product getI() {
        return I;
    }

    public float getScore() {
        return score;
    }

    public void setI(Product I) {
        this.I = I;
    }

    public void setScore(float score) {
        this.score = score;
    }
    
}
