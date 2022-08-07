/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Report {
    
    private String reportAmount;
    private String reportInfo;
     
    /**
     * Report Constructor.
     * @param reportAmount
     * @param reportInfo 
     */
    public Report(String reportAmount, String reportInfo) {
        
        this.reportAmount = reportAmount;
        this.reportInfo = reportInfo;
        
    }

    /**
     * 
     * ReportAmount getter.
     * @return 
     */
    public String getReportAmount() {
        return reportAmount;
    }

    /**
     * ReportAmount setter.
     * @param reportAmount 
     */
    public void setReportAmount(String reportAmount) {
        this.reportAmount = reportAmount;
    }

    /**
     * ReportInfo getter.
     * @return 
     */
    public String getReportInfo() {
        return reportInfo;
    }

    /**
     * ReportInfo setter.
     * @param reportInfo 
     */
    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }
    
}
