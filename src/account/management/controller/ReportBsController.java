/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.BS;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
public class ReportBsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public List<Account> account = FXCollections.observableArrayList();;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/accounts").asJson();
            JSONArray acc = res.getBody().getArray();
            for(int i=0; i<acc.length(); i++){
                JSONObject obj = acc.getJSONObject(i);
                this.account.add(new Account(obj.getInt("id"), obj.getString("name"), 0,"",0.0f));
            }
        } catch (UnirestException ex) {
            Logger.getLogger(ReportBsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void onShowBalanceSheetClick(ActionEvent event) {
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/balancesheet").asJson();
            JSONArray bs = res.getBody().getArray();
            HashMap params = new HashMap();
            Vector v = new Vector();
            float total_non_current_asset = 0, total_current_asset = 0, total_revenue = 0, total_expense = 0;
            for(int i=0; i<bs.length(); i++){
                JSONObject obj = bs.getJSONObject(i);
                String id = obj.get("id").toString();
                String balance = String.valueOf(Float.parseFloat(obj.get("totalBalance").toString()));
                v.add(new BS(id, balance));
                params.put(id, balance);
                
                // total non current asset
                if(Integer.parseInt(id) >=9 && Integer.parseInt(id) <=12){
                    total_non_current_asset += Float.parseFloat(balance);
                }
                // total current asset
                if(Integer.parseInt(id) >=13 && Integer.parseInt(id) <= 27){
                    total_current_asset += Float.parseFloat(balance);
                }
                // total revenue
                if(Integer.parseInt(id) >=38 && Integer.parseInt(id) <= 40){
                    total_revenue += Float.parseFloat(balance);
                }
                // total expense
                if((Integer.parseInt(id) >=42 && Integer.parseInt(id) <= 44) || (Integer.parseInt(id) >=46 && Integer.parseInt(id) <= 48) || (Integer.parseInt(id) >=50 && Integer.parseInt(id) <= 57)){
                    total_expense += Float.parseFloat(balance);
                }
                
            }
            params.put("date", "22-06-2015");
            params.put("total_non_current_asset", String.valueOf(total_non_current_asset));
            params.put("total_current_asset", String.valueOf(total_current_asset));
            params.put("total_revenue", String.valueOf(total_revenue));
            params.put("total_expense", String.valueOf(total_expense));
            Report r = new Report();
            r.getReport("src\\report\\bs3.jrxml", new JRBeanCollectionDataSource(v), params);
            r.getReport("src\\report\\pl.jrxml", new JRBeanCollectionDataSource(v), params);
        } catch (UnirestException ex) {
            Logger.getLogger(TopNavController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getAccountName(String id){
        int acc_id = Integer.parseInt(id);
        for(int i=0; i<account.size(); i++){
            if(account.get(i).getId() == acc_id) return account.get(i).getName();
        }
        return "";
    }
    
}
