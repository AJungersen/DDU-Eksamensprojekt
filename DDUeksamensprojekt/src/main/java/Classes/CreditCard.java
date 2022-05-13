/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;

/**
 *
 * @author chris
 */
public class CreditCard {
    private int creditCard_ID;
    private String experationDate;
    private String cardNumber;
    private String cvv;
    private String nameOfCardHolder;
    private String nameOfCard;

    public CreditCard(int creditCard_ID, String experationDate, String cardNumber, String cvv, String nameOfCardHolder, String nameOfCard) {
        this.creditCard_ID = creditCard_ID;
        this.experationDate = experationDate;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.nameOfCardHolder = nameOfCardHolder;
        this.nameOfCard = nameOfCard;
    }

    @Override
    public String toString() {
        return nameOfCard;
    }

    public int getCreditCard_ID() {
        return creditCard_ID;
    }

    public String getExperationDate() {
        return experationDate;
    }

    public void setExperationDate(String experationDate) {
        this.experationDate = experationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameOfCard() {
        return nameOfCard;
    }

    public void setNameOfCard(String nameOfCard) {
        this.nameOfCard = nameOfCard;
    }

    public String getNameOfCardHolder() {
        return nameOfCardHolder;
    }

    public void setNameOfCardHolder(String nameOfCardHolder) {
        this.nameOfCardHolder = nameOfCardHolder;
    }
}
