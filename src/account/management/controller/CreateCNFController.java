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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreateCNFController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextArea address;
    @FXML
    private ComboBox<Location> location;
    @FXML
    private Button save;
    @FXML
    private Button cancel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray location = response.getBody().getArray();
                System.out.println(location);
                for(int i=0; i<location.length(); i++){
                    JSONObject obj = location.getJSONObject(i);
                    this.location.getItems().add(new Location(Integer.parseInt(obj.get("id").toString()), obj.get("name").toString(), obj.get("details").toString()));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(CreateCNFController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String name,address,loc;
            name = this.name.getText();
            address = this.address.getText();
            loc = String.valueOf(this.location.getSelectionModel().getSelectedItem().getId());
            Unirest.post(MetaData.baseUrl + "add/cnf")
                    .field("party_name", name)
                    .field("party_address", address)
                    .field("location_id", loc)
                    .asString();
            
        } catch (UnirestException ex) {
            Logger.getLogger(CreateCNFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
