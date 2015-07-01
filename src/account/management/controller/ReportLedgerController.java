/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.Ledger;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
public class ReportLedgerController implements Initializable {
    @FXML
    private ComboBox<Account> account;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(()->{
            try {

                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/accounts").asJson();
                JSONArray account = response.getBody().getArray();
                for(int i=0; i<account.length(); i++){
                    if(i == 0) continue;
                    JSONObject obj = account.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("name").toString();
                    String desc = obj.get("description").toString();
                    int parent_id = Integer.parseInt(obj.get("parent").toString());

                    this.account.getItems().add(new Account(id,name,parent_id,desc,0f));
                    
                }
                
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }    

    @FXML
    private void onShowReportButtonClick(ActionEvent event) {
        String acc_id = String.valueOf(this.account.getSelectionModel().getSelectedItem().getId());
        String start_date = null,end_date = null;
        try {
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
        } catch (ParseException ex) {
            Logger.getLogger(ReportLedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector v = new Vector();
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/ledger")
                    .queryString("account_id", acc_id)
                    .queryString("start_date", start_date)
                    .queryString("end_date", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                if(obj.get("voucher_id").toString().equals("1")){
                    v.add(new Ledger("","","","Opening balance","","",obj.get("balance").toString()));
                    continue;
                }
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("date").toString()));
                v.add(new Ledger(date,obj.get("voucher_id").toString(),getAccountName(Integer.parseInt(obj.get("against_account_id").toString())),obj.get("remark").toString(),obj.get("dr").toString(),obj.get("cr").toString(),obj.get("balance").toString()));
            }
            HashMap params = new HashMap();
            params.put("ledger_name", "Ledger of " + this.account.getSelectionModel().getSelectedItem().getName());
            params.put("date", "From "+ start_date +" To " + end_date);
            Report report = new Report();
            report.getReport("src\\report\\ledger.jrxml", new JRBeanCollectionDataSource(v), params);
        } catch (UnirestException ex) {
            Logger.getLogger(ReportLedgerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ReportLedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getAccountName(int id){
        ObservableList<Account> a = this.account.getItems();
        for(int i=0; i<a.size(); i++){
            if(a.get(i).getId() == id){
                return a.get(i).getName();
            }
        }
        return "";
    }
    
}
