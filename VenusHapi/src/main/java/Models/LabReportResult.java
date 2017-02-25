package Models;


/**
 * Created by sanjesh_nair on 2/24/2017.
 */

public class LabReportResult {
    //(ReportID, TestID, Value)

    String reportID;
    String testID;
    String value;

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
