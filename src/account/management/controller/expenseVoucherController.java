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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

/**
 *
 * @author mohar
 */
public class expenseVoucherController implements Initializable{
    @FXML
    private Pane title_pane;
    @FXML
    private Label title_label;
    @FXML
    private AnchorPane main_container;
    @FXML
    private DatePicker input_date;
    @FXML
    private TextField input_name;
    @FXML
    private TextField input_address;
    @FXML
    private TextField input_via_address;
    @FXML
    private TextField input_via_name;
    @FXML
    private Button button_add_row;
    @FXML
    private VBox vbox_container;
    @FXML
    private HBox hbox_row;
    @FXML
    private TextField sl_no;
    @FXML
    private TextField input_expense_desc;
    @FXML
    private TextField input_amount;
    @FXML
    private Button button_delete_row;
    @FXML
    private Button button_submit;
    @FXML
    private TextField input_total_amount;
    @FXML
    private TextField input_amount_word;
    @FXML
    private ComboBox<Location> select_location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Location> locations = FXCollections.observableArrayList();
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
        
        
        
        
    }

    @FXML
    private void onAddRowButtonClick(ActionEvent event) {
        TextField sl = new TextField();
        TextField expense_desc = new TextField();
        TextField amount = new TextField();
        Button del = new Button("Delete");
        
        sl.setPrefWidth(this.sl_no.getPrefWidth());
        sl.setPrefHeight(this.sl_no.getPrefHeight());
        
        expense_desc.setPrefWidth(this.input_expense_desc.getPrefWidth());
        expense_desc.setPrefHeight(this.input_expense_desc.getPrefHeight());
        expense_desc.setTranslateX(this.input_expense_desc.getTranslateX());
        
        amount.setTranslateX(this.input_amount.getTranslateX());
        
        del.setTranslateX(this.button_delete_row.getTranslateX());
        del.setOnAction((ActionEvent event1) -> {
            Button b = (Button) event1.getSource();
            vbox_container.getChildren().removeAll(b.getParent());
            updateSerialNumber();
        });
        
        HBox row = new HBox();
        row.getChildren().addAll(sl,expense_desc,amount,del);
        vbox_container.getChildren().add(row);
        
        updateSerialNumber();
        
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        
    }

    @FXML
    private void onDeleteRowButtonClick(ActionEvent event) {
        vbox_container.getChildren().remove(this.hbox_row);
        updateSerialNumber();
    }
    
    private void updateSerialNumber(){
        int sl = 1;
        for(int i=1; i<this.vbox_container.getChildren().size(); i++){
            HBox row = (HBox) this.vbox_container.getChildren().get(i);
            TextField input_sl = (TextField) row.getChildren().get(0);
            input_sl.setText(String.valueOf(sl));
            sl++;
        }
    }
    
}
