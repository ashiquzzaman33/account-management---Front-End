/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.AutoCompleteComboBoxListener;
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
public class LCReportController implements Initializable {
    @FXML
    private ComboBox<LC> select_lc;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;

    private List<LC> lc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(select_lc);
        select_lc.setOnHiding((e)->{
                LC a = select_lc.getSelectionModel().getSelectedItem();
                select_lc.setEditable(false);
                select_lc.getSelectionModel().select(a);
        });
        select_lc.setOnShowing((e)->{
                select_lc.setEditable(true);
        });
        
        
        lc = FXCollections.observableArrayList();
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/lc/all").asJson();
                JSONArray array = res.getBody().getArray();
                
                
                
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    long id = obj.getLong("id");
                    String lc_no = obj.get("lc_number").toString();
                    String party_name = obj.getString("party_name");
                    String party_bank = obj.getString("party_bank_name");
                    String party_address = obj.getString("party_address");
                    String our_bank = obj.getString("our_bank_name");
                    String lc_amount = String.valueOf(Float.parseFloat(obj.getString("lc_amount")));
                    String init_date = obj.getString("initialing_date");
                    String start_date = obj.getString("starting_date");
                    String dimilish_date = obj.getString("dimilish_date");
                    
                    lc.add(new LC(id, lc_no, party_name, party_bank, party_address, our_bank, lc_amount,init_date, start_date, dimilish_date));
                    
                }
                
            } catch (UnirestException ex) {
                Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                this.select_lc.getItems().addAll(lc);
            }
        }).start();
    }    

    @FXML
    private void onShowReportClick(ActionEvent event) {
        String start_date,end_date;
        try {
            long lc_id = this.select_lc.getSelectionModel().getSelectedItem().getId();
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/balancesheet")
                    .queryString("date","\""+ start_date +"\"")
                    .queryString("plc",lc_id)
                    .asJson();
            
            HttpResponse<JsonNode> res2 = Unirest.get(MetaData.baseUrl + "report/balancesheet")
                    .queryString("date","\""+ end_date +"\"")
                    .queryString("plc",lc_id)
                    .asJson();
            
            JSONArray bs = res.getBody().getArray();
            JSONArray bs2 = res2.getBody().getArray();
            System.out.println(bs);
            System.out.println(bs2);
            HashMap params = new HashMap();
            Vector v = new Vector();
            float total_non_current_asset = 0, total_current_asset = 0, total_revenue = 0, total_expense = 0;
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
            }
            params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString())) + "To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString())));
            params.put("total_non_current_asset", String.valueOf(total_non_current_asset));
            params.put("total_current_asset", String.valueOf(total_current_asset));
            params.put("total_revenue", String.valueOf(total_revenue));
            params.put("total_expense", String.valueOf(total_expense));
            params.put("net_profit", String.valueOf(total_revenue - total_expense));
            LC lc = this.select_lc.getSelectionModel().getSelectedItem();
            params.put("lc_no", "LC No: " + lc.getLc_no());
            params.put("party_name", "Party Name: " + lc.getParty_name());
            params.put("party_address","Party Address: " + lc.getParty_address());
            params.put("party_bank","Bank (Party): " + lc.getParty_bank());
            params.put("our_bank","Bank (Utopia): " + lc.getOur_bank());
            params.put("lc_amount","Amount: " + lc.getLc_amount());
            params.put("init_date","initialing_date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lc.getInit_date())));
            params.put("start_date","Starting Date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lc.getStart_date())));
            params.put("dimilish_date","Dimilish Date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lc.getDimilish_date())));
            System.out.println(lc.getId() - 500000);
            JSONObject obj = Unirest.get(MetaData.baseUrl + "lc/meta")
                    .queryString("lc_id", lc.getId() - 500000)
                    .asJson().getBody().getObject();
            
            params.put("dollar", "LC Opening Dollar: " + String.valueOf(obj.getDouble("dollar")));
            params.put("rate", "Rate: " + String.valueOf(obj.getDouble("rate")));
            params.put("bd_amount", "Total BD Amount: " + String.valueOf(obj.getDouble("bd_amount")));
            
            
            Report r = new Report();
            r.getReport("src\\report\\LcReport.jrxml", new JRBeanCollectionDataSource(v), params, "LC Report");
        } catch (ParseException ex) {
            Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
