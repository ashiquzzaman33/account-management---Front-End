/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.EnglishNumberToWords;
import account.management.model.ExpenseVoucherEntry;
import account.management.model.Location;
import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 *
 * @author mohar
 */
public class expenseVoucherController implements Initializable{
    
    @FXML
    private DatePicker date;
    @FXML
    private TextField receivers_name;
    @FXML
    private TextArea receivers_address;
    @FXML
    private TextField via_name;
    @FXML
    private TextArea via_address;
    @FXML
    private Button add_new;
    @FXML
    private VBox container;
    @FXML
    private HBox row;
    @FXML
    private TextField total;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private TextField in_word;
    @FXML
    private ComboBox<Location> location;
    @FXML
    private TextField desc;
    @FXML
    private TextField amount;
    @FXML
    private Button delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        new AutoCompleteComboBoxListener<>(this.location);
        this.location.setOnHiding((e)->{
                Location a = this.location.getSelectionModel().getSelectedItem();
                this.location.setEditable(false);
                this.location.getSelectionModel().select(a);
        });
        this.location.setOnShowing((e)->{
                this.location.setEditable(true);
        });
        
        
        
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
            JSONArray array = res.getBody().getArray();
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                this.location.getItems().add(new Location(obj.getInt("id"), obj.getString("name"), obj.getString("details")));
            }
            this.amount.setOnKeyReleased((e)->{
                calculateTotal();
            });
        } catch (Exception e) {
        }
        
        new Thread(()->{
            try {
                Thread.sleep(5000);
                this.save.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                  if (keyEvent.getCode() == KeyCode.ENTER) {
                      System.out.println("attempting to submit expense voucher");
                   //Stop letting it do anything else
                   keyEvent.consume();
                   onSaveButtonClick(null);
                  }
                }
               });
            } catch (InterruptedException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }).start();
        
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String date = "", location = "", receivers_name = "", receivers_address = "", via = "", via_address = "", in_word = "", total = "", expenses = "[]";
            if(!this.date.getEditor().getText().equals("")){
                date = this.date.getValue().toString();
                date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            }
            if(!this.location.getSelectionModel().isEmpty()) location = String.valueOf(this.location.getSelectionModel().getSelectedItem().getId());
            if(!this.receivers_name.getText().equals("")) receivers_name = this.receivers_name.getText();
            if(!this.receivers_address.getText().equals("")) receivers_address = this.receivers_address.getText();
            if(!this.via_name.getText().equals("")) via = this.via_name.getText();
            if(!this.via_address.getText().equals("")) via_address = this.via_address.getText();
            if(!this.in_word.getText().equals("")) in_word = this.in_word.getText();
            if(!this.total.getText().equals("")) total = this.total.getText();
            JSONArray expenses_array = new JSONArray();
            for(int i=0; i<this.container.getChildren().size(); i++){
                JSONObject obj  = new JSONObject();
                HBox row = (HBox) this.container.getChildren().get(i);
                TextField desc =  (TextField) row.getChildren().get(0);
                TextField amount =  (TextField) row.getChildren().get(1);
                obj.put("description", desc.getText());
                obj.put("amount", amount.getText());
                expenses_array.put(obj);
            }
            expenses = expenses_array.toString();
        
        
            HttpResponse<String> res = Unirest.get(MetaData.baseUrl + "add/expenseVoucher")
                    .queryString("date", date)
                    .queryString("location", location)
                    .queryString("receivers_name", receivers_name)
                    .queryString("receivers_address", receivers_address)
                    .queryString("via", via)
                    .queryString("via_address", via_address)
                    .queryString("in_word", in_word)
                    .queryString("total", total)
                    .queryString("expenses", expenses)
                    .asString();
            System.out.println(expenses);
            if(res.getBody().equals("success")){
                Msg.showInformation("Expense voucher has been saved successfully.");
                
                Report report = new Report();
                Vector v = new Vector();
                HashMap params = new HashMap();
                params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString())));
                params.put("rcv_name", receivers_name);
                params.put("rcv_address", receivers_address);
                params.put("via_name", via);
                params.put("via_address", via_address);
                params.put("total_word", in_word);
                int sl = 1;
                for (int i = 0; i < expenses_array.length(); i++) {
                    JSONObject obj = expenses_array.getJSONObject(i);
                    v.add(new ExpenseVoucherEntry(String.valueOf(sl++), obj.getString("description"), obj.getString("amount")));
                }
                report.getReport("src\\report\\ExpenseVoucher.jrxml", new JRBeanCollectionDataSource(v), params, "Credit Voucher");
                
            }else{
                Msg.showError("");
            }
        } catch (Exception ex) {
            Msg.showError("");
        }
        
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }

    @FXML
    private void onAddNewButtonClick(ActionEvent event) {
        
        HBox row = new HBox();
        TextField desc = new TextField();
        TextField amount = new TextField();
        Button delete = new Button("Delete");
        desc.setPrefWidth(this.desc.getPrefWidth());
        amount.setPrefWidth(this.amount.getPrefWidth());
        row.getChildren().addAll(desc,amount,delete);
        row.setSpacing(this.row.getSpacing());
        this.container.getChildren().add(row);
        calculateTotal();
        delete.setOnAction((e)->{
            this.container.getChildren().remove(row);
            this.add_new.setDisable(false);
            calculateTotal();
        });
        amount.setOnKeyReleased((e)->{
            calculateTotal();
        });
        if(this.container.getChildren().size() >= 5){
            this.add_new.setDisable(true);
            return;
        }
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        this.add_new.setDisable(false);
        this.container.getChildren().remove(this.row);
        calculateTotal();
    }
    
    public void calculateTotal(){
        float sum = 0;
        for(int i=0; i<this.container.getChildren().size(); i++){
            HBox row = (HBox) this.container.getChildren().get(i);
            TextField amount = (TextField) row.getChildren().get(1);
            if(!amount.getText().equals("")) sum += Float.parseFloat(amount.getText());
        }
        this.total.setText(String.valueOf(sum));
        this.in_word.setText(EnglishNumberToWords.convert((long) sum) + " taka only");
    }
    
}
