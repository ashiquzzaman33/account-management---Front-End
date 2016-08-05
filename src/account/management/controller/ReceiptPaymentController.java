/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.ReceiptPaymentData;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ReceiptPaymentController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) throws ParseException {
        String start = this.start.getValue().toString();
        String end = this.end.getValue().toString();
        
        try {
            JSONObject res = Unirest.get(MetaData.baseUrl + "report/financial/statement/with/date")
                    .queryString("start_date", start)
                    .queryString("end_date", end)
                    .asJson().getBody().getObject();
            
            Report report = new Report();
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("title1", "Financial Statement for the period ended " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end)));
            params.put("title2", "Receipt And Payment Account From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start)) +" to " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end)));
            params.put("receipt_from_trade_debtors", String.valueOf(Double.parseDouble(res.getString("receipts_from_trade_debtors"))));
            params.put("other_income", String.valueOf(Double.parseDouble(res.getString("other_income"))));
            params.put("total_receipts", String.valueOf(Double.parseDouble(res.getString("receipts_from_trade_debtors")) +  Double.parseDouble(res.getString("other_income"))));
            
            JSONArray array = res.getJSONArray("payments");
            int len = array.length();
            double total_payments = 0;
            for (int i = 0; i < len; i++) {
                JSONObject obj = array.getJSONObject(i);
                v.add(new ReceiptPaymentData(obj.getString("name"),"", String.valueOf(obj.getDouble("balance"))));
                total_payments += obj.getDouble("balance");
            }
            params.put("total_payments", String.valueOf(total_payments));
            params.put("excess_of_payments", String.valueOf(Double.parseDouble((String) params.get("total_receipts")) - total_payments));
            params.put("cash_in_hand_opening_title", "Cash in hand at " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start)));
            params.put("cash_at_bank_opening_title", "Cash at bank at " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start)));
            params.put("cash_in_hand_closing_title", "Cash in hand at " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end)));
            params.put("cash_at_bank_closing_title", "Cash at bank at " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end)));
            params.put("cash_in_hand_opening", res.getString("cash_in_hand_opening"));
            params.put("cash_at_bank_opening", res.getString("cash_at_bank_opening"));
            params.put("cash_in_hand_closing", String.valueOf(Double.parseDouble(res.getString("cash_in_hand_closing"))));
            params.put("cash_at_bank_closing", String.valueOf(Double.parseDouble(res.getString("cash_at_bank_closing"))));
            params.put("total", String.valueOf(Double.parseDouble((String) params.get("excess_of_payments")) + Double.parseDouble(res.getString("cash_in_hand_opening")) + Double.parseDouble(res.getString("cash_at_bank_opening")))  );
            
            report.getReport("src\\report\\ReceiptPayments.jrxml", new JRBeanCollectionDataSource(v), params, "Receipt Payment");
            
            
        } catch (UnirestException ex) {
            Logger.getLogger(ReceiptPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
