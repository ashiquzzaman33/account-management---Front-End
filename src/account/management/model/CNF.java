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
public class CNF {
    private String id;
    private String party_name;
    private String party_address;
    
    public CNF(String id, String name, String address){
        this.id = id;
        this.party_name = name;
        this.party_address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getParty_address() {
        return party_address;
    }

    public void setParty_address(String party_address) {
        this.party_address = party_address;
    }
    
    @Override
    public String toString(){
        return this.party_name;
    }
    
}
