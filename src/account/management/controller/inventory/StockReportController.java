/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.MetaData;
import account.management.model.Product;
import account.management.model.Stock;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class StockReportController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;
    
    private List<Product> product_list;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product_list = FXCollections.observableArrayList();
        
        try {
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "all/products").asJson();
            JSONArray array = res.getBody().getArray();
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                float p_qty = Float.parseFloat(obj.get("p_qty").toString());
                float s_qty = Float.parseFloat(obj.get("s_qty").toString());
                double last_p_rate = obj.getDouble("last_p_rate");
                double last_s_rate = obj.getDouble("last_s_rate");
                double avg_p_rate = obj.getDouble("avg_p_rate");
                double avg_s_rate = obj.getDouble("avg_s_rate");
                
                product_list.add(new Product(id, name, p_qty, s_qty, last_p_rate, last_s_rate, avg_p_rate, avg_s_rate));
                
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error in the server. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
        
    }    

    @FXML
    private void onShowClick(ActionEvent event) {
        this.show.setDisable(true);
        String start_date = "1980-01-01", end_date = "2050-12-31";
        try {
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/stock")
                    .queryString("start", start_date)
                    .queryString("end", end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("date","From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String id = String.valueOf(obj.getInt("item_id"));
                String name = getProductName(Integer.parseInt(id));
                String opening_qty = obj.get("opening_qty").toString();
                String opening_price = obj.get("opening_price").toString() == "null" ? "0.0" : obj.get("opening_price").toString();
                String p_qty = obj.get("p_qty").toString() == "null" ? "0.0" : obj.get("p_qty").toString();
                String p_price = obj.get("p_price").toString() == "null" ? "0.0" : obj.get("p_price").toString();
                String s_qty = obj.get("s_qty").toString() == "null" ? "0.0" : obj.get("s_qty").toString();
                String s_price = obj.get("s_price").toString() == "null" ? "0.0" : obj.get("s_price").toString();
                
                String closing_qty = String.valueOf(Float.parseFloat(opening_qty) + Float.parseFloat(p_qty) - Float.parseFloat(s_qty));
                
                String closing_total = String.valueOf(Float.parseFloat(opening_price) + Float.parseFloat(p_price) - Float.parseFloat(s_price));
                String closing_rate = String.valueOf(Float.parseFloat(closing_total)/Float.parseFloat(closing_qty));
                v.add(new Stock(0,id,name,opening_qty,String.valueOf(Float.parseFloat(opening_price)/Float.parseFloat(opening_qty)), opening_price, closing_qty, closing_rate, closing_total));
                
            }
            
            Report report = new Report();
            report.getReport("src\\report\\StockReport.jrxml", new JRBeanCollectionDataSource(v), params, "Stock Report");
            this.show.setDisable(false);
        } catch (Exception ex) {
            Logger.getLogger(StockReportController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
    public String getProductName(int id){
       for(int i=0; i<this.product_list.size(); i++){
           if(this.product_list.get(i).getId() == id){
               return this.product_list.get(i).getName();
           }
       }
        return "";
    }
    
}
