/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.EnglishNumberToWords;
import account.management.model.MetaData;
import account.management.model.Msg;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
public class SalaryVoucherController implements Initializable {
    @FXML
    private DatePicker date;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(()->{
            try {
                Thread.sleep(5000);
                this.amount_words.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                  if (keyEvent.getCode() == KeyCode.ENTER) {
                      System.out.println("attempting to submit deposit voucher");
                   //Stop letting it do anything else
                   keyEvent.consume();
                      try {
                          onSubmitButtonClick(null);
                      } catch (ParseException ex) {
                          Logger.getLogger(SalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
                }
               });
            } catch (InterruptedException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }).start();
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
            
            JSONArray res = Unirest.get(MetaData.baseUrl + "add/salary/voucher")
                    .queryString("date",this.date.getValue().toString())
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
                Report report = new Report();
                Vector v = new Vector();
                HashMap params = new HashMap();
                String id = Unirest.get(MetaData.baseUrl + "salary/voucher/lastId").asString().getBody();
                params.put("voucher_no", id);
                params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString())));
                params.put("section", this.section.getText());
                params.put("name", this.name.getText());
                params.put("basis_on_date", this.basis_date.getText());
                params.put("amount", this.amount_words.getText());
                params.put("1", String.valueOf(this.basic_salary.getText()));
                params.put("2", String.valueOf(this.presence.getText()));
                params.put("3", String.valueOf(this.total1.getText()));
                params.put("4", String.valueOf(this.others.getText()));
                params.put("5", String.valueOf(this.total2.getText()));
                params.put("6", String.valueOf(this.advance.getText()));
                params.put("7", String.valueOf(this.fine.getText()));
                params.put("8", String.valueOf(this.apron.getText()));
                params.put("9", String.valueOf(this.deduction.getText()));
                params.put("10", String.valueOf(this.grand_total.getText()));
                v.add("a");
                report.getReport("src\\report\\SalaryVoucher.jrxml", new JRBeanCollectionDataSource(v), params, "Salary Slip");
            }else{
                Msg.showError("" + obj.getString("Message"));
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(SalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
    }
    
}
