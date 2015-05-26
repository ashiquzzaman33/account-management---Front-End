/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Location;
import account.management.model.MetaData;
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
 * @author Raju
 */
public class EditLocationController implements Initializable {
    @FXML
    private ComboBox<Location> location_select;
    @FXML
    private TextField name_input;
    @FXML
    private TextArea details_input;
    @FXML
    private Button update_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(()->{
            try {
                JSONArray location_array = Unirest.get(MetaData.baseUrl + "get/locations").asJson().getBody().getArray();
                
                for(int i=0; i<location_array.length(); i++){
                    JSONObject location = location_array.getJSONObject(i);
                    int id = location.getInt("id");
                    if(id == 1) continue;
                    String name = location.getString("name");
                    String details = location.getString("details");
                    location_select.getItems().add(new Location(id,name,details));
                }
                
            } catch (UnirestException ex) {
            }
        }).start();
        
    }    

    @FXML
    private void onLocationSelect(ActionEvent event) {
        
        Location loc = location_select.getSelectionModel().getSelectedItem();
        this.name_input.setText(loc.getName());
        this.details_input.setText(loc.getDetails());
        
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
    }
    
}
