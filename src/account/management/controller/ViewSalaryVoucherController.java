/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Data;
import account.management.model.MetaData;
import account.management.model.SalaryVoucher;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ViewSalaryVoucherController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private TableView<SalaryVoucher> table;
    @FXML
    private TableColumn<SalaryVoucher, Integer> voucher_no;
    @FXML
    private TableColumn<SalaryVoucher, String> date;
    @FXML
    private TableColumn<SalaryVoucher, String> section;
    @FXML
    private TableColumn<SalaryVoucher, String> name;
    @FXML
    private TableColumn<SalaryVoucher, String> basis;
    @FXML
    private TableColumn<SalaryVoucher, Double> amount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.voucher_no.setCellValueFactory(new PropertyValueFactory("id"));
        this.date.setCellValueFactory(new PropertyValueFactory("date"));
        this.section.setCellValueFactory(new PropertyValueFactory("section"));
        this.name.setCellValueFactory(new PropertyValueFactory("name"));
        this.basis.setCellValueFactory(new PropertyValueFactory("basis"));
        this.amount.setCellValueFactory(new PropertyValueFactory("total"));
        
        
        
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("    View                  ");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Data.salaryVoucher = table.getSelectionModel().getSelectedItem();
                
                try {
                    Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "EditSalaryVoucher.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    scene.setRoot(root);
                    stage.setResizable(false);
                    stage.setTitle("Salary Voucher");
                    stage.setScene(scene);
                    stage.showAndWait();
                    int index = table.getSelectionModel().getSelectedIndex();
                    getData();
                    
                } catch (IOException ex) {
                    Logger.getLogger(ViewSalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        contextMenu.getItems().addAll(item1);
        this.table.setContextMenu(contextMenu);
        
        
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) {
        getData();
        
    }
    
    public void getData(){
        this.table.getItems().clear();
        String start = this.start.getValue().toString();
        String end = this.end.getValue().toString();
        
        try {
            JSONArray res = Unirest.get(MetaData.baseUrl + "get/salary/voucher")
                    .queryString("start_date", this.start.getValue().toString())
                    .queryString("end_date", this.end.getValue().toString())
                    .asJson().getBody().getArray();
            
            int len = res.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = res.getJSONObject(i);
                this.table.getItems().add(new SalaryVoucher(obj.getInt("id"), obj.getString("date"), obj.getString("section"), obj.getString("party_name"), obj.getString("basis_on_or_date"), obj.getString("amount_in_words"), obj.getDouble("basic_salary"), obj.getDouble("presence"), obj.getDouble("total1"), obj.getDouble("others"), obj.getDouble("total2"), obj.getDouble("advance"), obj.getDouble("fine"), obj.getDouble("apron_or_mask"), obj.getDouble("other_deduction"), obj.getDouble("grand_total")));
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(ViewSalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
