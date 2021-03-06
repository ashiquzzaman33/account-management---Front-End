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
import account.management.model.Msg;
import account.management.model.Project;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class NewVoucherController implements Initializable {
    @FXML
    private VBox field_container;
    @FXML
    private HBox field_row;
    @FXML
    private ComboBox<Location> select_location;
    @FXML
    private ComboBox<VoucherType> select_voucher_type;
    @FXML
    private Button button_submit;
    
    private Collection<Location> locations;
    @FXML
    private Pane title_pane;
    @FXML
    private AnchorPane main_container;
    @FXML
    private DatePicker input_date;
    @FXML
    private TextArea input_narration;
    @FXML
    private Button button_add_new_field;
    @FXML
    private Button button_delete_row;
    @FXML
    private RadioButton project;
    @FXML
    private RadioButton lc;
    @FXML
    private RadioButton cnf;
    @FXML
    private ComboBox<Project> select_type;
    private List<Account> account_list;
    private List<Account> filter_acc;
    private List<Account> filter_party_rec;
    private List<Account> filter_party_pay;
    @FXML
    private RadioButton project1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            onAddNewFieldButtonClick(null);
        });
        
        account_list = FXCollections.observableArrayList();
        filter_party_rec = FXCollections.observableArrayList();
        filter_party_pay = FXCollections.observableArrayList();
        
        new AutoCompleteComboBoxListener<>(select_voucher_type);
        select_voucher_type.setOnHiding((e)->{
                VoucherType a = select_voucher_type.getSelectionModel().getSelectedItem();
                select_voucher_type.setEditable(false);
                select_voucher_type.getSelectionModel().select(a);
        });
        select_voucher_type.setOnShowing((e)->{
                select_voucher_type.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(select_location);
        select_location.setOnHiding((e)->{
                Location a = select_location.getSelectionModel().getSelectedItem();
                select_location.setEditable(false);
                select_location.getSelectionModel().select(a);
        });
        select_location.setOnShowing((e)->{
                select_location.setEditable(true);
        });
        
        new AutoCompleteComboBoxListener<>(select_type);
        select_type.setOnHiding((e)->{
                Project a = select_type.getSelectionModel().getSelectedItem();
                select_type.setEditable(false);
                select_type.getSelectionModel().select(a);
        });
        select_type.setOnShowing((e)->{
                select_type.setEditable(true);
        });

        
        new Thread(()->{
            try {
                Thread.sleep(5000);
                this.button_submit.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                  if (keyEvent.getCode() == KeyCode.ENTER) {
                      System.out.println("attempting to submit new voucher");
                   //Stop letting it do anything else
                   keyEvent.consume();
                   onSubmitButtonClick(null);
                  }
                }
               });
            } catch (InterruptedException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }).start();
        
       
        /*
        *   voucher type
        */
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/voucher/type").asJson();
                JSONArray type = res.getBody().getArray();
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
        
        /*
        *   init locations in select_location combobox
        */
        locations = FXCollections.observableArrayList();
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray location_array = response.getBody().getArray();
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
        
        this.button_submit.setDisable(true);
        
        field_container.setOnKeyReleased((KeyEvent event)->{
            validateFields();
        });
        
        
        ToggleGroup tg = new ToggleGroup();
        this.project.setToggleGroup(tg);
        this.lc.setToggleGroup(tg);
        this.cnf.setToggleGroup(tg);
       
        /*
        *   init account name
        */
        new Thread(()->{
            final ComboBox<Account> a = (ComboBox<Account>) this.field_row.getChildren().get(0);
            try {

                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/accounts").asJson();
                JSONArray account = response.getBody().getArray();
                for(int i=1; i<account.length(); i++){
                    JSONObject obj = account.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("name").toString();
                    String desc = obj.get("description").toString();
                    int parent_id = Integer.parseInt(obj.get("parent").toString());

                    account_list.add(new Account(id,name,parent_id,desc,0f, obj.get("account_type").toString()));
                    if(parent_id == 21){
                        this.filter_party_rec.add(new Account(id,name,parent_id,desc,0f, obj.get("account_type").toString()));
                    }
                    if(parent_id == 34){
                        this.filter_party_pay.add(new Account(id,name,parent_id,desc,0f, obj.get("account_type").toString()));
                    }
                }
                
                a.getItems().addAll(account_list);
                
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                new AutoCompleteComboBoxListener<>(a);
                a.setOnHiding((e)->{
                    Account acc = a.getSelectionModel().getSelectedItem();
                    a.setEditable(false);
                    a.getSelectionModel().select(acc);
                });
                a.setOnShowing((e)->{
                    a.setEditable(true);
                });
                
                a.setOnAction((e)->{
                    if(!a.getSelectionModel().isEmpty() && a.getSelectionModel().getSelectedItem().getId() == 21){
                        a.setPromptText("Select Party");
                        a.getItems().clear();
                        a.getItems().addAll(this.filter_party_rec);
                    }
                    if(!a.getSelectionModel().isEmpty() && a.getSelectionModel().getSelectedItem().getId() == 34){
                        a.getItems().clear();
                        a.getItems().addAll(this.filter_party_pay);
                        a.setPromptText("Select Party");
                    }
                });
                
                
            }
        }).start();
        
        
        

    }    

    @FXML
    private void onAddNewFieldButtonClick(ActionEvent event) {
        
        HBox row = new HBox();
        row.setId("field_row");
        ComboBox<Account> select_account = new ComboBox<>();
        if(this.select_type.getSelectionModel().isEmpty()){
            select_account.getItems().addAll(this.account_list);
        }else{
            select_account.getItems().addAll(this.filter_acc);
        }
        
        TextField dr        = new TextField();
        TextField cr        = new TextField();
        TextField remarks   = new TextField();
        Button del_row = new Button("Delete");
        
        row.setSpacing(field_row.getSpacing());
        
        ComboBox<Account> combo = (ComboBox)field_row.getChildren().get(0);
        select_account.setPrefWidth(combo.getPrefWidth());
        select_account.setPromptText("Select account");
        
        TextField tf = (TextField)field_row.getChildren().get(1);
        dr.setPrefWidth(tf.getPrefWidth());
        dr.setPromptText("Dr");
        
        tf = (TextField)field_row.getChildren().get(2);
        cr.setPrefWidth(tf.getPrefWidth());
        cr.setPromptText("Cr");
        
        tf = (TextField)field_row.getChildren().get(3);
        remarks.setPrefWidth(tf.getPrefWidth());
        remarks.setPromptText("remarks");
        
        row.getChildren().addAll(select_account, dr, cr, remarks, del_row);
        field_container.getChildren().add(row);
        
        del_row.setOnMouseClicked((MouseEvent event1) -> {
            field_container.getChildren().removeAll(row);
            validateFields();
        });
        
        combo.setOnAction((e)->{
            if(!combo.getSelectionModel().isEmpty() && combo.getSelectionModel().getSelectedItem().getId() == 21){
                combo.setPromptText("Select Party");
                combo.getItems().clear();
                combo.getItems().addAll(this.filter_party_rec);
            }
            if(!combo.getSelectionModel().isEmpty() && combo.getSelectionModel().getSelectedItem().getId() == 34){
                combo.getItems().clear();
                combo.getItems().addAll(this.filter_party_pay);
                combo.setPromptText("Select Party");
            }
        });
        
        new AutoCompleteComboBoxListener<>(select_account);
        select_account.setOnHiding((e)->{
            Account a = select_account.getSelectionModel().getSelectedItem();
            select_account.setEditable(false);
            select_account.getSelectionModel().select(a);
        });
        select_account.setOnShowing((e)->{
            select_account.setEditable(true);
        });
        
        validateFields();
        
    }


    @FXML
    private void onDeleteRowButtonClick(MouseEvent event) {
        field_container.getChildren().removeAll(field_row);
        validateFields();
    }
    
    private void validateFields(){
        
        int dr_counter = 0, cr_counter = 0;
        float total_dr = 0f, total_cr = 0f;
        
        for(int i=0; i<field_container.getChildren().size(); i++){
            HBox row = (HBox) field_container.getChildren().get(i);
            TextField dr = (TextField) row.getChildren().get(1);
            TextField cr = (TextField) row.getChildren().get(2);
            
            if(dr.getText().equals("") && cr.getText().equals("")){
                this.button_submit.setDisable(true);
                return;
            }
            if(!dr.getText().equals("") && !cr.getText().equals("")){
                this.button_submit.setDisable(true);
                return;
            }
            /*
            *   regular expression check
            */
            Pattern pattern = Pattern.compile("^[0-9]*\\.?[0-9]+$");
            Matcher match = pattern.matcher(dr.getText());
            if(!dr.getText().equals("") && !match.find()){
                this.button_submit.setDisable(true);
                return;
            }
            match = pattern.matcher(cr.getText());
            if(!cr.getText().equals("") && !match.find()){
                this.button_submit.setDisable(true);
                return;
            }
            
            
            if(!dr.getText().equals("")){
                dr_counter++;
                total_dr += Float.parseFloat(dr.getText());
            }
            if(!cr.getText().equals("")){
                cr_counter++;
                total_cr += Float.parseFloat(cr.getText());
            }
                
        }
        
        if((dr_counter > 1 && cr_counter > 1) || dr_counter ==0 || cr_counter == 0 || (total_dr != total_cr)){
            this.button_submit.setDisable(true);
            return;
        }
        
        this.button_submit.setDisable(false);
        
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
//        if(new Date().getTime() > 1471365130021l){
//            System.exit(0);
//        }
        
        try {
            String loc, project_id = "0", date, narration;
            JSONObject transaction;
            loc = String.valueOf(this.select_location.getSelectionModel().getSelectedItem().getId());
            if(this.select_type.getSelectionModel().isEmpty()){
                project_id = "0";
            }else{
                project_id = this.select_type.getSelectionModel().getSelectedItem().getId();
                
            }   
            date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.input_date.getValue().toString())) + " 00:00:00";
            narration = this.input_narration.getText();
            
            transaction = new JSONObject();
            JSONArray transactionArray = new JSONArray();
            JSONArray transaction_print = new JSONArray();
            for(int i=0; i<this.field_container.getChildren().size(); i++){
               HBox row = (HBox) this.field_container.getChildren().get(i);
               JSONObject inner = new JSONObject();
               JSONObject transaction_obj_print = new JSONObject();
               ComboBox<Account> acc = (ComboBox<Account>) row.getChildren().get(0);
               TextField dr =  (TextField) row.getChildren().get(1);
               TextField cr =  (TextField) row.getChildren().get(2);
               TextField remarks =  (TextField) row.getChildren().get(3);
               int acc_id;
               float amount;
               String remark;
               acc_id = acc.getSelectionModel().getSelectedItem().getId();
               if(!dr.getText().equals("")){
                   amount = Float.parseFloat(dr.getText());
               }else{
                   amount = Float.parseFloat(cr.getText());
                   amount *= -1;
               }
               transaction_obj_print.put("account_id", acc.getSelectionModel().getSelectedItem().getId());
               transaction_obj_print.put("dr", dr.getText().isEmpty() ? "" : dr.getText());
               transaction_obj_print.put("cr", cr.getText().isEmpty() ? "" : cr.getText());
               transaction_obj_print.put("remark", remarks.getText().isEmpty() ? "" : remarks.getText());
               transaction_print.put(transaction_obj_print);
               remark = remarks.getText();
               
               inner.put("amount", String.valueOf(amount));
               inner.put("account_id", String.valueOf(acc_id));
               inner.put("remark", narration);
               transactionArray.put(inner);
            }
            
            transaction.put("transaction", transactionArray);
            System.out.println(transaction);
            HttpResponse<JsonNode> res = null;
            try {
                res = Unirest.post(MetaData.baseUrl + "add/voucher")
                        .field("location_id", loc)
                        .field("voucher_type", this.select_voucher_type.getSelectionModel().getSelectedItem().getId())
                        .field("projectOrCnf", project_id)
                        .field("date", date)
                        .field("narration", narration)
                        .field("transaction", transaction)
                        .asJson();
                
                        JSONObject obj = res.getBody().getArray().getJSONObject(0);
                        if(obj.getString("Status").equals("Success")){
                            Msg.showInformation("Voucher has been saved successfully!!!");
                            JSONObject obj2 = new JSONObject();
                            obj2.put("transactions", transaction_print);
                            JSONArray transaction_array_print = new JSONArray();
                            transaction_array_print.put(obj2);
                            String voucher_id = Unirest.get(MetaData.baseUrl + "get/lastVoucherId").asString().getBody();
                            gerReport(voucher_id, new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date)), narration, this.select_voucher_type.getSelectionModel().getSelectedItem().getName(), this.select_location.getSelectionModel().getSelectedItem().getName(), transaction_array_print.toString());
                            
                        }else{
                            Msg.showError("Sorry. Something is wrong. Please try again.");
                        }
                        
            } catch (UnirestException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Sorry!! there is an error in the server. Please try again.");
                        alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
                        alert.showAndWait();
            }finally{
                System.out.println(res.getBody());
                
            }
            
            
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
        

    }

    @FXML
    private void onProjectSelect(ActionEvent event) {
        new Thread(()->{
            this.select_type.getItems().clear();
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/project/all").asJson();
                JSONArray projects = response.getBody().getArray();
                for(int i=0; i<projects.length(); i++){
                    JSONObject obj = projects.getJSONObject(i);
                    this.select_type.getItems().add(new Project(obj.get("id").toString(),obj.get("name").toString()));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    @FXML
    private void onLcSelect(ActionEvent event) {
        
        new Thread(()->{
            this.select_type.getItems().clear();
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/lc/all").asJson();
                JSONArray projects = response.getBody().getArray();
                for(int i=0; i<projects.length(); i++){
                    JSONObject obj = projects.getJSONObject(i);
                    this.select_type.getItems().add(new Project(obj.get("id").toString(),obj.get("lc_number").toString() + " -- " + obj.get("party_name").toString()));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }

    @FXML
    private void onCNFSelect(ActionEvent event) {
        new Thread(()->{
            this.select_type.getItems().clear();
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/cnf/all").asJson();
                JSONArray projects = response.getBody().getArray();
                for(int i=0; i<projects.length(); i++){
                    JSONObject obj = projects.getJSONObject(i);
                    this.select_type.getItems().add(new Project(obj.get("id").toString(),obj.get("party_name").toString()));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(NewVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    @FXML
    private void onSelectType(ActionEvent event) {
        filter_acc = FXCollections.observableArrayList();
        for(int i=0; i<this.account_list.size(); i++){
            if(this.account_list.get(i).getAccount_type().equals(this.select_type.getSelectionModel().getSelectedItem().getId()) && this.account_list.get(i).getId() > 57){
               filter_acc.add(this.account_list.get(i));
               
            }
        }
        //this.account_list.clear();
        for(int i=0; i<this.field_container.getChildren().size(); i++){
            HBox row = (HBox) this.field_container.getChildren().get(i);
            ComboBox<Account> combo = (ComboBox<Account>) row.getChildren().get(0);
            combo.getItems().clear();
            combo.getItems().addAll(filter_acc);
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
                String dr = obj2.getString("dr");
                String cr = obj2.getString("cr");
                String remark = obj2.getString("remark");
                v.add(new VoucherData(getAccountNameFromAccountId(acc_id), dr, cr, remark));
            }
        }
        
        report.getReport("src\\report\\JournalVoucher.jrxml", new JRBeanCollectionDataSource(v), params, "Journal Voucher");
        
    }
    
    public String getAccountNameFromAccountId(int id){
        int len = this.account_list.size();
        for (int i = 0; i < len; i++) {
            if(this.account_list.get(i).getId() == id){
                return this.account_list.get(i).getName();
            }
        }
        return "";
    }
    

    
}

