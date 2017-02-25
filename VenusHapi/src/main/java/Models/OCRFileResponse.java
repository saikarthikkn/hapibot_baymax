package Models;

/**
 * Created by sai_goturu on 2/22/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OCRFileResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private OCRFileIDData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OCRFileIDData getData() {
        return data;
    }

    public void setData(OCRFileIDData data) {
        this.data = data;
    }
}
