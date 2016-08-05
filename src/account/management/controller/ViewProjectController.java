/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Project;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ViewProjectController implements Initializable {
    @FXML
    private TableView<Project> tableView;
    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, String> investment;
    @FXML
    private TableColumn<Project, String> party;
    @FXML
    private TableColumn<Project, String> start_date;
    @FXML
    private TableColumn<Project, String> operation_date;
    @FXML
    private TableColumn<Project, String> dimilish_date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.name.setCellValueFactory(new PropertyValueFactory("name"));
        this.investment.setCellValueFactory(new PropertyValueFactory("investment"));
        this.party.setCellValueFactory(new PropertyValueFactory("party"));
        this.start_date.setCellValueFactory(new PropertyValueFactory("formattedStartDate"));
        this.operation_date.setCellValueFactory(new PropertyValueFactory("formattedOperationDate"));
        this.dimilish_date.setCellValueFactory(new PropertyValueFactory("formattedDimilishDate"));
        
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/project/all").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    String id = obj.get("id").toString();
                    String name = obj.getString("name");
                    String investment = String.valueOf(Float.parseFloat(obj.getString("investment")));
                    String related_party = obj.getString("related_party");
                    String starting_date = obj.getString("starting_date");
                    String operation_date = obj.getString("operation_date");
                    String dimilish_date = obj.getString("dimilish_date");
                    
                    tableView.getItems().add(new Project(id,name,investment,related_party,starting_date,operation_date,dimilish_date));
                    
                }
            } catch (UnirestException ex) {
                Logger.getLogger(ViewProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }    
    
}
