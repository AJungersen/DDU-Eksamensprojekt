/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Wallet {
    private int wallet_ID;
    private int fonds;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<Coupon> cupons;

    public Wallet(int wallet_ID, int fonds, ArrayList<CreditCard> creditCards, ArrayList<Coupon> cupons) {
        this.wallet_ID = wallet_ID;
        this.fonds = fonds;
        this.creditCards = creditCards;
        this.cupons = cupons;
    }

    public int getWallet_ID() {
        return wallet_ID;
    }

    public int getFonds() {
        return fonds;
    }

    public void setFonds(int fonds) {
        this.fonds = fonds;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public ArrayList<Coupon> getCupons() {
        return cupons;
    }

    public void setCupons(ArrayList<Coupon> cupons) {
        this.cupons = cupons;
    }
}
