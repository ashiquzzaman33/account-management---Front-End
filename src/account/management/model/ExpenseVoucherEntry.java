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
public class ExpenseVoucherEntry {
    private String sl;
    private String desc;
    private String amount;

    public ExpenseVoucherEntry(String sl, String desc, String amount) {
        this.sl = sl;
        this.desc = desc;
        this.amount = amount;
    }
    
    
    
    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
