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
public class DebitVoucher {
    private int id;
    private String date;
    private String details;
    private String debitors;
    private String bank;
    private String branch;
    private String address;
    private String amount;
    private String type;
    private String note;
    private String word;

    public DebitVoucher(int id, String date, String details, String debitors, String bank, String branch, String address, String amount, String type, String note, String word) {
        this.id = id;
        this.date = date;
        this.details = details;
        this.debitors = debitors;
        this.bank = bank;
        this.branch = branch;
        this.address = address;
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.word = word;
    }
    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDebitors() {
        return debitors;
    }

    public void setDebitors(String debitors) {
        this.debitors = debitors;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
}
