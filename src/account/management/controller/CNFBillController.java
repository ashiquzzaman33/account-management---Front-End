/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.EnglishNumberToWords;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import report.Report;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CNFBillController implements Initializable {
    @FXML
    private TextField t1;
    @FXML
    private DatePicker t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField t4;
    @FXML
    private TextField t5;
    @FXML
    private TextField t6;
    @FXML
    private DatePicker t7;
    @FXML
    private TextField t8;
    @FXML
    private TextField t9;
    @FXML
    private DatePicker t10;
    @FXML
    private TextField t11;
    @FXML
    private TextField t12;
    @FXML
    private TextField t13;
    @FXML
    private TextField t14;
    @FXML
    private TextField t15;
    @FXML
    private TextField t16;
    @FXML
    private TextField t17;
    @FXML
    private TextField t18;
    @FXML
    private TextField t19;
    @FXML
    private TextField t20;
    @FXML
    private TextField t21;
    @FXML
    private TextField t22;
    @FXML
    private TextField t23;
    @FXML
    private TextField t24;
    @FXML
    private TextField t25;
    @FXML
    private TextField t26;
    @FXML
    private TextField t27;
    @FXML
    private TextField t28;
    @FXML
    private TextField t29;
    @FXML
    private TextField t30;
    @FXML
    private TextField t31;
    @FXML
    private TextField t32;
    @FXML
    private TextField t33;
    @FXML
    private TextField t34;
    @FXML
    private TextField t35;
    @FXML
    private TextField t36;
    @FXML
    private TextField t37;
    @FXML
    private TextField t38;
    @FXML
    private TextField t39;
    @FXML
    private TextField t40;
    @FXML
    private TextField t41;
    @FXML
    private TextField t42;
    @FXML
    private TextField t43;
    @FXML
    private TextField t44;
    @FXML
    private TextField t55;
    @FXML
    private TextField t56;
    @FXML
    private TextField t57;
    @FXML
    private TextField t58;
    @FXML
    private TextField t45;
    @FXML
    private TextField t46;
    @FXML
    private TextField t47;
    @FXML
    private TextField t48;
    @FXML
    private TextField t49;
    @FXML
    private TextField t50;
    @FXML
    private TextField t51;
    @FXML
    private TextField t52;
    @FXML
    private TextField t53;
    @FXML
    private TextField t54;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCreateBillButtonClick(ActionEvent event) throws ParseException {
        Vector v = new Vector();
        v.add("aaa");
        HashMap params = new HashMap();
        Report r = new Report();
        
        params.put("t1", t1.getText() == null ? "" : t1.getText());
        params.put("t2", t2.getValue() == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(t2.getValue().toString())));
        params.put("t3", t3.getText() == null ? "" : t3.getText());
        params.put("t4", t4.getText() == null ? "" : t4.getText());
        params.put("t5", t5.getText() == null ? "" : t5.getText());
        params.put("t6", t6.getText() == null ? "" : t6.getText());
        params.put("t7", t7.getValue() == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(t7.getValue().toString())));
        params.put("t8", t8.getText() == null ? "" : t8.getText());
        params.put("t9", t9.getText() == null ? "" : t9.getText());
        params.put("t10", t10.getValue() == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(t10.getValue().toString())));
        params.put("t11", t11.getText() == null ? "" : t11.getText());
        params.put("t12", t12.getText() == null ? "" : t12.getText());
        params.put("t13", t13.getText() == null ? "" : t13.getText());
        params.put("t14", t14.getText() == null ? "" : t14.getText());
        params.put("t15", t15.getText() == null ? "" : t15.getText());
        params.put("t16", t16.getText() == null ? "" : t16.getText());
        params.put("t17", t17.getText() == null ? "" : t17.getText());
        params.put("t18", t18.getText() == null ? "" : t18.getText());
        params.put("t19", t19.getText() == null ? "" : t19.getText());
        params.put("t20", t20.getText() == null ? "" : t20.getText());
        params.put("t21", t21.getText() == null ? "" : t21.getText());
        params.put("t22", t22.getText() == null ? "" : t22.getText());
        params.put("t23", t23.getText() == null ? "" : t23.getText());
        params.put("t24", t24.getText() == null ? "" : t24.getText());
        params.put("t25", t25.getText() == null ? "" : t25.getText());
        params.put("t26", t26.getText() == null ? "" : t26.getText());
        params.put("t27", t27.getText() == null ? "" : t27.getText());
        params.put("t28", t28.getText() == null ? "" : t28.getText());
        params.put("t29", t29.getText() == null ? "" : t29.getText());
        params.put("t30", t30.getText() == null ? "" : t30.getText());
        params.put("t31", t31.getText() == null ? "" : t31.getText());
        params.put("t32", t32.getText() == null ? "" : t32.getText());
        params.put("t33", t33.getText() == null ? "" : t33.getText());
        params.put("t34", t34.getText() == null ? "" : t34.getText());
        params.put("t35", t35.getText() == null ? "" : t35.getText());
        params.put("t36", t36.getText() == null ? "" : t36.getText());
        params.put("t37", t37.getText() == null ? "" : t37.getText());
        params.put("t38", t38.getText() == null ? "" : t38.getText());
        params.put("t39", t39.getText() == null ? "" : t39.getText());
        params.put("t40", t40.getText() == null ? "" : t40.getText());
        params.put("t41", t41.getText() == null ? "" : t41.getText());
        params.put("t42", t42.getText() == null ? "" : t42.getText());
        params.put("t43", t43.getText() == null ? "" : t43.getText());
        params.put("t44", t44.getText() == null ? "" : t44.getText());
        params.put("t45", t45.getText() == null ? "" : t45.getText());
        params.put("t46", t46.getText() == null ? "" : t46.getText());
        params.put("t47", t47.getText() == null ? "" : t47.getText());
        params.put("t48", t48.getText() == null ? "" : t48.getText());
        params.put("t49", t49.getText() == null ? "" : t49.getText());
        params.put("t50", t50.getText() == null ? "" : t50.getText());
        params.put("t51", t51.getText() == null ? "" : t51.getText());
        params.put("t52", t52.getText() == null ? "" : t52.getText());
        params.put("t53", t53.getText() == null ? "" : t53.getText());
        params.put("t54", t54.getText() == null ? "" : t54.getText());
        params.put("t55", t55.getText() == null ? "" : t55.getText());
        params.put("t56", t56.getText() == null ? "" : t56.getText());
        params.put("t57", t57.getText() == null ? "" : t57.getText());
        params.put("t58", t58.getText() == null ? "" : t58.getText());
        
        try{
            String word = EnglishNumberToWords.convert(Long.parseLong(t58.getText()));
            params.put("t59", word + " taka only.");
        }catch(Exception e){
            params.put("t59", "");
        }
        
        
        BufferedImage img = null;
        try {
                img = ImageIO.read(new File("src/report/Utopia.png"));
                params.put("logo", img);
        } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        r.getReport("src\\report\\CNFBill.jrxml", new JRBeanCollectionDataSource(v), params, "BILL");
    }
    
}
