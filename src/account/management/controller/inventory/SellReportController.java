/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.MetaData;
import account.management.model.Product;
import account.management.model.SellPurchaseLedger;
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
public class SellReportController implements Initializable {
    @FXML
    private ComboBox<Product> item;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;
    private List<Product> products_list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void onShowReportButtonClick(ActionEvent event) {
        this.show.setDisable(true);
        try {
            
            String id = String.valueOf(this.item.getSelectionModel().getSelectedItem().getId());
            String start_date = "1980-01-01", end_date = "2050-12-31";
        
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/ledger/sell")
                    .queryString("id", id)
                    .queryString("start", start_date)
                    .queryString("end", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            System.out.println(array);
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("date","From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            params.put("item_name", "Purchase report of " + this.item.getSelectionModel().getSelectedItem().getName());
            
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String date = obj.getString("date");
                String quantity = obj.get("quantity").toString();
                String rate = obj.get("rate").toString();
                v.add(new SellPurchaseLedger(date, quantity, rate));
            }
            Report report = new Report();
            report.getReport("src\\report\\SellPurchaseLedger.jrxml", new JRBeanCollectionDataSource(v), params);
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
