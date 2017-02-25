package Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sai_goturu on 2/23/2017.
 */

public class OCRTextData {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("progress")
    @Expose
    private Integer progress;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
