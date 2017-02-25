package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

public class Extras {
    @SerializedName("hint")
    @Expose
    private String hint;
    @SerializedName("icd10_code")
    @Expose
    private String icd10Code;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }
}
