/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.BS;
import account.management.model.LC;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class ReportBsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public List<Account> account = FXCollections.observableArrayList();
    @FXML
    private Button cancel;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void onShowBalanceSheetClick(ActionEvent event) {
        String start_date,end_date;
        try {
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/balancesheet")
                    .queryString("date","\""+ start_date +"\"")
                    .asJson();
            
            HttpResponse<JsonNode> res2 = Unirest.get(MetaData.baseUrl + "report/balancesheet")
                    .queryString("date","\""+ end_date +"\"")
                    .asJson();
            
            JSONArray bs = res.getBody().getArray();
            JSONArray bs2 = res2.getBody().getArray();
            System.out.println(bs);
            System.out.println(bs2);
            HashMap params = new HashMap();
            Vector v = new Vector();
            float total_non_current_asset = 0, total_current_asset = 0, total_revenue = 0, total_expense = 0, equity = 0, long_term_liability = 0, current_liability = 0;
            for(int i=0; i<bs.length(); i++){
                JSONObject obj = bs.getJSONObject(i);
                JSONObject obj2 = bs2.getJSONObject(i);
                String id = obj.get("id").toString();
                String balance = String.valueOf(Float.parseFloat(obj2.get("totalBalance").toString()) - Float.parseFloat(obj.get("totalBalance").toString()));
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
                if(Integer.parseInt(id) == 28){
                    equity = Float.parseFloat(balance);
                }
                if(Integer.parseInt(id) == 29){
                    equity = equity - Float.parseFloat(balance);
                }
                if(Integer.parseInt(id) == 30){
                    long_term_liability = Float.parseFloat(balance);
                }
                if(Integer.parseInt(id) == 33){
                    current_liability = Float.parseFloat(balance);
                }
            }
            if(total_expense < 0){
                total_expense *= -1;
            }
            params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString())) + "To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString())));
            params.put("total_non_current_asset", String.valueOf(total_non_current_asset));
            params.put("total_current_asset", String.valueOf(total_current_asset));
            params.put("total_asset", String.valueOf(total_current_asset + total_non_current_asset));
            params.put("total_revenue", String.valueOf(total_revenue));
            params.put("total_expense", String.valueOf(total_expense));
            params.put("net_profit", String.valueOf(total_revenue - total_expense));
            params.put("total_equity", String.valueOf(equity + total_revenue - total_expense));
            System.out.println(long_term_liability);
            System.out.println(current_liability);
            params.put("total_liability", String.valueOf(long_term_liability + current_liability));
            
            Report r = new Report();
            r.getReport("src\\report\\bs.jrxml", new JRBeanCollectionDataSource(v), params);
            r.getReport("src\\report\\pl.jrxml", new JRBeanCollectionDataSource(v), params);
        } catch (ParseException ex) {
            Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.cancel.getScene().getWindow().hide();
        }
        
    }
    
    public String getAccountName(String id){
        int acc_id = Integer.parseInt(id);
        for(int i=0; i<account.size(); i++){
            if(account.get(i).getId() == acc_id) return account.get(i).getName();
        }
        return "";
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
