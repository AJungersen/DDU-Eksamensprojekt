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
    private LocalDate experationDate;
    private String cardNumber;
    private String cvv;

    public CreditCard(int creditCard_ID, LocalDate experationDate, String cardNumber, String cvv) {
        this.creditCard_ID = creditCard_ID;
        this.experationDate = experationDate;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public int getCreditCard_ID() {
        return creditCard_ID;
    }

    public LocalDate getExperationDate() {
        return experationDate;
    }

    public void setExperationDate(LocalDate experationDate) {
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
}
