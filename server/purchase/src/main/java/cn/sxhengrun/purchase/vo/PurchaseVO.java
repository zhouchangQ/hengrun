package cn.sxhengrun.purchase.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PurchaseVO implements Serializable {
    private String id;
    private String title;
    private String tel;
    private String type;
    private String details;
    private Date publishAt;
    private String publishBy;
    private boolean completed;
    private long quoteCount;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(long quoteCount) {
        this.quoteCount = quoteCount;
    }

    public List<PhotoVO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVO> photos) {
        this.photos = photos;
    }
}
