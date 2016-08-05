/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.CNF;
import account.management.model.LC;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author unitech
 */
public class ViewCNFController implements Initializable {
    @FXML
    private TableView<CNF> tableView;
    @FXML
    private TableColumn<CNF, String> id;
    @FXML
    private TableColumn<CNF, String> name;
    @FXML
    private TableColumn<CNF, String> address;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("party_name"));
        address.setCellValueFactory(new PropertyValueFactory("party_address"));
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/cnf/all").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    String id = obj.get("id").toString();
                    String name = obj.getString("party_name");
                    String address = obj.getString("party_address");
                    
                    tableView.getItems().add(new CNF(id,name,address));
                    
                }
            } catch (Exception e) {
            }
        }).start();
        
    }    
    
}
