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
public class AllProductWiseReport {
    private String id;
    private String name;
    private String ob;
    private String p_qty;
    private String p_rate;
    private String p_total;
    private String s_qty;
    private String s_rate;
    private String s_total;
    public AllProductWiseReport(String id,String name,String ob,String p_qty,String p_rate,String p_total,String s_qty,String s_rate,String s_total){
        this.id = id;
        this.name = name;
        this.ob = ob;
        this.p_qty = p_qty;
        this.p_rate = p_rate;
        this.p_total = p_total;
        this.s_qty = s_qty;
        this.s_rate = s_rate;
        this.s_total = s_total;
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

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
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

    public String getP_total() {
        return p_total;
    }

    public void setP_total(String p_total) {
        this.p_total = p_total;
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

    public String getS_total() {
        return s_total;
    }

    public void setS_total(String s_total) {
        this.s_total = s_total;
    }
}
