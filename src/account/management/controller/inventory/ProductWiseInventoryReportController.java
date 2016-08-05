/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.IndividualProductReport;
import account.management.model.MetaData;
import account.management.model.Product;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ProductWiseInventoryReportController implements Initializable {
    @FXML
    private ComboBox<Product> item;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    
    private List<Product> products_list;
    @FXML
    private Button cancel;
    @FXML
    private Button show;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(item);
        item.setOnHiding((e)->{
                Product a = item.getSelectionModel().getSelectedItem();
                item.setEditable(false);
                item.getSelectionModel().select(a);
        });
        item.setOnShowing((e)->{
                item.setEditable(true);
        });
        
        // get product list
        products_list = FXCollections.observableArrayList();
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "all/products").asJson();
            JSONArray array = res.getBody().getArray();
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                products_list.add(new Product(id, name));
                
            }
            
            this.item.getItems().addAll(products_list);
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error in the server. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
    }    

    @FXML
    private void onShowReportClick(ActionEvent event) {
        this.show.setDisable(true);
        try {
            
            String id = String.valueOf(this.item.getSelectionModel().getSelectedItem().getId());
            String start_date = "1980-01-01", end_date = "2050-12-31";
        
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/product/sellPurchase")
                    .queryString("id", id)
                    .queryString("start", start_date)
                    .queryString("end", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("date","From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            params.put("item_name", "Inventory report of " + this.item.getSelectionModel().getSelectedItem().getName());
            float total_p_qty = 0, total_s_qty = 0;
            double total_p_param = 0;
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String date = obj.getString("date");
                date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                String p_qty = obj.get("p_qty").toString()=="null" ? "-" : obj.get("p_qty").toString();
                String p_rate = obj.get("p_rate").toString()=="null" ? "-" : obj.get("p_rate").toString();
                String p_price = obj.get("p_total").toString()=="null" ? "-" : obj.get("p_total").toString();
                String s_qty = obj.get("s_qty").toString()=="null" ? "-" : obj.get("s_qty").toString();
                String s_rate = obj.get("s_rate").toString()=="null" ? "-" : obj.get("s_rate").toString();
                String s_price = obj.get("s_total").toString()=="null" ? "-" : obj.get("s_total").toString();
                System.out.println(s_price);
                v.add(new IndividualProductReport(date, p_qty, p_rate, p_price, s_qty, s_rate, s_price));
                
                if(!p_qty.equals("-")){
                    total_p_qty += Float.parseFloat(p_qty);
                }
                if(!s_qty.equals("-")){
                    total_s_qty += Float.parseFloat(s_qty);
                }
                if(!p_price.equals("-")){
                    total_p_param += Float.parseFloat(p_price);
                }
                
            }
            params.put("closing_qty", String.valueOf(total_p_qty - total_s_qty));
            System.out.println(total_p_qty);
            params.put("closing_rate", String.valueOf(total_p_param/total_p_qty));
            params.put("closing_total", String.valueOf((total_p_qty - total_s_qty) * (total_p_param/total_p_qty)));
            Report report = new Report();
            report.getReport("src\\report\\IndiviualProductPurchaseSellReport.jrxml", new JRBeanCollectionDataSource(v), params, "Product Wise Inventory Report");
            this.show.setDisable(false);
        } catch (Exception e) {
            System.out.println("Exception in show report button click");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
