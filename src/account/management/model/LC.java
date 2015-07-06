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
public class LC {
    private long id;
    private String lc_no;
    private String party_name, party_address, party_bank, our_bank,lc_amount, init_date, start_date, dimilish_date;
    
    public LC(long id, String lc_no,String party_name,String party_address,String party_bank,String our_bank,String lc_amount,String init_date,String start_date,String dimilish_date){
        this.id  =id;
        this.lc_no = lc_no;
        this.party_name = party_name;
        this.party_address = party_address;
        this.party_bank = party_bank;
        this.our_bank = our_bank;
        this.lc_amount = lc_amount;
        this.init_date = init_date;
        this.start_date = start_date;
        this.dimilish_date = dimilish_date;
    }

    public String getLc_no() {
        return lc_no;
    }

    public void setLc_no(String lc_no) {
        this.lc_no = lc_no;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }
    
    @Override
    public String toString(){
        return this.lc_no + " - " + this.getParty_name();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParty_address() {
        return party_address;
    }

    public void setParty_address(String party_address) {
        this.party_address = party_address;
    }

    public String getParty_bank() {
        return party_bank;
    }

    public void setParty_bank(String party_bank) {
        this.party_bank = party_bank;
    }

    public String getOur_bank() {
        return our_bank;
    }

    public void setOur_bank(String our_bank) {
        this.our_bank = our_bank;
    }

    public String getLc_amount() {
        return lc_amount;
    }

    public void setLc_amount(String lc_amount) {
        this.lc_amount = lc_amount;
    }

    public String getInit_date() {
        return init_date;
    }

    public void setInit_date(String init_date) {
        this.init_date = init_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDimilish_date() {
        return dimilish_date;
    }

    public void setDimilish_date(String dimilish_date) {
        this.dimilish_date = dimilish_date;
    }
    
}
