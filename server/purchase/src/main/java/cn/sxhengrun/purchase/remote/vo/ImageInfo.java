package cn.sxhengrun.purchase.remote.vo;

import java.util.Date;

public class ImageInfo {
    private String id;
    private String filename;
    private Integer originWidth;
    private Integer originHeight;
    private Long originFileSize;
    private String originSavedId;
    private String originUrl;
    private Integer thumbWidth;
    private Integer thumbHeight;
    private Long thumbFileSize;
    private String thumbSavedId;
    private String thumbUrl;
    private Date uploadedAt;
    private String uploadedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getOriginWidth() {
        return originWidth;
    }

    public void setOriginWidth(Integer originWidth) {
        this.originWidth = originWidth;
    }

    public Integer getOriginHeight() {
        return originHeight;
    }

    public void setOriginHeight(Integer originHeight) {
        this.originHeight = originHeight;
    }

    public Long getOriginFileSize() {
        return originFileSize;
    }

    public void setOriginFileSize(Long originFileSize) {
        this.originFileSize = originFileSize;
    }

    public String getOriginSavedId() {
        return originSavedId;
    }

    public void setOriginSavedId(String originSavedId) {
        this.originSavedId = originSavedId;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public Long getThumbFileSize() {
        return thumbFileSize;
    }

    public void setThumbFileSize(Long thumbFileSize) {
        this.thumbFileSize = thumbFileSize;
    }

    public String getThumbSavedId() {
        return thumbSavedId;
    }

    public void setThumbSavedId(String thumbSavedId) {
        this.thumbSavedId = thumbSavedId;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
