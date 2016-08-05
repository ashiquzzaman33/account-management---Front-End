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
public class PartDetailsReport {
    private String name;
    private String opening_balance;
    private String dr;
    private String cr;
    private String balance;
    
    public PartDetailsReport(String name, String opening_balance, String dr, String cr, String balance){
        this.name = name;
        this.opening_balance = String.valueOf(Float.parseFloat(opening_balance));
        this.dr = String.valueOf(Float.parseFloat(dr));
        this.cr = String.valueOf(Float.parseFloat(cr));
        this.balance = String.valueOf(Float.parseFloat(balance));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(String opening_balance) {
        this.opening_balance = opening_balance;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    
    
}
