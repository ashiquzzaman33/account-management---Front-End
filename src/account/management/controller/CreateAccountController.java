/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * @author mohar
 */
public class CreateAccountController implements Initializable {
    @FXML
    private TextField input_account_name;
    @FXML
    private TextArea input_description;
    @FXML
    private ComboBox<Account> select_parent;
    @FXML
    private Button button_submit;
    @FXML
    private TextField input_opening_balance;
    @FXML
    private ComboBox<String> select_dr_cr;
    
    private Collection<Account> account;
    private Collection<Location> location;
    @FXML
    private ComboBox<Location> select_location;
    @FXML
    private ImageView parent_preloader;
    @FXML
    private ImageView location_preloader;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        parent_preloader.setImage(new Image("/pre_loader.gif",true));
        location_preloader.setImage(new Image("/pre_loader.gif",true));
        location = FXCollections.observableArrayList();
        
        /*
        *   add location to combo box
        */
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                    JSONArray locationArray = response.getBody().getArray();
                    for(int i=0; i<locationArray.length(); i++){
                        JSONObject obj = locationArray.getJSONObject(i);
                        int id = Integer.parseInt(obj.get("id").toString());
                        String name = obj.get("name").toString();
                        String details = obj.get("details").toString();
                        // if the location is not "none" then add it
                        if(id != 1) location.add(new Location(id, name,details));
                    }
                    select_location.getItems().addAll(location);
                    location_preloader.setVisible(false);
                } catch (UnirestException ex) {
                    
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                account = FXCollections.observableArrayList();
                /*
                *   add dr/cr options in select_dr_cr combobox
                */
                select_dr_cr.setItems(FXCollections.observableArrayList("Dr","Cr"));
                Unirest.get(MetaData.baseUrl +"get/accounts").asJsonAsync(new Callback<JsonNode>(){

                    @Override
                    public void completed(HttpResponse<JsonNode> response) {
                        parent_preloader.setVisible(false);
                        JSONArray array = response.getBody().getArray();
                        for(int i=0; i<array.length();i++){

                            JSONObject obj = array.getJSONObject(i);
                            int id = Integer.parseInt(obj.get("id").toString());
                            String name = obj.get("name").toString();
                            int parent = Integer.parseInt(obj.get("parent").toString());
                            String desc = obj.get("description").toString();

                            account.add(new Account(id, name, parent, desc,0f));
                        }

                        select_parent.getItems().addAll(account);
                        
                    }

                    @Override
                    public void failed(UnirestException ue) {
                        System.err.println("Failed to get Account lists");
                        
                    }

                    @Override
                    public void cancelled() {
                        System.err.println("Cancelled when getting Account lists");
                        
                    }

                });
            }
        }).start();
    }
    
    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        
        this.button_submit.setDisable(true);
        
        String name = input_account_name.getText();
        int parent = select_parent.getSelectionModel().getSelectedItem().getId();
        float opening_balance = Float.parseFloat(input_opening_balance.getText());
        String desc = input_description.getText();
        int location_id = this.select_location.getSelectionModel().getSelectedItem().getId();
        
        
        if(select_dr_cr.getSelectionModel().getSelectedItem().equals("Cr")){
            opening_balance *= -1f;
        }
        Account account = new Account(0, name, parent, desc, opening_balance);
        
        Unirest.post(MetaData.baseUrl + "add/account")
                .header("accept", "application/json")
                .field("name", account.getName())
                .field("parent", String.valueOf(account.getParent()))
                .field("description", account.getDescription())
                .field("opening_balance", String.valueOf(account.getOpeningBalance()))
                .field("location_id", String.valueOf(location_id))
                .asJsonAsync(new Callback<JsonNode>() {
                    
                    @Override
                    public void completed(HttpResponse<JsonNode> hr) {
                        button_submit.setDisable(false);
                        System.out.println(hr.getBody());
                    }
                    
                    @Override
                    public void failed(UnirestException ue) {
                        System.err.println("failed");
                    }
                    
                    @Override
                    public void cancelled() {
                        System.err.println("cancelled");
                    }
                });

        
        
        
   }

}
