/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.DebitVoucher;
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
public class ViewDebitVoucherController implements Initializable {
    @FXML
    private TableView<DebitVoucher> table;
    @FXML
    private TableColumn<DebitVoucher, Integer> no;
    @FXML
    private TableColumn<DebitVoucher, String> date;
    @FXML
    private TableColumn<DebitVoucher, String> details;
    @FXML
    private TableColumn<DebitVoucher, String> debitors;
    @FXML
    private TableColumn<DebitVoucher, String> bank;
    @FXML
    private TableColumn<DebitVoucher, String> branch;
    @FXML
    private TableColumn<DebitVoucher, String> address;
    @FXML
    private TableColumn<DebitVoucher, String> amount;
    @FXML
    private TableColumn<DebitVoucher, String> type;
    @FXML
    private TableColumn<DebitVoucher, String> note;
    @FXML
    private TableColumn<DebitVoucher, String> word;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.no.setCellValueFactory(new PropertyValueFactory("id"));
        this.date.setCellValueFactory(new PropertyValueFactory("date"));
        this.details.setCellValueFactory(new PropertyValueFactory("details"));
        this.debitors.setCellValueFactory(new PropertyValueFactory("debitors"));
        this.bank.setCellValueFactory(new PropertyValueFactory("bank"));
        this.branch.setCellValueFactory(new PropertyValueFactory("branch"));
        this.address.setCellValueFactory(new PropertyValueFactory("address"));
        this.amount.setCellValueFactory(new PropertyValueFactory("amount"));
        this.type.setCellValueFactory(new PropertyValueFactory("type"));
        this.note.setCellValueFactory(new PropertyValueFactory("note"));
        this.word.setCellValueFactory(new PropertyValueFactory("word"));
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) {
        this.table.getItems().clear();
        String start = this.start.getValue().toString();
        String end = this.end.getValue().toString();
        
        try {
            JSONArray res = Unirest.get(MetaData.baseUrl + "get/deposit/voucher")
                    .queryString("start_date", start)
                    .queryString("end_date", end)
                    .asJson().getBody().getArray();
            int len = res.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = res.getJSONObject(i);
                this.table.getItems().add(new DebitVoucher(obj.getInt("id"), obj.getString("date"), obj.getString("details"), obj.getString("via"), obj.getString("bank_ac"), obj.getString("branch"), obj.getString("address"), obj.getString("amount"), obj.getString("method"), obj.getString("note"), obj.getString("word")));
            }
        } catch (UnirestException ex) {
            Logger.getLogger(ViewDebitVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
