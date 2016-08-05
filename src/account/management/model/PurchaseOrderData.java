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
public class PurchaseOrderData {
    private String sl;
    private String desc;
    private String qty;
    private String rate;
    private String total;
    private String commision;
    private String total_commision;
    private String neat_amount;

    public PurchaseOrderData(String sl, String desc, String qty, String rate, String total, String commision, String total_commision, String neat_amount) {
        this.sl = sl;
        this.desc = desc;
        this.qty = qty;
        this.rate = rate;
        this.total = total;
        this.commision = commision;
        this.total_commision = total_commision;
        this.neat_amount = neat_amount;
    }
    
    
    
    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getTotal_commision() {
        return total_commision;
    }

    public void setTotal_commision(String total_commision) {
        this.total_commision = total_commision;
    }

    public String getNeat_amount() {
        return neat_amount;
    }

    public void setNeat_amount(String neat_amount) {
        this.neat_amount = neat_amount;
    }
}
