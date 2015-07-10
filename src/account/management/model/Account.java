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
public class Account {
    private String name;
    private int id;
    private int parent;
    private String description;
    private float openingBalance;
    private String account_type;
    
    public Account(int id, String name, int parent, String description, float opening_balance){
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.description = description;
        this.openingBalance = opening_balance;
    }
    public Account(int id, String name, int parent, String description, float opening_balance, String acc_type){
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.description = description;
        this.openingBalance = opening_balance;
        this.account_type = acc_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
        return name;
    }

    public float getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(float openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
    
}
