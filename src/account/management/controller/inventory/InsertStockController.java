/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.MetaData;
import account.management.model.Product;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class InsertStockController implements Initializable {
    @FXML
    private Button add_row;
    @FXML
    private VBox conatiner;
    
    private List<Product> products_list;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> voucher_type;
    @FXML
    private TextField total;

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
                float p_qty = Float.parseFloat(obj.get("p_qty").toString());
                float s_qty = Float.parseFloat(obj.get("s_qty").toString());
                double last_p_rate = obj.getDouble("last_p_rate");
                double last_s_rate = obj.getDouble("last_s_rate");
                double avg_p_rate = obj.getDouble("avg_p_rate");
                double avg_s_rate = obj.getDouble("avg_s_rate");
                
                products_list.add(new Product(id, name, p_qty, s_qty, last_p_rate, last_s_rate, avg_p_rate, avg_s_rate));
                
            }
            
            addRow();
            
        } catch (Exception e) {
        }
        
        // voucher type (purchase/sell)
        this.voucher_type.getItems().addAll("Purchase", "Sell");
        this.voucher_type.getSelectionModel().select("Sell");
        
    }   
    
    public void addRow(){
        
        ComboBox<Product> select_item = new ComboBox();
        select_item.setPromptText("Select Item");
        select_item.setPrefWidth(190);
        select_item.setPrefHeight(25);
        
        TextField qty = new TextField();
        qty.setPromptText("Quantity");
        qty.setPrefWidth(97);
        qty.setPrefHeight(25);
        
        TextField rate = new TextField();
        rate.setPrefWidth(100);
        rate.setPrefHeight(25);
        
        if(this.voucher_type.getSelectionModel().getSelectedItem().equals("Purchase")){
            rate.setPromptText("Purchase Rate");
        }
        else{
            rate.setPromptText("Sell Rate");
        }
        
        Button del = new Button("Delete");
        
        HBox row = new HBox();
        row.getChildren().addAll(select_item, qty, rate,del);
        row.setSpacing(10);
        row.setPadding(new Insets(0, 0, 0, 15));
        
        this.conatiner.getChildren().add(row);
        
        del.setOnAction((e)->{
            this.conatiner.getChildren().remove(row);
            this.add_row.setDisable(false);
            calculateTotal();
        });
        
        select_item.getItems().addAll(this.products_list);
        
        select_item.setOnAction((e)->{
            qty.setText("0");
            if(this.voucher_type.getSelectionModel().getSelectedItem().equals("Purchase")){
                rate.setText(String.valueOf(select_item.getSelectionModel().getSelectedItem().getLast_p_rate()));
            }else{
                rate.setText(String.valueOf(select_item.getSelectionModel().getSelectedItem().getLast_s_rate()));
            }
            calculateTotal();
        });
        
        qty.setOnKeyReleased((e)->{
            calculateTotal();
        });
        rate.setOnKeyReleased((e)->{
            calculateTotal();
        });
        
        
        if(this.conatiner.getChildren().size() >= 8){
            this.add_row.setDisable(true);
            return;
        }
        
    }

    @FXML
    private void onAddRowButtonClick(ActionEvent event) {
        addRow();
    }
    
    
    public void calculateTotal(){
        double total = 0;
        try {
            for(int i=0; i<this.conatiner.getChildren().size(); i++){
                HBox row = (HBox) this.conatiner.getChildren().get(i);

                TextField qty = (TextField) row.getChildren().get(1);
                TextField rate = (TextField) row.getChildren().get(2);

                total += Double.parseDouble(rate.getText()) * Float.parseFloat(qty.getText());
            }
            this.total.setText(String.valueOf(total));
        } catch (Exception e) {
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        this.save.setDisable(true);
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString()));
            JSONArray array = new JSONArray();
            for(int i=0; i<this.conatiner.getChildren().size(); i++){
                HBox row = (HBox) this.conatiner.getChildren().get(i);
                ComboBox<Product> item = (ComboBox) row.getChildren().get(0);
                TextField qty = (TextField) row.getChildren().get(1);
                TextField rate = (TextField) row.getChildren().get(2);
                
                JSONObject obj = new JSONObject();
                obj.put("id", item.getSelectionModel().getSelectedItem().getId());
                obj.put("quantity", qty.getText());
                obj.put("rate", rate.getText());
                
                array.put(obj);
                
            }
            
            Unirest.post(MetaData.baseUrl + "products/ledger")
                    .field("voucher_type", this.voucher_type.getSelectionModel().getSelectedItem())
                    .field("date", date)
                    .field("products", array)
                    .asString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Ledger has been saved successfully!");
                alert.setGraphic(new ImageView(new Image("resources/success.jpg")));
                alert.showAndWait();
            this.save.setDisable(false);
        } catch (Exception ex) {
            Logger.getLogger(InsertStockController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void onVoucherTypeSelect(ActionEvent event) {
        this.conatiner.getChildren().clear();
        calculateTotal();
    }
}
