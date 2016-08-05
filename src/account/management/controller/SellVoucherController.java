/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import account.management.model.PurchaseOrderData;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.event.ChangeListener;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class SellVoucherController implements Initializable {
    @FXML
    private DatePicker date;
    @FXML
    private TextField name;
    @FXML
    private TextArea address;
    @FXML
    private VBox container;
    @FXML
    private HBox row;
    @FXML
    private TextField sl;
    @FXML
    private TextField desc;
    @FXML
    private TextField qty;
    @FXML
    private TextField rate;
    @FXML
    private TextField total;
    @FXML
    private TextField commision;
    @FXML
    private TextField total_commision;
    @FXML
    private TextField neat_total;
    @FXML
    private TextField total_price;
    @FXML
    private TextField opening;
    @FXML
    private TextField grand_total;
    @FXML
    private TextField cash;
    @FXML
    private TextField balance;
    @FXML
    private Button delete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        balance.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue){
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        balance.requestFocus();
                        balance.selectAll();
                    }
                });
                
            }
        });
        this.container.getChildren().remove(0);
        new Thread(()->{
            try {
                Thread.sleep(5000);
                this.total_price.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                  if (keyEvent.getCode() == KeyCode.ENTER) {
                      System.out.println("attempting to submit deposit voucher");
                   //Stop letting it do anything else
                   keyEvent.consume();
                      try {
                          onSubmitButtonClick(null);
                      } catch (ParseException ex) {
                          Logger.getLogger(SalaryVoucherController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
                }
               });
            } catch (InterruptedException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }).start();
    }    

    @FXML
    private void onSubmitButtonClick(ActionEvent event) throws ParseException {
        try {
            
            JSONArray array = new JSONArray();
            int len = this.container.getChildren().size();
            for (int i = 0; i < len; i++) {
                HBox row = (HBox) this.container.getChildren().get(i);
                TextField sl_t = (TextField) row.getChildren().get(0);
                TextField desc_t = (TextField) row.getChildren().get(1);
                TextField qty_t = (TextField) row.getChildren().get(2);
                TextField rate_t = (TextField) row.getChildren().get(3);
                TextField total_t = (TextField) row.getChildren().get(4);
                TextField commision_t = (TextField) row.getChildren().get(5);
                TextField total_commision_t = (TextField) row.getChildren().get(6);
                TextField neat_amount_t = (TextField) row.getChildren().get(7);
                JSONObject obj = new JSONObject();
                obj.put("sl", sl_t.getText());
                obj.put("desc", desc_t.getText());
                obj.put("qty", qty_t.getText());
                obj.put("rate", rate_t.getText());
                obj.put("total", total_t.getText());
                obj.put("commision", commision_t.getText());
                obj.put("total_commision", total_commision_t.getText());
                obj.put("neat_amount", neat_amount_t.getText());
                array.put(obj);
            }
            
            JSONArray res = Unirest.get(MetaData.baseUrl + "add/sell/order")
                    .queryString("name", this.name.getText())
                    .queryString("address", this.address.getText())
                    .queryString("date", this.date.getValue().toString())
                    .queryString("total_price", this.total_price.getText())
                    .queryString("shabek", this.opening.getText())
                    .queryString("grand_total", this.grand_total.getText())
                    .queryString("tt_dd_cash", this.cash.getText())
                    .queryString("jer", this.balance.getText())
                    .queryString("content", array)
                    .asJson().getBody().getArray();
            JSONObject obj = res.getJSONObject(0);
            if(obj.getString("Status").equals("Success")){
                String voucher_no = Unirest.get(MetaData.baseUrl + "sell/order/lastId").asString().getBody();
                Msg.showInformation("Success");
                showReport(voucher_no, this.name.getText(), this.address.getText(), this.date.getValue().toString(), array.toString(), this.total_price.getText(), this.opening.getText(), this.grand_total.getText(), this.cash.getText(), this.balance.getText());
            }else{
                Msg.showError(obj.getString("Message"));
            }
        } catch (UnirestException ex) {
            Logger.getLogger(POVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
    }

    @FXML
    private void onAddRowButtonClick(ActionEvent event) {
        addNewRow();
    }
    
    public void addNewRow(){
        TextField sl = new TextField();
        sl.setPromptText("SL");
        sl.setPrefWidth(54);
        sl.setEditable(false);
        
        TextField desc = new TextField();
        desc.setPromptText("Description");
        desc.setPrefWidth(216);
        
        TextField qty = new TextField();
        qty.setPromptText("Quantity");
        qty.setPrefWidth(62);
        qty.setOnKeyReleased((e)->{ calculate(); });
        
        TextField rate = new TextField();
        rate.setPromptText("Rate");
        rate.setPrefWidth(72);
        rate.setOnKeyReleased((e)->{ calculate(); });
        
        TextField total = new TextField();
        total.setPromptText("Total");
        total.setPrefWidth(80);
        total.setEditable(false);
        
        TextField commision = new TextField();
        commision.setPromptText("Commision %");
        commision.setPrefWidth(90);
        commision.setOnKeyReleased((e)->{ calculate(); });
        
        TextField total_commision = new TextField();
        total_commision.setPromptText("Total Commision");
        total_commision.setPrefWidth(132);
        total_commision.setEditable(false);
        
        TextField neat_total = new TextField();
        neat_total.setPromptText("Neat Amount");
        neat_total.setPrefWidth(115);
        neat_total.setEditable(false);
        
        Button delete = new Button("Delete");
        
        
        HBox row = new HBox();
        row.setSpacing(10);
        row.getChildren().addAll(sl, desc, qty, rate, total, commision, total_commision, neat_total, delete);
        
        delete.setOnAction((e)->{
            this.container.getChildren().remove(row);
            calculate();
        });
        
        this.container.getChildren().add(row);
        calculate();
        
    }
    
    public void calculate(){
        int len = this.container.getChildren().size();
        double total_amount = 0;
        for (int i = 0; i < len; i++) {
            HBox row = (HBox) this.container.getChildren().get(i);
            TextField sl_t = (TextField) row.getChildren().get(0);
            TextField qty_t = (TextField) row.getChildren().get(2);
            TextField rate_t = (TextField) row.getChildren().get(3);
            TextField total_t = (TextField) row.getChildren().get(4);
            TextField commision_t = (TextField) row.getChildren().get(5);
            TextField total_commision_t = (TextField) row.getChildren().get(6);
            TextField neat_amount_t = (TextField) row.getChildren().get(7);
            
            sl_t.setText(String.valueOf(i+1));
            
            double qty = qty_t.getText().isEmpty() ? 0 : Double.parseDouble(qty_t.getText());
            double rate = rate_t.getText().isEmpty() ? 0 : Double.parseDouble(rate_t.getText());
            double total = total_t.getText().isEmpty() ? 0 : Double.parseDouble(total_t.getText());
            double commision = commision_t.getText().isEmpty() ? 0 : Double.parseDouble(commision_t.getText());
            double total_commision = total_commision_t.getText().isEmpty() ? 0 : Double.parseDouble(total_commision_t.getText());
            double neat_amount = neat_amount_t.getText().isEmpty() ? 0 : Double.parseDouble(neat_amount_t.getText());
            
            total = qty * rate;
            total_commision = total * commision / 100;
            neat_amount = total - total_commision;
            
            total_t.setText(String.valueOf(total));
            total_commision_t.setText(String.valueOf(total_commision));
            neat_amount_t.setText(String.valueOf(neat_amount));
            total_amount += neat_amount;
        }
        
        this.total_price.setText(String.valueOf(total_amount));
    }
    
    public void showReport(String sl, String name, String address, String date, String content, String total_amount, String opening, String total, String cash, String balance) throws ParseException{
        Report report = new Report();
        Vector v = new Vector();
        HashMap params = new HashMap();
        
        JSONArray array = new JSONArray(content);
        int len = array.length();
        for (int i = 0; i < len; i++) {
            JSONObject obj = array.getJSONObject(i);
            String sl_l = obj.getString("sl");
            String desc_l = obj.getString("desc");
            String qty_l = obj.getString("qty");
            String rate_l = obj.getString("rate");
            String total_l = obj.getString("total");
            String commision_l = obj.getString("commision");
            String total_commision_l = obj.getString("total_commision");
            String neat_amount_l = obj.getString("neat_amount");
            v.add(new PurchaseOrderData(sl_l, desc_l, qty_l, rate_l, total_l, commision_l + "%", total_commision_l, neat_amount_l));
        }
        
        params.put("voucher_no", sl);
        params.put("name", name);
        params.put("address", address);
        params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date)));
        params.put("total_amount", total_amount);
        params.put("opening", opening);
        params.put("total", total);
        params.put("cash", cash);
        params.put("balance", balance);
        
        
        report.getReport("src\\report\\SellOrder.jrxml", new JRBeanCollectionDataSource(v), params, "Purchase Order");
    }
    
}
