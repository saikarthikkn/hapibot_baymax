package Models;


import java.util.Date;

/**
 * Created by sanjesh_nair on 2/24/2017.
 */

public class ScannedReport {

    String reportID;
    String date;
    String name;
    String path;
    String type;

    public ScannedReport(String date, String name, String path, String type){
        this.date = date;
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public ScannedReport(){}

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
