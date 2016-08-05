/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        
        new AutoCompleteComboBoxListener<>(location_select);
        location_select.setOnHiding((e)->{
                Location a = location_select.getSelectionModel().getSelectedItem();
                location_select.setEditable(false);
                location_select.getSelectionModel().select(a);
        });
        location_select.setOnShowing((e)->{
                location_select.setEditable(true);
        });
        
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
        try {
            
            int id = this.location_select.getSelectionModel().getSelectedItem().getId();
            String name = this.name_input.getText();
            String details = this.details_input.getText();
        
        
            JSONArray response = Unirest.post(MetaData.baseUrl + "edit/location")
                    .queryString("id", id)
                    .queryString("name", name)
                    .queryString("details", details)
                    .asJson().getBody().getArray();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Location has been updated successfully!");
            alert.setGraphic(new ImageView(new Image("resources/success.jpg")));
            alert.showAndWait();
            //this.update_button.getScene().getWindow().hide();
            
            
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error in the server. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
 
    }
    
}
