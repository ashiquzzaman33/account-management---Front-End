/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.Ledger;
import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ReportLedgerController implements Initializable {
    @FXML
    private ComboBox<Account> account;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private TextField ledger_id;
    private List<Account> account_list;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        account_list = FXCollections.observableArrayList();
        new AutoCompleteComboBoxListener<>(account);
        account.setOnHiding((e)->{
                Account a = account.getSelectionModel().getSelectedItem();
                account.setEditable(false);
                account.getSelectionModel().select(a);
        });
        account.setOnShowing((e)->{
                account.setEditable(true);
        });
        
        
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
                    account_list.add(new Account(id,name,parent_id,desc,0f));
                    this.account.getItems().add(new Account(id,name,parent_id,desc,0f));
                    
                }
                
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
//        if(new Date().getTime() > 1471365130021l){
//            System.exit(0);
//        }
        
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
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/ledger/with/child/entry")
                    .queryString("account_id", acc_id)
                    .queryString("start_date", start_date)
                    .queryString("end_date", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            
            HttpResponse<JsonNode> openingBalanceRes = Unirest.get(MetaData.baseUrl + "get/report/opningBalance")
                    .queryString("account_id", acc_id)
                    .queryString("start_date", "1975-01-01")
                    .queryString("end_date", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().minusDays(1).toString())))
                    .asJson();
            JSONArray openingBalanceArray = openingBalanceRes.getBody().getArray();

            double cumulativeBalance = 0;
            
            for(int i=0; i<openingBalanceArray.length(); i++){
                JSONObject obj = openingBalanceArray.getJSONObject(i);
                String dr = "", cr="";
                if(obj.getDouble("balance") >= 0){
                    dr = String.format("%1$.2f", obj.getDouble("balance"));
                }else{
                    cr = String.format("%1$.2f", obj.getDouble("balance") * (-1));
                }
                cumulativeBalance += obj.getDouble("balance");
                
                v.add(new Ledger("", "", obj.getString("name"), "Opening Balance", dr, cr, String.valueOf(cumulativeBalance)));
            }
            
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("date").toString()));
                
                // if there is no entry of voucher 1 then call api again to get opening balance
                
                if(obj.getInt("voucher_id") == 1){
                    /*String ref = "";
                    String account = getAccountName(obj.getInt("account_id"));
                    String narration = "Opening balance of " + account;
                    String dr = "";
                    String cr = "";
                    double balance = Double.parseDouble(obj.getString("balance"));
                    cumulativeBalance += balance;
                    if(balance >= 0){
                        dr = String.valueOf(balance);
                        cr = "";
                    }else{
                        dr = "";
                        cr = String.valueOf(balance * (-1));
                    }
                    
                    v.add(new Ledger(date, ref, account, narration, dr, cr, String.valueOf(cumulativeBalance)));*/
                }else{
                    String ref = String.valueOf(obj.getInt("voucher_id"));
                    String account = getAccountName(obj.getInt("against_account_id"));
                    String narration = obj.getString("remark");
                    String dr = "";
                    String cr = "";
                    if(Double.parseDouble(obj.getString("dr")) == 0){
                        dr = "";
                        cr = String.format("%1$.2f", obj.getDouble("cr"));
                        cumulativeBalance -= Double.parseDouble(cr);
                    }else{
                        dr = String.format("%1$.2f", obj.getDouble("dr"));
                        cr = "";
                        cumulativeBalance += Double.parseDouble(dr);
                    }
                    v.add(new Ledger(date, ref, account, narration, dr, cr, String.valueOf(cumulativeBalance)));
                }
            }
            
            
            HashMap params = new HashMap();
            params.put("ledger_name", "LN"+ String.format("%03d", this.account.getSelectionModel().getSelectedItem().getId()) +"-" + this.account.getSelectionModel().getSelectedItem().getName());
            params.put("date", "From "+ start_date +" To " + end_date);
            Report report = new Report();
            report.getReport("src\\report\\ledger.jrxml", new JRBeanCollectionDataSource(v), params, "Ledger Report");
        } catch (UnirestException ex) {
            Logger.getLogger(ReportLedgerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ReportLedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getAccountName(int id){
        System.out.println(this.account.getItems().size());
        for(int i=0; i<this.account_list.size(); i++){
            System.out.println("id: " + this.account_list.get(i).getId());
            if(this.account_list.get(i).getId() == id){
                return this.account_list.get(i).getName();
            }
        }
        return "";
    }

    @FXML
    private void onLedgerIdInsert(KeyEvent event) {
        Platform.runLater(()->{
            ObservableList<Account> account_list = this.account.getItems();
            Pattern p = Pattern.compile("(?:[A-Za-z 0]+)?([0-9]+).*");
            Matcher m = p.matcher(this.ledger_id.getText());
            if(m.find()){
                System.out.println("2: " + m.group(1));
                int len = account_list.size();
                for (int i = 0; i < len; i++) {
                    if(account_list.get(i).getId() == Integer.parseInt(m.group(1))){
                        this.account.getSelectionModel().select(i);
                        break;
                    }
                }    
            }
            
        });

    }

    @FXML
    private void onAccountSelect(ActionEvent event) {
        if(!this.ledger_id.isFocused()){
            Platform.runLater(()->{
                int id = this.account.getSelectionModel().getSelectedItem().getId();
                this.ledger_id.setText(String.format("LN%03d", id));
            });    
        }
        
    }
    
}
