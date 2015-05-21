/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.Location;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    @FXML
    private void onAddNewFieldButtonClick(ActionEvent event) {
        
        HBox row = new HBox();
        row.setId("field_row");
        ComboBox<Account> select_account = new ComboBox<Account>();
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
        
        del_row.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                field_container.getChildren().removeAll(row);
            }
            
        });
        
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
    }

    @FXML
    private void onDeleteRowButtonClick(MouseEvent event) {
        field_container.getChildren().removeAll(field_row);
    }
    
}

