package Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sai_goturu on 2/23/2017.
 */

public class OCRRecognizedTextResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private OCRTextData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OCRTextData getData() {
        return data;
    }

    public void setData(OCRTextData data) {
        this.data = data;
    }
}
