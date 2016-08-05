/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.view.layouts;

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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amimul Ahsan
 */
public class Layout_sidenavController implements Initializable {
    @FXML
    private Accordion sidenav_container;
    @FXML
    private TitledPane settings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(!User.database_maintanance){
            this.settings.setDisable(true);
        }
    }    

    @FXML
    private void onCreateUserButtonClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateUser.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setTitle("Create User");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onDeleteUserButtonClick(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "DeleteUser.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setTitle("Delete User");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onNewVoucherClick(ActionEvent event) throws IOException {
        Stage s = (Stage) this.settings.getScene().getWindow();
        
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
    private void onExpenceVoucherClick(ActionEvent event) throws IOException {
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
    private void onSalarySlipClick(ActionEvent event) throws IOException {
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
    private void onPOClick(ActionEvent event) throws IOException {
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
    private void onSellOrderClick(ActionEvent event) throws IOException {
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
    private void onLedgerClick(ActionEvent event) throws IOException {
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
    private void onFinancialStatementClick(ActionEvent event) throws IOException {
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
    private void onTrialBalanceClick(ActionEvent event) throws IOException {
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
    private void onPartyWiseReportClick(ActionEvent event) throws IOException {
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

    
}
