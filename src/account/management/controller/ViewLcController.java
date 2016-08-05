/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.LC;
import account.management.model.MetaData;
import account.management.model.Project;
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
public class ViewLcController implements Initializable {
    @FXML
    private TableView<LC> tableView;
    @FXML
    private TableColumn<LC, String> lc_no;
    @FXML
    private TableColumn<LC, String> party_name;
    @FXML
    private TableColumn<LC, String> party_bank;
    @FXML
    private TableColumn<LC, String> party_address;
    @FXML
    private TableColumn<LC, String> our_bank;
    @FXML
    private TableColumn<LC, String> amount;
    @FXML
    private TableColumn<LC, String> init_date;
    @FXML
    private TableColumn<LC, String> start_date;
    @FXML
    private TableColumn<LC, String> dimilish_date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lc_no.setCellValueFactory(new PropertyValueFactory("lc_no"));
        party_name.setCellValueFactory(new PropertyValueFactory("party_name"));
        party_address.setCellValueFactory(new PropertyValueFactory("party_address"));
        party_bank.setCellValueFactory(new PropertyValueFactory("party_bank"));
        our_bank.setCellValueFactory(new PropertyValueFactory("our_bank"));
        amount.setCellValueFactory(new PropertyValueFactory("lc_amount"));
        init_date.setCellValueFactory(new PropertyValueFactory("formattedInitDate"));
        start_date.setCellValueFactory(new PropertyValueFactory("formattedStartDate"));
        dimilish_date.setCellValueFactory(new PropertyValueFactory("formattedDimilishDate"));
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/lc/all").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    String id = obj.get("id").toString();
                    String lc_no = obj.getString("lc_number");
                    String party_name = obj.getString("party_name");
                    String party_address = obj.getString("party_address");
                    String party_bank = obj.getString("party_bank_name");
                    String our_bank = obj.getString("our_bank_name");
                    String lc_amount = String.valueOf(Float.parseFloat(obj.getString("lc_amount")));
                    String init_date = obj.getString("initialing_date");
                    String start_date = obj.getString("starting_date");
                    String dimilish_date = obj.getString("dimilish_date");
                    
                    tableView.getItems().add(new LC(Long.parseLong(id),lc_no,party_name,party_address,party_bank,our_bank,lc_amount,init_date,start_date,dimilish_date));
                    //tableView.getItems().add(new Project(id,name,investment,related_party,starting_date,operation_date,dimilish_date));
                    
                }
            } catch (Exception e) {
            }
        }).start();
        
    }    
    
}
