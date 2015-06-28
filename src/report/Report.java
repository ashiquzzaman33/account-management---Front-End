/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author mohar
 */
public class Report {
    
    private JasperReport report = null;
    private JasperPrint print = null;
    private JasperDesign design = null;
    
    
    //JRDataSource dataSource = new JRBeanCollectionDataSource(Person.getAllPerson());
    public void getReport(String reportPath, JRDataSource dataSource, String outputFileName, HashMap params){
        try {

            //design = JRXmlLoader.load("src\\report\\report.jrxml");
            design = JRXmlLoader.load(reportPath);
            report = JasperCompileManager.compileReport(design);
            print = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToPdfFile(print, outputFileName);
            
            JasperViewer jv = new JasperViewer(print, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getReport(String reportPath, JRDataSource dataSource, HashMap params){
        try {
   
            //design = JRXmlLoader.load("src\\report\\report.jrxml");
            design = JRXmlLoader.load(reportPath);
            report = JasperCompileManager.compileReport(design);
            print = JasperFillManager.fillReport(report, params, dataSource);
            //JasperExportManager.exportReportToPdfFile(print, outputFileName);
            JasperViewer jv = new JasperViewer(print, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printReport(){
        try {
            JasperPrintManager.printReport(print, true);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void printReport(boolean ShowPrintDialog){
        try {
            JasperPrintManager.printReport(print, ShowPrintDialog);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    
}
