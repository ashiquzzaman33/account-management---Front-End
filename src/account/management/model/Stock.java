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
public class Stock {
    private String id;
    private String name;
    private String opening_qty;
    private String opening_rate;
    private String opening_total;
    private String closing_quantity;
    private String closing_rate;
    private String closing_total;
    private String totla_p_qty;
    private String total_s_qty;
    private String opening_price;
    private String total_p_price;
    private String total_s_price;
    
    public Stock(String id, String name, String opening_qty, String opening_price, String total_p_qty, String total_p_price,String total_s_qty, String total_s_price){
        this.id = id;
        this.name = name;
        this.opening_qty = opening_qty;
        this.opening_price = opening_price;
        this.totla_p_qty = total_p_qty;
        this.total_p_price = total_p_price;
        this.total_s_qty = total_s_qty;
        this.total_s_price = total_s_price;
    }
    
    public Stock(int a, String id, String name, String opening_qty,String opening_rate, String opening_total, String closing_qty, String closing_rate, String closing_total){
        this.id = id;
        this.name = name;
        this.opening_qty = opening_qty;
        this.opening_rate = opening_rate;
        this.opening_total = opening_total;
        this.closing_quantity = closing_qty;
        this.closing_rate = closing_rate;
        this.closing_total = closing_total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpening_qty() {
        return opening_qty;
    }

    public void setOpening_qty(String opening_qty) {
        this.opening_qty = opening_qty;
    }

    public String getTotla_p_qty() {
        return totla_p_qty;
    }

    public void setTotla_p_qty(String totla_p_qty) {
        this.totla_p_qty = totla_p_qty;
    }

    public String getTotal_s_qty() {
        return total_s_qty;
    }

    public void setTotal_s_qty(String total_s_qty) {
        this.total_s_qty = total_s_qty;
    }

    public String getOpening_price() {
        return opening_price;
    }

    public void setOpening_price(String opening_price) {
        this.opening_price = opening_price;
    }

    public String getTotal_p_price() {
        return total_p_price;
    }

    public void setTotal_p_price(String total_p_price) {
        this.total_p_price = total_p_price;
    }

    public String getTotal_s_price() {
        return total_s_price;
    }

    public void setTotal_s_price(String total_s_price) {
        this.total_s_price = total_s_price;
    }

    public String getOpening_rate() {
        return opening_rate;
    }

    public void setOpening_rate(String opening_rate) {
        this.opening_rate = opening_rate;
    }

    public String getOpening_total() {
        return opening_total;
    }

    public void setOpening_total(String opening_total) {
        this.opening_total = opening_total;
    }

    public String getClosing_quantity() {
        return closing_quantity;
    }

    public void setClosing_quantity(String closing_quantity) {
        this.closing_quantity = closing_quantity;
    }

    public String getClosing_rate() {
        return closing_rate;
    }

    public void setClosing_rate(String closing_rate) {
        this.closing_rate = closing_rate;
    }

    public String getClosing_total() {
        return closing_total;
    }

    public void setClosing_total(String closing_total) {
        this.closing_total = closing_total;
    }
    
}
