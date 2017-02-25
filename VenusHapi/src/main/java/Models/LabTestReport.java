package Models;

// Static Data for the Lab Tests
public class LabTestReport {
    String testID;
    String testName;
    String gender;
    String testNameAlias;
    String reportType;
    String units;
    String lowerVal;
    String higherVal;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTestNameAlias() {
        return testNameAlias;
    }

    public void setTestNameAlias(String testNameAlias) {
        this.testNameAlias = testNameAlias;
    }

    public String getHigherVal() {
        return higherVal;
    }

    public void setHigherVal(String higherVal) {
        this.higherVal = higherVal;
    }

    public String getLowerVal() {
        return lowerVal;
    }

    public void setLowerVal(String lowerVal) {
        this.lowerVal = lowerVal;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
