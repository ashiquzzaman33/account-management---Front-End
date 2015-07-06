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
public class Product {
    private int id;
    private String name;
    private float p_qty;
    private float s_qty;
    private double last_p_rate;
    private double last_s_rate;
    private double avg_p_rate;
    private double avg_s_rate;
    
    public Product(int id, String name, float p_qty, float s_qty, double last_p_rate, double last_s_rate, double avg_p_rate, double avg_s_rate){
        this.id = id;
        this.name = name;
        this.p_qty = p_qty;
        this.s_qty = s_qty;
        this.last_p_rate = last_p_rate;
        this.last_s_rate = last_s_rate;
        this.avg_p_rate = avg_p_rate;
        this.avg_s_rate = avg_s_rate;
    }
    
    public Product(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getP_qty() {
        return p_qty;
    }

    public void setP_qty(float p_qty) {
        this.p_qty = p_qty;
    }

    public float getS_qty() {
        return s_qty;
    }

    public void setS_qty(float s_qty) {
        this.s_qty = s_qty;
    }

    public double getLast_p_rate() {
        return last_p_rate;
    }

    public void setLast_p_rate(double last_p_rate) {
        this.last_p_rate = last_p_rate;
    }

    public double getLast_s_rate() {
        return last_s_rate;
    }

    public void setLast_s_rate(double last_s_rate) {
        this.last_s_rate = last_s_rate;
    }

    public double getAvg_p_rate() {
        return avg_p_rate;
    }

    public void setAvg_p_rate(double avg_p_rate) {
        this.avg_p_rate = avg_p_rate;
    }

    public double getAvg_s_rate() {
        return avg_s_rate;
    }

    public void setAvg_s_rate(double avg_s_rate) {
        this.avg_s_rate = avg_s_rate;
    }

    @Override
    public String toString(){
        return this.getName();
    }
    
}
