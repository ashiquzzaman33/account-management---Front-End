/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Amimul Ahsan
 */
public class CreateUserController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private PasswordField auth_password;
    @FXML
    private CheckBox inventory;
    @FXML
    private CheckBox project;
    @FXML
    private CheckBox lc;
    @FXML
    private CheckBox cnf;
    @FXML
    private CheckBox deposit_voucher;
    @FXML
    private CheckBox expense_voucher;
    @FXML
    private CheckBox sell;
    @FXML
    private CheckBox purchase;
    @FXML
    private CheckBox party;
    @FXML
    private CheckBox ledger;
    @FXML
    private CheckBox voucher;
    @FXML
    private CheckBox bank;
    @FXML
    private CheckBox inventory_report;
    @FXML
    private CheckBox trial_balance;
    @FXML
    private CheckBox balance_sheet;
    @FXML
    private CheckBox financal_statement;
    @FXML
    private CheckBox database_maintanance;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private CheckBox check_all;
    @FXML
    private CheckBox uncheck_all;
    @FXML
    private CheckBox party_wise_report;
    @FXML
    private CheckBox receipt_payment_report;
    @FXML
    private CheckBox salary_voucher;
    @FXML
    private CheckBox purchase_order;
    @FXML
    private CheckBox sell_voucher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.check_all.setOnAction((e)->{
            if(this.check_all.selectedProperty().getValue()){
                checkAll();
                this.uncheck_all.setSelected(false);
            }else{
                uncheckAll();
            }
        });
        
        this.uncheck_all.setOnAction((e)->{
            if(this.uncheck_all.selectedProperty().getValue()){
                uncheckAll();
                this.check_all.setSelected(false);
            }else{
                uncheckAll();
            }
        });
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        
        try {
            String username = this.username.getText();
            String password = this.password.getText();
            String confirm_password = this.confirm_password.getText();
            String auth_password = this.auth_password.getText();
            String inventory = this.inventory.selectedProperty().getValue().toString();
            String project = this.project.selectedProperty().getValue().toString();
            String lc = this.lc.selectedProperty().getValue().toString();
            String cnf = this.cnf.selectedProperty().getValue().toString();
            String deposit_voucher = this.deposit_voucher.selectedProperty().getValue().toString();
            String expense_voucher = this.expense_voucher.selectedProperty().getValue().toString();
            String sell = this.sell.selectedProperty().getValue().toString();
            String purchase = this.purchase.selectedProperty().getValue().toString();
            String party = this.party.selectedProperty().getValue().toString();
            String ledger = this.ledger.selectedProperty().getValue().toString();
            String voucher = this.voucher.selectedProperty().getValue().toString();
            String bank = this.bank.selectedProperty().getValue().toString();
            String inventory_report = this.inventory_report.selectedProperty().getValue().toString();
            String trial_balance = this.trial_balance.selectedProperty().getValue().toString();
            String balance_sheet = this.balance_sheet.selectedProperty().getValue().toString();
            String financial_statement = this.inventory.selectedProperty().getValue().toString();
            String database = this.database_maintanance.selectedProperty().getValue().toString();
            String party_wise_report = this.party_wise_report.selectedProperty().getValue().toString();
            String receipt_payment_report = this.receipt_payment_report.selectedProperty().getValue().toString();
            String salary_voucher = this.salary_voucher.selectedProperty().getValue().toString();
            String purchase_order = this.purchase_order.selectedProperty().getValue().toString();
            String sell_voucher = this.sell_voucher.selectedProperty().getValue().toString();
            
            if(!password.equals(confirm_password)){
                Msg.showError("You must enter same password");
                this.confirm_password.setText("");
                this.confirm_password.setFocusTraversable(true);
                return;
            }
            
            HttpResponse<String> res = Unirest.post(MetaData.baseUrl + "create/users")
                    .field("username", username)
                    .field("password", password)
                    .field("inventory", inventory)
                    .field("project", project)
                    .field("lc", lc)
                    .field("cnf", cnf)
                    .field("deposit_voucher", deposit_voucher)
                    .field("expense_voucher", expense_voucher)
                    .field("sell", sell)
                    .field("purchase", purchase)
                    .field("party_create", party)
                    .field("ledger_create", ledger)
                    .field("voucher", voucher)
                    .field("bank", bank)
                    .field("inventory_report", inventory_report)
                    .field("trial_balance", trial_balance)
                    .field("balance_sheet", balance_sheet)
                    .field("financial_statement", financial_statement)
                    .field("database_maintanance", database)
                    .field("party_wise_report", party_wise_report)
                    .field("receipt_payment_report", receipt_payment_report)
                    .field("salary_voucher", salary_voucher)
                    .field("purchase_order", purchase_order)
                    .field("sell_voucher", sell_voucher)
                    .asString();
            if(res.getBody().equals("success")){
                Msg.showInformation("User has been created successfully");
                this.save.getScene().getWindow().hide();
            }else{
                Msg.showError("Sorry.There is a problem. Please try again");
            }
            
        } catch (Exception e) {
            Msg.showError("Sorry.There is a problem. Please try again");
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }

    @FXML
    private void onCheckAllClick(ActionEvent event) {
        
    }

    @FXML
    private void onUncheckAllClick(ActionEvent event) {
        
    }
    
    public void checkAll(){
        this.inventory.setSelected(true);
        this.project.setSelected(true);
        this.lc.setSelected(true);
        this.cnf.setSelected(true);
        this.deposit_voucher.setSelected(true);
        this.expense_voucher.setSelected(true);
        this.sell.setSelected(true);
        this.purchase.setSelected(true);
        this.party.setSelected(true);
        this.ledger.setSelected(true);
        this.voucher.setSelected(true);
        this.bank.setSelected(true);
        this.inventory_report.setSelected(true);
        this.trial_balance.setSelected(true);
        this.balance_sheet.setSelected(true);
        this.financal_statement.setSelected(true);
        this.database_maintanance.setSelected(true);
    }
    
    public void uncheckAll(){
        this.inventory.setSelected(false);
        this.project.setSelected(false);
        this.lc.setSelected(false);
        this.cnf.setSelected(false);
        this.deposit_voucher.setSelected(false);
        this.expense_voucher.setSelected(false);
        this.sell.setSelected(false);
        this.purchase.setSelected(false);
        this.party.setSelected(false);
        this.ledger.setSelected(false);
        this.voucher.setSelected(false);
        this.bank.setSelected(false);
        this.inventory_report.setSelected(false);
        this.trial_balance.setSelected(false);
        this.balance_sheet.setSelected(false);
        this.financal_statement.setSelected(false);
        this.database_maintanance.setSelected(false);
    }
    
}
