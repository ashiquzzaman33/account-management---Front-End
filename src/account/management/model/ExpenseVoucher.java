/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author mohar
 */
public class ExpenseVoucher {
    private int id;
    private String date;
    private String location;
    private String rcv_name;
    private String rcv_address;
    private String via;
    private String via_address;
    private String total;
    private String word;
    private String details;

    public ExpenseVoucher(int id, String date, String location, String rcv_name, String rcv_address, String via, String via_address, String total, String word, String details) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.rcv_name = rcv_name;
        this.rcv_address = rcv_address;
        this.via = via;
        this.via_address = via_address;
        this.total = total;
        this.word = word;
        this.details = details;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRcv_name() {
        return rcv_name;
    }

    public void setRcv_name(String rcv_name) {
        this.rcv_name = rcv_name;
    }

    public String getRcv_address() {
        return rcv_address;
    }

    public void setRcv_address(String rcv_address) {
        this.rcv_address = rcv_address;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getVia_address() {
        return via_address;
    }

    public void setVia_address(String via_address) {
        this.via_address = via_address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
