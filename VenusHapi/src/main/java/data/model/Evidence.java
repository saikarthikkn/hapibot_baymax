package data.model;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evidence {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("choice_id")
    @Expose
    private String choiceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

}