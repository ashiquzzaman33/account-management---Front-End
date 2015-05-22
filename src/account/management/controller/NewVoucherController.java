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
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class NewVoucherController implements Initializable {
    @FXML
    private VBox field_container;
    @FXML
    private HBox field_row;
    @FXML
    private Button button_add_new_field;
    @FXML
    private DatePicker input_date;
    @FXML
    private ComboBox<Location> select_location;
    @FXML
    private TextField input_voucher_no;
    @FXML
    private TextArea input_narration;
    @FXML
    private ComboBox<String> select_voucher_type;
    @FXML
    private Button button_submit;
    @FXML
    private Button button_delete_row;
    
    private Collection<Location> locations;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        select_voucher_type.getItems().addAll("JV","CV","PV","SV");
        
        /*
        *   init locations in select_location combobox
        */
        locations = FXCollections.observableArrayList();
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray location_array = response.getBody().getArray();
                for(int i=0; i<location_array.length()-1; i++){
                    String name = location_array.getJSONObject(i).getString("name");
                    String details = location_array.getJSONObject(i).getString("details");
                    int id = location_array.getJSONObject(i).getInt("id");
                    locations.add(new Location(id,name,details));
                }
                select_location.getItems().addAll(locations);
                
            } catch (UnirestException ex) {
                System.out.println("exception in UNIREST");
            }
        }).start();
        
        this.button_submit.setDisable(true);
        
        field_container.setOnKeyReleased((KeyEvent event)->{
            validateFields();
        });
        
        
    }    

    @FXML
    private void onAddNewFieldButtonClick(ActionEvent event) {
        
        HBox row = new HBox();
        row.setId("field_row");
        ComboBox<Account> select_account = new ComboBox<>();
        TextField dr        = new TextField();
        TextField cr        = new TextField();
        TextField remarks   = new TextField();
        Button del_row = new Button("Delete");
        
        row.setSpacing(field_row.getSpacing());
        
        ComboBox combo = (ComboBox)field_row.getChildren().get(0);
        select_account.setPrefWidth(combo.getPrefWidth());
        select_account.setPromptText("Select account");
        
        TextField tf = (TextField)field_row.getChildren().get(1);
        dr.setPrefWidth(tf.getPrefWidth());
        dr.setPromptText("Dr");
        
        tf = (TextField)field_row.getChildren().get(2);
        cr.setPrefWidth(tf.getPrefWidth());
        cr.setPromptText("Cr");
        
        tf = (TextField)field_row.getChildren().get(3);
        remarks.setPrefWidth(tf.getPrefWidth());
        remarks.setPromptText("remarks");
        
        row.getChildren().addAll(select_account, dr, cr, remarks, del_row);
        field_container.getChildren().add(row);
        
        del_row.setOnMouseClicked((MouseEvent event1) -> {
            field_container.getChildren().removeAll(row);
            validateFields();
        });
        
        validateFields();
        
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
    }

    @FXML
    private void onDeleteRowButtonClick(MouseEvent event) {
        field_container.getChildren().removeAll(field_row);
        validateFields();
    }
    
    private void validateFields(){
        
        int dr_counter = 0, cr_counter = 0;
        float total_dr = 0f, total_cr = 0f;
        
        for(int i=0; i<field_container.getChildren().size(); i++){
            HBox row = (HBox) field_container.getChildren().get(i);
            TextField dr = (TextField) row.getChildren().get(1);
            TextField cr = (TextField) row.getChildren().get(2);
            
            if(dr.getText().equals("") && cr.getText().equals("")){
                this.button_submit.setDisable(true);
                return;
            }
            if(!dr.getText().equals("") && !cr.getText().equals("")){
                this.button_submit.setDisable(true);
                return;
            }
            /*
            *   regular expression check
            */
            Pattern pattern = Pattern.compile("^[0-9]*\\.?[0-9]+$");
            Matcher match = pattern.matcher(dr.getText());
            if(!dr.getText().equals("") && !match.find()){
                this.button_submit.setDisable(true);
                return;
            }
            match = pattern.matcher(cr.getText());
            if(!cr.getText().equals("") && !match.find()){
                this.button_submit.setDisable(true);
                return;
            }
            
            
            if(!dr.getText().equals("")){
                dr_counter++;
                total_dr += Float.parseFloat(dr.getText());
            }
            if(!cr.getText().equals("")){
                cr_counter++;
                total_cr += Float.parseFloat(cr.getText());
            }
                
        }
        
        if((dr_counter > 1 && cr_counter > 1) || dr_counter ==0 || cr_counter == 0 || (total_dr != total_cr)){
            this.button_submit.setDisable(true);
            return;
        }
        
        this.button_submit.setDisable(false);
        
    }
    
}

