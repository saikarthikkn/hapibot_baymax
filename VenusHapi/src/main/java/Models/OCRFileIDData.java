package Models;

/**
 * Created by sai_goturu on 2/22/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OCRFileIDData {
    @SerializedName("file_id")
    @Expose
    private String fileId;
    @SerializedName("pages")
    @Expose
    private Integer pages;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
