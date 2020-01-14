package cn.sxhengrun.purchase.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HeadlineVO implements Serializable {
    private String id;
    private String title;
    private String details;
    private Date publishAt;
    private String publishBy;
    private boolean active;

    private List<PhotoVO> photos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PhotoVO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVO> photos) {
        this.photos = photos;
    }
}
