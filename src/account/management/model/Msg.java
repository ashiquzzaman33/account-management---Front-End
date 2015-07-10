/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author mohar
 */
public class Msg {
    public static void showInformation(String msg){
        if(msg.equals("")){
            msg = "Successfully Saved!!!";
        }
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.setGraphic(new ImageView(new Image("resources/success.jpg")));
        alert.showAndWait();
    }
    
    public static void showError(String msg){
        if(msg.equals("")){
            msg = "Sorry there is an error. Please try again!!!";
        }
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
        alert.showAndWait();
    }
}
