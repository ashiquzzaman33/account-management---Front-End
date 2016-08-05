/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.BS;
import account.management.model.MetaData;
import account.management.model.Project;
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
public class ProjectReportController implements Initializable {
    @FXML
    private ComboBox<Project> select_project;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;
    
    private List<Project> project;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(select_project);
        select_project.setOnHiding((e)->{
                Project a = select_project.getSelectionModel().getSelectedItem();
                select_project.setEditable(false);
                select_project.getSelectionModel().select(a);
        });
        select_project.setOnShowing((e)->{
                select_project.setEditable(true);
        });
        
        
        project = FXCollections.observableArrayList();
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/project/all").asJson();
                JSONArray array = res.getBody().getArray();
                
                
                
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    long id = obj.getLong("id");
                    String name = obj.getString("name");
                    String investment = String.valueOf(Float.parseFloat(obj.getString("investment")));
                    String party = obj.getString("related_party");
                    String operation_date = obj.getString("operation_date");
                    String start_date = obj.getString("starting_date");
                    String dimilish_date = obj.getString("dimilish_date");
                    
                    project.add(new Project(String.valueOf(id), name, investment, party, start_date, operation_date, dimilish_date));
                    
                }
                
            } catch (UnirestException ex) {
                Logger.getLogger(LCReportController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                this.select_project.getItems().addAll(project);
            }
        }).start();
    }    

    @FXML
    private void onShowReportButtonClick(ActionEvent event) {
        String start_date,end_date;
        try {
            String lc_id = this.select_project.getSelectionModel().getSelectedItem().getId();
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
            params.put("total_expense", String.valueOf(total_revenue - total_expense));
            params.put("net_profit", String.valueOf(total_expense));
            Project p = this.select_project.getSelectionModel().getSelectedItem();
            params.put("name", "Name: " + p.getName());
            params.put("investment", "Investment: " + p.getInvestment());
            params.put("party", "Party: " + p.getParty());
            params.put("operation_date","initialing_date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(p.getOperation_date())));
            params.put("start_date","Starting Date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(p.getStart_date())));
            params.put("dimilish_date","Dimilish Date: " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(p.getDimilish_date())));
            
            
            
            Report r = new Report();
            r.getReport("src\\report\\ProjectReport.jrxml", new JRBeanCollectionDataSource(v), params, "Project Report");
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
