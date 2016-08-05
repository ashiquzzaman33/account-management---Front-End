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
public class SalaryVoucher {
    private int id;
    private String date;
    private String section;
    private String name;
    private String basis;
    private String amount_word;
    private double basic;
    private double presence;
    private double total1;
    private double other;
    private double total2;
    private double advance;
    private double fine;
    private double apron;
    private double deduction;
    private double total;

    public SalaryVoucher(int id, String date, String section, String name, String basis, String amount_word, double basic, double presence, double total1, double other, double total2, double advance, double fine, double apron, double deduction, double total) {
        this.id = id;
        this.date = date;
        this.section = section;
        this.name = name;
        this.basis = basis;
        this.amount_word = amount_word;
        this.basic = basic;
        this.presence = presence;
        this.total1 = total1;
        this.other = other;
        this.total2 = total2;
        this.advance = advance;
        this.fine = fine;
        this.apron = apron;
        this.deduction = deduction;
        this.total = total;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getAmount_word() {
        return amount_word;
    }

    public void setAmount_word(String amount_word) {
        this.amount_word = amount_word;
    }

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    public double getPresence() {
        return presence;
    }

    public void setPresence(double presence) {
        this.presence = presence;
    }

    public double getTotal1() {
        return total1;
    }

    public void setTotal1(double total1) {
        this.total1 = total1;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getTotal2() {
        return total2;
    }

    public void setTotal2(double total2) {
        this.total2 = total2;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public double getApron() {
        return apron;
    }

    public void setApron(double apron) {
        this.apron = apron;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
