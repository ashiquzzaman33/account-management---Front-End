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
public class Project {
    private String id;
    private String name;
    private String investment;
    private String party;
    private String start_date;
    private String operation_date;
    private String dimilish_date;
    
    public Project(String id, String name){
        this.id = id;
        this.name = name;
        
    }
    
    public Project(String id, String name,String invetment,String party,String start_date,String operation_date,String dimilis_date){
        this.id = id;
        this.name = name;
        this.investment = invetment;
        this.party = party;
        this.start_date = start_date;
        this.operation_date = operation_date;
        this.dimilish_date = dimilis_date;
        
    }

    @Override
    public String toString(){
        return this.getName();
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

    public String getInvestment() {
        return investment;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(String operation_date) {
        this.operation_date = operation_date;
    }

    public String getDimilish_date() {
        return dimilish_date;
    }

    public void setDimilish_date(String dimilish_date) {
        this.dimilish_date = dimilish_date;
    }
    
}
