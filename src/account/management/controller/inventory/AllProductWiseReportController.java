/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.AllProductWiseReport;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
public class AllProductWiseReportController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Button show;
    @FXML
    private Button cancel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void onShowReportClick(ActionEvent event) {
        this.show.setDisable(true);
        try {
            String start_date = "1980-01-01", end_date = "2050-12-31";
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start.getValue().toString()));
            end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end.getValue().toString()));
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "report/product/all/sellPurchase")
                    .queryString("start",start_date)
                    .queryString("end",end_date)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("date","From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" To " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String id = obj.get("id").toString().equals("null") ? "-" : obj.get("id").toString();
                String name = obj.get("name").toString().equals("null") ? "-" : obj.get("name").toString();
                String ob = obj.get("ob").toString().equals("null") ? "-" : obj.get("ob").toString();
                String p_qty = obj.get("p_qty").toString().equals("null") ? "-" : obj.get("p_qty").toString();
                String p_rate = obj.get("p_rate").toString().equals("null") ? "-" : obj.get("p_rate").toString();
                String p_total = obj.get("p_total").toString().equals("null") ? "-" : obj.get("p_total").toString();
                String s_qty = obj.get("s_qty").toString().equals("null") ? "-" : obj.get("s_qty").toString();
                String s_rate = obj.get("s_rate").toString().equals("null") ? "-" : obj.get("s_rate").toString();
                String s_total = obj.get("s_total").toString().equals("null") ? "-" : obj.get("s_total").toString();
                v.add(new AllProductWiseReport(id,name,ob,p_qty,p_rate,p_total,s_qty,s_rate,s_total));
                
            }
            Report report = new Report();
            report.getReport("src\\report\\AllProductPurchaseSellReport.jrxml", new JRBeanCollectionDataSource(v), params);
        } catch (ParseException ex) {
            Logger.getLogger(AllProductWiseReportController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter date correctly.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        } catch (UnirestException ex) {
            Logger.getLogger(AllProductWiseReportController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error in the server. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }finally{
            this.show.setDisable(false);
        }
            
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
