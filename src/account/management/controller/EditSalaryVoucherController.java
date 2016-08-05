/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Data;
import account.management.model.EnglishNumberToWords;
import account.management.model.MetaData;
import account.management.model.Msg;
import account.management.model.SalaryVoucher;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class EditSalaryVoucherController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField section;
    @FXML
    private TextField name;
    @FXML
    private TextField basis_date;
    @FXML
    private TextField basic_salary;
    @FXML
    private TextField presence;
    @FXML
    private TextField total1;
    @FXML
    private TextField others;
    @FXML
    private TextField total2;
    @FXML
    private TextField advance;
    @FXML
    private TextField fine;
    @FXML
    private TextField apron;
    @FXML
    private TextField deduction;
    @FXML
    private TextField grand_total;
    @FXML
    private TextField amount_words;
    SalaryVoucher v;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        v = Data.salaryVoucher;
        this.section.setText(v.getSection());
        this.name.setText(v.getName());
        this.basis_date.setText(v.getBasis());
        this.amount_words.setText(v.getAmount_word());
        this.basic_salary.setText(String.valueOf(v.getBasic()));
        this.presence.setText(String.valueOf(v.getPresence()));
        this.total1.setText(String.valueOf(v.getTotal()));
        this.others.setText(String.valueOf(v.getOther()));
        this.total2.setText(String.valueOf(v.getTotal2()));
        this.advance.setText(String.valueOf(v.getAdvance()));
        this.fine.setText(String.valueOf(v.getFine()));
        this.apron.setText(String.valueOf(v.getApron()));
        this.deduction.setText(String.valueOf(v.getDeduction()));
        this.grand_total.setText(String.valueOf(v.getTotal()));
    }    
    
    @FXML
    private void autoCalculate(KeyEvent event) {
        double a = Double.parseDouble(this.basic_salary.getText().isEmpty() ? "0" : this.basic_salary.getText());
        double b = Double.parseDouble(this.presence.getText().isEmpty() ? "0" : this.presence.getText());
        double c = a*b;
        double d = Double.parseDouble(this.others.getText().isEmpty() ? "0" : this.others.getText());
        double e = c+d;
        double f = Double.parseDouble(this.advance.getText().isEmpty() ? "0" : this.advance.getText());
        double g = Double.parseDouble(this.fine.getText().isEmpty() ? "0" : this.fine.getText());
        double h = Double.parseDouble(this.apron.getText().isEmpty() ? "0" : this.apron.getText());
        double i = Double.parseDouble(this.deduction.getText().isEmpty() ? "0" : this.deduction.getText());
        double total = e-f-g-h-i;
        
        this.total1.setText(String.valueOf(c));
        this.total2.setText(String.valueOf(e));
        this.grand_total.setText(String.valueOf(total));
        this.amount_words.setText(EnglishNumberToWords.convert((long) total) + " taka only");
        
    }
    
    
    @FXML
    private void onSubmitButtonClick(ActionEvent event) throws ParseException {
        try {
            
            JSONArray res = Unirest.get(MetaData.baseUrl + "edit/salary/voucher")
                    .queryString("id",v.getId())
                    .queryString("date",v.getDate())
                    .queryString("section",this.section.getText())
                    .queryString("party_name",this.name.getText())
                    .queryString("basis_on_or_date",this.basis_date.getText())
                    .queryString("amount_in_words",this.amount_words.getText())
                    .queryString("basic_salary",Double.parseDouble(this.basic_salary.getText()))
                    .queryString("presence",Double.parseDouble(this.presence.getText()))
                    .queryString("total1",Double.parseDouble(this.total1.getText()))
                    .queryString("others",Double.parseDouble(this.others.getText()))
                    .queryString("total2",Double.parseDouble(this.total2.getText()))
                    .queryString("advance",Double.parseDouble(this.advance.getText()))
                    .queryString("fine",Double.parseDouble(this.fine.getText()))
                    .queryString("apron_or_mask",Double.parseDouble(this.apron.getText()))
                    .queryString("other_deduction",Double.parseDouble(this.deduction.getText()))
                    .queryString("grand_total",Double.parseDouble(this.grand_total.getText()))
                    .asJson().getBody().getArray();
            System.out.println(res);
            JSONObject obj = res.getJSONObject(0);
            if(obj.getString("Status").equals("Success")){
                Msg.showInformation("Success");
                v.setSection(this.section.getText());
                v.setName(this.name.getText());
                v.setBasis(this.basis_date.getText());
                v.setAmount_word(this.amount_words.getText());
                v.setBasic(Double.parseDouble(this.basic_salary.getText()));
                v.setPresence(Double.parseDouble(this.presence.getText()));
                v.setTotal1(Double.parseDouble(this.total1.getText()));
                v.setOther(Double.parseDouble(this.others.getText()));
                v.setTotal2(Double.parseDouble(this.total2.getText()));
                v.setAdvance(Double.parseDouble(this.advance.getText()));
                v.setFine(Double.parseDouble(this.fine.getText()));
                v.setApron(Double.parseDouble(this.apron.getText()));
                v.setDeduction(Double.parseDouble(this.deduction.getText()));
                v.setTotal(Double.parseDouble(this.grand_total.getText()));
                Data.salaryVoucher = v;
                
                
            }else{
                Msg.showError("" + obj.getString("Message"));
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(SalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
    }

    @FXML
    private void onPrintButtonClick(ActionEvent event) throws ParseException {
        Report report = new Report();
        Vector v = new Vector();
        HashMap params = new HashMap();
        params.put("voucher_no", String.valueOf(Data.salaryVoucher.getId()));
        params.put("date", Data.salaryVoucher.getDate());
        params.put("section", Data.salaryVoucher.getSection());
        params.put("name", Data.salaryVoucher.getName());
        params.put("basis_on_date", Data.salaryVoucher.getBasis());
        params.put("amount", Data.salaryVoucher.getAmount_word());
        params.put("1", String.valueOf(Data.salaryVoucher.getBasic()));
        params.put("2", String.valueOf(Data.salaryVoucher.getPresence()));
        params.put("3", String.valueOf(Data.salaryVoucher.getTotal1()));
        params.put("4", String.valueOf(Data.salaryVoucher.getOther()));
        params.put("5", String.valueOf(Data.salaryVoucher.getTotal2()));
        params.put("6", String.valueOf(Data.salaryVoucher.getAdvance()));
        params.put("7", String.valueOf(Data.salaryVoucher.getFine()));
        params.put("8", String.valueOf(Data.salaryVoucher.getApron()));
        params.put("9", String.valueOf(Data.salaryVoucher.getDeduction()));
        params.put("10", String.valueOf(Data.salaryVoucher.getTotal()));
        v.add("a");
        report.getReport("src\\report\\SalaryVoucher.jrxml", new JRBeanCollectionDataSource(v), params, "Salary Slip");
    }
        
}
