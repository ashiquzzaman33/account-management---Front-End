/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.TrialBalance;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ReportTrialBalanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickShowTrialBalance(ActionEvent event) {
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/trialbalance").asJson();
            JSONArray array = res.getBody().getArray();
            Vector v = new Vector();
            HashMap params = new HashMap();
            float total_dr = 0, total_cr = 0;
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                String balance = obj.getString("balance");
                String dr, cr;
                if(Float.parseFloat(balance) < 0){
                    cr = balance;
                    dr = "";
                    total_cr += Float.parseFloat(cr);
                }else{
                    dr = balance;
                    total_dr += Float.parseFloat(dr);
                    cr = "";
                }
                v.add(new TrialBalance(name,dr,cr));
            }
            params.put("total_dr", String.valueOf(total_dr));
            params.put("total_cr", String.valueOf(total_cr));
            Report report = new Report();
            report.getReport("src\\report\\trialBalance.jrxml", new JRBeanCollectionDataSource(v), params);
            
        } catch (UnirestException ex) {
            Logger.getLogger(ReportTrialBalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
