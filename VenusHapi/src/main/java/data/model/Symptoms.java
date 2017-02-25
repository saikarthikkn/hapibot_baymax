package data.model;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Symptoms {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("extras")
    @Expose
    private Extras extras;
    @SerializedName("children")
    @Expose
    private List<Object> children = null;
    @SerializedName("sex_filter")
    @Expose
    private String sexFilter;
    @SerializedName("image_url")
    @Expose
    private Object imageUrl;
    @SerializedName("image_source")
    @Expose
    private Object imageSource;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("parent_relation")
    @Expose
    private Object parentRelation;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public String getSexFilter() {
        return sexFilter;
    }

    public void setSexFilter(String sexFilter) {
        this.sexFilter = sexFilter;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getImageSource() {
        return imageSource;
    }

    public void setImageSource(Object imageSource) {
        this.imageSource = imageSource;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getParentRelation() {
        return parentRelation;
    }

    public void setParentRelation(Object parentRelation) {
        this.parentRelation = parentRelation;
    }

}

