/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Raju
 */
public class TopNavController implements Initializable{
    @FXML
    private MenuItem logout;
    @FXML
    private MenuBar top_navbar;
    @FXML
    private MenuItem depositVoucher;
    @FXML
    private MenuItem expense_voucher;
    @FXML
    private MenuItem depositVoucher1;
    @FXML
    private MenuItem ledger_create;
    @FXML
    private Menu bank;
    @FXML
    private Menu voucher;
    @FXML
    private Menu project;
    @FXML
    private Menu lc;
    @FXML
    private Menu cnf;
    @FXML
    private MenuItem party_create;
    @FXML
    private Menu inventory;
    @FXML
    private MenuItem purchase;
    @FXML
    private MenuItem sell;
    @FXML
    private Menu inventory_report;
    @FXML
    private MenuItem balance_sheet;
    @FXML
    private MenuItem trial_balance;
    @FXML
    private MenuItem salary_voucher;
    @FXML
    private MenuItem view_salary_voucher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!User.inventory){
            this.inventory.setDisable(true);
        }
        if(!User.project){
            this.project.setDisable(true);
        }
        if(!User.lc){
            this.lc.setDisable(true);
        }
        if(!User.cnf){
            this.cnf.setDisable(true);
        }
        if(!User.deposit_voucher){
            this.depositVoucher.setDisable(true);
        }
        if(!User.expense_voucher){
            this.expense_voucher.setDisable(true);
        }
        if(!User.sell){
            this.sell.setDisable(true);
        }
        if(!User.purchase){
            this.purchase.setDisable(true);
        }
        if(!User.party_create){
            this.party_create.setDisable(true);
        }
        if(!User.ledger){
            this.ledger_create.setDisable(true);
        }
        if(!User.voucher){
            this.voucher.setDisable(true);
        }
        if(!User.bank){
            this.bank.setDisable(true);
        }
        if(!User.trial_balance){
            this.trial_balance.setDisable(true);
        }
        if(!User.balance_sheet){
            this.balance_sheet.setDisable(true);
        }
        if(!User.financial_statement){
            this.balance_sheet.setDisable(true);
        }
    }

    @FXML
    private void onLogoutMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "login.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle("Uni Accounts - Login");
    }

    @FXML
    private void onAddLocationMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "AddLocation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Add new location");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onEditLocationMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "editLocation.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle("Uni Accounts - Edit Location");
    }

    @FXML
    private void onCreateAccountMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateAccount2.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Create New Account");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onEditAccountMenuClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "editAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Edit Account");
        stage.setScene(scene);
        stage.showAndWait();
    }


    @FXML
    private void onNewVoucherMenuClick(ActionEvent event) throws IOException {
        Stage s = (Stage) this.top_navbar.getScene().getWindow();
        
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "newVoucher.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setTitle("Uni Accounts - New Journal Voucher");
        stage.setScene(scene);
        stage.show();
        s.hide();
    }

    @FXML
    private void onDepositVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "depositVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Deposit Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onExpenseVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "expenseVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Expense Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onCreateAccountTypeClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "createAccountType.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle("Uni Accounts - Create Account Type");
    }

    @FXML
    private void onCreateProjectMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateProject.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Add New Project");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateLCMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateLC.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Add New LC");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateCNFMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateCNF.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Add New C&F");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onLedgerReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportLedger.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Show Ledger");
            stage.setScene(scene);
            stage.showAndWait();

        
    }

    @FXML
    private void onCreateVoucherType(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "VoucherType.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Create Voucher Type");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onBsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportBs.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Show Financial Statement");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onTrialBalanceReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportTrialBalance.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Trial Balance");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateBank(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "BankAccount.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Create Bank Account");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onDisplaySettingClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "DisplaySettings.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Display Settings");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreatePartyClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateParty.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Create Party");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onAddItemClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "AddNewItem.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Add New Item");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onInsertStockClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "InsertStock.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Insert into Stock");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onStockReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "StockReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onProductWiseInventoryReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "ProductWiseInventoryReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onAllProductsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "AllProductWiseReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onPurchaseReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "PurchaseReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Purchase Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onSellReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "SellReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Sell Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onLCReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "LCReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - LC Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onProjectReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ProjectReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Project Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCNFReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CNFReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - C&F Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onPartyDetailsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "PartyDetailsReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(true);
            stage.setTitle("Uni Accounts - Party Wise Details Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onViewProjectsMenuClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewProject.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View All Projects");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewAllLcMenuClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewLc.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View All Lc");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewAllCNFMenuClcik(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewCNF.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View All C&F");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onCreateBankClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "BankAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Create New Bank Account");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewAllBanksMenuClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewBankAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View All Bank Accounts");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewAllPartyClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewAllParty.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View All Parties");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onCreateUserClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateUser.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Create User");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewVouchers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onSalaryVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "SalaryVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Salary Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewSalaryVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewSalaryVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Salary Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewDebitVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewDebitVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Debit Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewExpenseVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewCreditVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Credit Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onPurchaseOrederVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "POVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - PO Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewPurchaseOrederVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewPOVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View PO Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onReceiptPaymentReportClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReceiptPayment.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Receipt Payment");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onSellVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "SellVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - Sell Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onViewSellOrederVoucherClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ViewSellVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - View Sell Voucher");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onCNFBillclick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CNFBill.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(true);
        stage.setTitle("Uni Accounts - CNFBill");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
}
