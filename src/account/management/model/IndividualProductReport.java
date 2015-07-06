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
public class IndividualProductReport {
    private String date;
    private String p_qty;
    private String p_rate;
    private String p_price;
    private String s_qty;
    private String s_rate;
    private String s_price;
    
    public IndividualProductReport(String date, String p_qty, String p_rate, String p_price, String s_qty, String s_rate, String s_price){
        this.date = date;
        this.p_qty = p_qty;
        this.p_rate = p_rate;
        this.p_price = p_price;
        this.s_qty = s_qty;
        this.s_rate = s_rate;
        this.s_price = s_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }

    public String getP_rate() {
        return p_rate;
    }

    public void setP_rate(String p_rate) {
        this.p_rate = p_rate;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getS_qty() {
        return s_qty;
    }

    public void setS_qty(String s_qty) {
        this.s_qty = s_qty;
    }

    public String getS_rate() {
        return s_rate;
    }

    public void setS_rate(String s_rate) {
        this.s_rate = s_rate;
    }

    public String getS_price() {
        return s_price;
    }

    public void setS_price(String s_price) {
        this.s_price = s_price;
    }
}
