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
    private int funds;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<Coupon> cupons;

    public Wallet() {
    }
    
    public Wallet(int wallet_ID, int funds, ArrayList<CreditCard> creditCards, ArrayList<Coupon> cupons) {
        this.wallet_ID = wallet_ID;
        this.funds = funds;
        this.creditCards = creditCards;
        this.cupons = cupons;
    }

    public int getWallet_ID() {
        return wallet_ID;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int fonds) {
        this.funds = fonds;
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
