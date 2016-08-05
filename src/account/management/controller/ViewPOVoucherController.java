/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import account.management.model.POVoucher;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ViewPOVoucherController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private TableView<POVoucher> table;
    @FXML
    private TableColumn<POVoucher, String> no;
    @FXML
    private TableColumn<POVoucher, String> date;
    @FXML
    private TableColumn<POVoucher, String> name;
    @FXML
    private TableColumn<POVoucher, String> address;
    @FXML
    private TableColumn<POVoucher, String> total_amount;
    @FXML
    private TableColumn<POVoucher, String> opening;
    @FXML
    private TableColumn<POVoucher, String> total;
    @FXML
    private TableColumn<POVoucher, String> cash;
    @FXML
    private TableColumn<POVoucher, String> balance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.no.setCellValueFactory(new PropertyValueFactory("id"));
        this.date.setCellValueFactory(new PropertyValueFactory("date"));
        this.name.setCellValueFactory(new PropertyValueFactory("name"));
        this.address.setCellValueFactory(new PropertyValueFactory("address"));
        this.total_amount.setCellValueFactory(new PropertyValueFactory("total_amount"));
        this.opening.setCellValueFactory(new PropertyValueFactory("opening"));
        this.total.setCellValueFactory(new PropertyValueFactory("total"));
        this.cash.setCellValueFactory(new PropertyValueFactory("cash"));
        this.balance.setCellValueFactory(new PropertyValueFactory("balance"));
        
        
        this.table.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    POVoucher po = this.table.getSelectionModel().getSelectedItem();
                    try {
                        showReport(po.getId(), po.getName(), po.getAddress(), po.getDate(), po.getContent(), po.getTotal_amount(), po.getOpening(), po.getTotal(), po.getCash(), po.getBalance());
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewPOVoucherController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) throws ParseException {
        this.table.getItems().clear();
        String start = this.start.getValue().toString();
        String end = this.end.getValue().toString();
        
        try {
            JSONArray res = Unirest.get(MetaData.baseUrl + "get/purchase/order")
                    .queryString("start_date", start)
                    .queryString("end_date", end)
                    .asJson().getBody().getArray();
            
            int len = res.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = res.getJSONObject(i);
                String id = String.valueOf(obj.getInt("id"));
                String name = obj.getString("name");
                String address = obj.getString("address");
                String date = obj.getString("date");
                String content = obj.getString("content");
                String total_amount = String.valueOf(obj.getDouble("total_price"));
                String opening = String.valueOf(obj.getDouble("shabek"));
                String total = String.valueOf(obj.getDouble("grand_total"));
                String cash = String.valueOf(obj.getDouble("tt_dd_cash"));
                String balance = String.valueOf(obj.getDouble("jer"));
                
                
                this.table.getItems().add(new POVoucher(id, name, address, new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date)), content, total_amount, opening, total, cash, balance));
                
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(ViewPOVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
        
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
        params.put("date", date);
        params.put("total_amount", total_amount);
        params.put("opening", opening);
        params.put("total", total);
        params.put("cash", cash);
        params.put("balance", balance);
        
        
        report.getReport("src\\report\\PurchaseOrder.jrxml", new JRBeanCollectionDataSource(v), params, "Purchase Order");
    }
    
}
