/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.ExpenseVoucher;
import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ViewCreditVoucherController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private TableColumn<ExpenseVoucher, Integer> id;
    @FXML
    private TableColumn<ExpenseVoucher, String> date;
    @FXML
    private TableColumn<ExpenseVoucher, String> location;
    @FXML
    private TableColumn<ExpenseVoucher, String> rcv_name;
    @FXML
    private TableColumn<ExpenseVoucher, String> rcv_address;
    @FXML
    private TableColumn<ExpenseVoucher, String> via;
    @FXML
    private TableColumn<ExpenseVoucher, String> via_address;
    @FXML
    private TableColumn<ExpenseVoucher, String> total;
    @FXML
    private TableColumn<ExpenseVoucher, String> word;
    @FXML
    private TableColumn<ExpenseVoucher, String> details;
    
    private List<Location> location_list;
    @FXML
    private TableView<ExpenseVoucher> table;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        location_list = FXCollections.observableArrayList();
        
        this.id.setCellValueFactory(new PropertyValueFactory("id"));
        this.date.setCellValueFactory(new PropertyValueFactory("date"));
        this.location.setCellValueFactory(new PropertyValueFactory("location"));
        this.rcv_name.setCellValueFactory(new PropertyValueFactory("rcv_name"));
        this.rcv_address.setCellValueFactory(new PropertyValueFactory("rcv_address"));
        this.via.setCellValueFactory(new PropertyValueFactory("via"));
        this.via_address.setCellValueFactory(new PropertyValueFactory("via_address"));
        this.total.setCellValueFactory(new PropertyValueFactory("total"));
        this.word.setCellValueFactory(new PropertyValueFactory("word"));
        this.details.setCellValueFactory(new PropertyValueFactory("details"));
        
        try {
            JSONArray res = Unirest.get(MetaData.baseUrl + "get/locations").asJson().getBody().getArray();
            int len = res.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = res.getJSONObject(i);
                this.location_list.add(new Location(obj.getInt("id"), obj.getString("name"), obj.getString("details")));
            }
        } catch (UnirestException ex) {
            Logger.getLogger(ViewCreditVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) {

        String start = this.start.getValue().toString();
        String end = this.end.getValue().toString();
        
        try {
            JSONArray res = Unirest.get(MetaData.baseUrl + "get/expenseVoucher")
                    .queryString("start_date", start)
                    .queryString("end_date", end)
                    .asJson().getBody().getArray();
            int len = res.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = res.getJSONObject(i);
                int id = obj.getInt("id");
                String date = obj.getString("date");
                String location = getLocationNameFromId(obj.getInt("location"));
                String rcv_name = obj.getString("receivers_name");
                String rcv_address = obj.getString("receivers_address");
                String via = obj.getString("via");
                String via_address = obj.getString("via_address");
                String in_word = obj.getString("in_word");
                String total = String.valueOf(obj.getDouble("total"));
                String details = obj.getString("expenses");
                JSONArray array2 = new JSONArray(details);
                details = " ";
                int len2 = array2.length();
                for (int j = 0; j < len2; j++) {
                    JSONObject obj2 = array2.getJSONObject(j);
                    details += obj2.getString("description") + " (" + obj2.getString("amount") +" tk ) , ";
                }
                
                this.table.getItems().add(new ExpenseVoucher(id, date, location, rcv_name, rcv_address, via, via_address, total, in_word, details));
                
            }
        } catch (UnirestException ex) {
            Logger.getLogger(ViewCreditVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getLocationNameFromId(int id){
        int len = this.location_list.size();
        for (int i = 0; i < len; i++) {
            if(this.location_list.get(i).getId() == id){
                return this.location_list.get(i).getName();
            }
        }
        return "";
    }
    
}
