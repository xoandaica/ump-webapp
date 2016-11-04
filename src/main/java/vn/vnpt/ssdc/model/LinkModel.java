package vn.vnpt.ssdc.model;

/**
 * Created by SSDC on 11/2/2016.
 */
public class LinkModel {
    private String url;
    private String id;
    private boolean isActive = false;

    public LinkModel(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        if (isActive) {
            this.url = "#";
        }
    }
}
