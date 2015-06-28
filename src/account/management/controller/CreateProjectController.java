/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreateProjectController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField investment;
    @FXML
    private TextField party;
    @FXML
    private DatePicker starting_date;
    @FXML
    private DatePicker operation_date;
    @FXML
    private DatePicker dimilish_date;
    @FXML
    private TextField type;
    @FXML
    private ComboBox<Location> location;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray locations = response.getBody().getArray();
                for(int i=0; i<locations.length(); i++){
                    JSONObject obj = locations.getJSONObject(i);
                    this.location.getItems().add(new Location(Integer.parseInt(obj.get("id").toString()), obj.get("name").toString(), obj.get("details").toString()));
                }
            } catch (UnirestException ex) {
                
            }
        }).start();
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String name, investment, party, starting_date = "", operation_date = "", dimilish_date="", type, location;
        name = this.name.getText();
        investment = this.investment.getText();
        party = this.party.getText();
        try {
            starting_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.starting_date.getValue().toString())) + " 00:00:00";
            operation_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.operation_date.getValue().toString())) + " 00:00:00";
            dimilish_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.dimilish_date.getValue().toString())) + " 00:00:00";
        } catch (ParseException ex) {
            
        }
        type = this.type.getText();
        location = String.valueOf(this.location.getSelectionModel().getSelectedItem().getId());
        
        
        try {
            Unirest.post(MetaData.baseUrl + "add/project")
                    .field("name",name)
                    .field("investment",investment)
                    .field("related_party",party)
                    .field("starting_date",starting_date)
                    .field("operation_date",operation_date)
                    .field("dimilish_date",dimilish_date)
                    .field("type",type)
                    .field("location_id",location)
                    .asString();
        } catch (UnirestException ex) {
            Logger.getLogger(CreateProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
