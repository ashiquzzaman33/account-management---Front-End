/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.model;

/**
 *
 * @author mohar
 */
public class SellPurchaseLedger {
    private String date;
    private String quantity;
    private String rate;
    private String total;
    public SellPurchaseLedger(String date, String quantity, String rate){
        this.date = date;
        this.quantity = quantity;
        this.rate = rate;
        this.total = String.valueOf(Float.parseFloat(quantity) * Float.parseFloat(rate));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
