/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.Location;
import account.management.model.MetaData;
import account.management.model.Voucher;
import account.management.model.VoucherData;
import account.management.model.VoucherType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author unitech
 */
public class ViewVouchersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private List<Location> locations;
    @FXML
    private DatePicker start_date;
    @FXML
    private DatePicker end_date;
    @FXML
    private ComboBox<Location> select_location;
    @FXML
    private ComboBox<VoucherType> select_voucher_type;
    @FXML
    private Button search;
    @FXML
    private TableView<Voucher> table;

    @FXML
    private TableColumn<Voucher, String> c_id;
    @FXML
    private TableColumn<Voucher, String> c_date;
    @FXML
    private TableColumn<Voucher, String> c_location;
    @FXML
    private TableColumn<Voucher, String> c_narration;
    @FXML
    private TableColumn<Voucher, String> c_voucher_type;
    private List<Account> account_list;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(select_location);
        select_location.setOnHiding((e)->{
                Location a = select_location.getSelectionModel().getSelectedItem();
                select_location.setEditable(false);
                select_location.getSelectionModel().select(a);
        });
        select_location.setOnShowing((e)->{
                select_location.setEditable(true);
        });
        
        new AutoCompleteComboBoxListener<>(select_voucher_type);
        select_voucher_type.setOnHiding((e)->{
                VoucherType a = select_voucher_type.getSelectionModel().getSelectedItem();
                select_voucher_type.setEditable(false);
                select_voucher_type.getSelectionModel().select(a);
        });
        select_voucher_type.setOnShowing((e)->{
                select_voucher_type.setEditable(true);
        });
        
        
        account_list = FXCollections.observableArrayList();
        this.account_list = this.getAccounts();
        c_id.setCellValueFactory(new PropertyValueFactory("id"));
        c_date.setCellValueFactory(new PropertyValueFactory("date"));
        c_location.setCellValueFactory(new PropertyValueFactory("location"));
        c_narration.setCellValueFactory(new PropertyValueFactory("narration"));
        c_voucher_type.setCellValueFactory(new PropertyValueFactory("voucher_type"));
        
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            JSONArray res = Unirest.get(MetaData.baseUrl + "get/voucher/with/date")
                                    .queryString("start_date", start_date.getValue().toString())
                                    .queryString("end_date", end_date.getValue().toString())
                                    .queryString("id", table.getSelectionModel().getSelectedItem().getId())
                                    .asJson().getBody().getArray();
                            Voucher v = table.getSelectionModel().getSelectedItem();
                            gerReport("VN" + String.format("%03d", Integer.parseInt(v.getId())), v.getDate(), v.getNarration(), v.getVoucher_type(), v.getLocation(), res.toString());
                            System.out.println(res);
                        } catch (UnirestException ex) {
                            Logger.getLogger(ViewVouchersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        
        
        locations = FXCollections.observableArrayList();
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray location_array = response.getBody().getArray();
                locations.add(new Location(0,"All",""));
                for(int i=0; i<location_array.length()-1; i++){
                    String name = location_array.getJSONObject(i).getString("name");
                    String details = location_array.getJSONObject(i).getString("details");
                    int id = location_array.getJSONObject(i).getInt("id");
                    locations.add(new Location(id,name,details));
                }
                select_location.getItems().addAll(locations);
                
            } catch (UnirestException ex) {
                System.out.println("exception in UNIREST");
            }
        }).start();
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/voucher/type").asJson();
                JSONArray type = res.getBody().getArray();
                this.select_voucher_type.getItems().add(new VoucherType(0,"All",""));
                for(int i=0; i<type.length(); i++){
                    JSONObject obj = type.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("type_name").toString();
                    String note = obj.get("details").toString();
                    this.select_voucher_type.getItems().add(new VoucherType(id,name,note));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }    

    @FXML
    private void onSearchButtonClick(ActionEvent event) {
        this.table.getItems().clear();
        try {
            String start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start_date.getValue().toString()));
            String end_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.end_date.getValue().toString()));
            
            String location = String.valueOf(this.select_location.getSelectionModel().getSelectedItem().getId());
            String voucher_type = String.valueOf(this.select_voucher_type.getSelectionModel().getSelectedItem().getId());
            
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/voucher")
                    .queryString("start_date", start_date)
                    .queryString("end_date", end_date)
                    .queryString("location_id", location)
                    .queryString("voucher_type", voucher_type)
                    .asJson();
            JSONArray array = res.getBody().getArray();
            System.out.println(array);
            for(int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String id = String.valueOf(obj.getInt("id"));
                if(id.equals("1")) continue;
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("date")));
                String location_name = obj.getString("location");
                String narration = obj.getString("narration");
                String voucher_type_name  = obj.getString("voucher_type");
                
                this.table.getItems().add(new Voucher(id,date,location_name,narration,voucher_type_name));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ViewVouchersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerReport(String no, String date, String narration, String type, String location, String transaction){
        
        Report report = new Report();
        Vector v = new Vector();
        HashMap params = new HashMap();
        params.put("voucher_no", no);
        params.put("date", date);
        params.put("narration", narration);
        params.put("voucher_type", type);
        params.put("location", location);
        
        
        BufferedImage img = null;
        try {
                img = ImageIO.read(new File("src/Utopia.png"));
                params.put("logo", img);
        } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JSONArray array = new JSONArray(transaction);
        int len = array.length();
        for (int i = 0; i < len; i++) {
            JSONObject obj = array.getJSONObject(i);
            JSONArray transactions = obj.getJSONArray("transactions");
            int len2 = transactions.length();
            for (int j = 0; j < len2; j++) {
                JSONObject obj2 = transactions.getJSONObject(j);
                int acc_id = obj2.getInt("account_id");
                String dr = String.valueOf(Double.parseDouble(obj2.getString("dr"))).equals("0.0") ? "" : String.valueOf(Double.parseDouble(obj2.getString("dr")));
                String cr = String.valueOf(Double.parseDouble(obj2.getString("cr"))).equals("0.0") ? "" : String.valueOf(Double.parseDouble(obj2.getString("cr")));
                String remark = obj2.getString("remark");
                v.add(new VoucherData(getAccountNameFromAccountId(acc_id), dr, cr, remark));
            }
        }
        
        report.getReport("src\\report\\JournalVoucher.jrxml", new JRBeanCollectionDataSource(v), params, "Journal Voucher");
        
    }
    
    public List<Account> getAccounts(){
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(MetaData.baseUrl + "get/accounts").asJson();
        } catch (UnirestException ex) {
            Logger.getLogger(ViewVouchersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray account = response.getBody().getArray();
        List<Account> account_list = FXCollections.observableArrayList();
        for(int i=1; i<account.length(); i++){
            JSONObject obj = account.getJSONObject(i);
            int id = Integer.parseInt(obj.get("id").toString());
            String name = obj.get("name").toString();
            String desc = obj.get("description").toString();
            int parent_id = Integer.parseInt(obj.get("parent").toString());

            account_list.add(new Account(id,name,parent_id,desc,0f, obj.get("account_type").toString()));

        }
        return account_list;
    }
    
    public String getAccountNameFromAccountId(int acc_id){
        
        
        
        int len = account_list.size();
        for (int i = 0; i < len; i++) {
            if(account_list.get(i).getId() == acc_id){
                return account_list.get(i).getName();
            }
        }
        return "";
    }

    
}
