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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class DepositVoucherController implements Initializable {
    @FXML
    private Pane title_pane;
    @FXML
    private Label title_label;
    @FXML
    private AnchorPane main_container;
    @FXML
    private DatePicker input_date;
    @FXML
    private TextArea input_desc;
    @FXML
    private TextField input_party;
    @FXML
    private TextField input_bank_acc;
    @FXML
    private TextField input_branch;
    @FXML
    private TextArea input_address;
    @FXML
    private ComboBox<String> select_payment_type;
    @FXML
    private TextField input_amount;
    @FXML
    private TextField input_amount_word;
    @FXML
    private TextArea input_note;
    @FXML
    private Button button_submit;
    @FXML
    private ComboBox<Location> select_location;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(()->{
            try {
                JSONArray loc_array = Unirest.get(MetaData.baseUrl + "get/locations").asJson().getBody().getArray();
                for(int i=0; i<loc_array.length(); i++){
                    JSONObject obj = loc_array.getJSONObject(i);
                    select_location.getItems().add(new Location(obj.getInt("id"), obj.getString("name"), obj.getString("details")));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(DepositVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }    

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
//validate every field like this        
//String date =  !this.input_date.getEditor().getText().equals(null) ? this.input_date.getValue().toString() : "";
        String date = this.input_date.getValue().toString();
        int loc_id = this.select_location.getSelectionModel().getSelectedItem().getId();
        String desc = this.input_desc.getText();
        String party = this.input_party.getText();
        String bank_acc = this.input_bank_acc.getText();
        String branch = this.input_branch.getText();
        String address = this.input_address.getText();
        String payment_type = this.select_payment_type.getSelectionModel().getSelectedItem();
        String amount = this.input_amount.getText();
        String word = this.input_amount_word.getText();
        String note = this.input_note.getText();
        
        Unirest.post("");
        
        
    }
    
}
