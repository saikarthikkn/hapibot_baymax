package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

public class Conditions {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("prevalence")
    @Expose
    private String prevalence;
    @SerializedName("acuteness")
    @Expose
    private String acuteness;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("extras")
    @Expose
    private Extras extras;
    @SerializedName("sex_filter")
    @Expose
    private String sexFilter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getPrevalence() {
        return prevalence;
    }

    public void setPrevalence(String prevalence) {
        this.prevalence = prevalence;
    }

    public String getAcuteness() {
        return acuteness;
    }

    public void setAcuteness(String acuteness) {
        this.acuteness = acuteness;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public String getSexFilter() {
        return sexFilter;
    }

    public void setSexFilter(String sexFilter) {
        this.sexFilter = sexFilter;
    }
}
