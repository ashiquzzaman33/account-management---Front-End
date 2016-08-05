/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.model;

/**
 *
 * @author unitech
 */
public class Voucher {
    private String id;
    private String date;
    private String location;
    private String narration;
    private String voucher_type;

    public Voucher(String id,String date, String location,String narration, String voucher_type){
        this.id = id;
        this.date = date;
        this.location = location;
        this.narration = narration;
        this.voucher_type = voucher_type;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the voucher_type
     */
    public String getVoucher_type() {
        return voucher_type;
    }

    /**
     * @param voucher_type the voucher_type to set
     */
    public void setVoucher_type(String voucher_type) {
        this.voucher_type = voucher_type;
    }
}
