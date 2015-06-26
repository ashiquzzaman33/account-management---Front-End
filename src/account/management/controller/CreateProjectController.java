/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreateProjectController implements Initializable {
    @FXML
    private Pane title_pane;
    @FXML
    private Label title_label;
    @FXML
    private AnchorPane main_container;
    @FXML
    private TextField name;
    @FXML
    private TextField party;
    @FXML
    private DatePicker starting_date;
    @FXML
    private TextField loation;
    @FXML
    private DatePicker operation_date;
    @FXML
    private Button save;
    @FXML
    private DatePicker dimilish_date;
    @FXML
    private TextArea address;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        
        String name = this.name.getText();
        String party = this.party.getText();
        String address = this.address.getText();
        Date starting_date, operation_date, dimilish_date;
        try {
            starting_date = new SimpleDateFormat("MM/dd/yyyy").parse(this.starting_date.getEditor().getText());
            operation_date = new SimpleDateFormat("MM/dd/yyyy").parse(this.operation_date.getEditor().getText());
            dimilish_date = new SimpleDateFormat("MM/dd/yyyy").parse(this.dimilish_date.getEditor().getText());
            
        } catch (ParseException ex) {
            Logger.getLogger(CreateProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String location = this.loation.getText();
        
        
    }
    
}
