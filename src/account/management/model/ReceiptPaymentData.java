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
public class ReceiptPaymentData {
    private String particulars;
    private String taka;
    private String total_taka;

    public ReceiptPaymentData(String particulars, String taka, String total_taka) {
        this.particulars = particulars;
        this.taka = taka;
        this.total_taka = total_taka;
    }
    
    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getTaka() {
        return taka;
    }

    public void setTaka(String taka) {
        this.taka = taka;
    }

    public String getTotal_taka() {
        return total_taka;
    }

    public void setTotal_taka(String total_taka) {
        this.total_taka = total_taka;
    }
}
