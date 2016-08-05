/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.PartDetailsReport;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
public class PartyDetailsReportController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onShowReportButtonClick(ActionEvent event) {
        try {
            String start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            String end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            
            HttpResponse<JsonNode> res =  Unirest.get(MetaData.baseUrl +  "report/party/all")
                    .queryString("start_date", start_date)
                    .queryString("end_date", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            Vector v = new Vector();
            HashMap params = new HashMap();
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                
                String name = obj.getString("party_name");
                String opening_bal = obj.getString("opening_balance");
                String dr = obj.getString("dr");
                String cr = obj.getString("cr");
                String balance = obj.getString("balance");
                
                v.add(new PartDetailsReport(name, opening_bal, dr, cr, balance));
                
                
            }
            
            params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString())) + " To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString())));
            Report report = new Report();
            report.getReport("src\\report\\PartyReport.jrxml", new JRBeanCollectionDataSource(v), params, "Party Report");
            
        } catch (ParseException ex) {
            Logger.getLogger(PartyDetailsReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(PartyDetailsReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
