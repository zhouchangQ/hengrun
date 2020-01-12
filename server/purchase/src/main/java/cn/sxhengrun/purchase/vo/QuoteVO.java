package cn.sxhengrun.purchase.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QuoteVO implements Serializable {
    private String id;
    private String tel;
    private String details;
    private Date quoteAt;
    private String quoteBy;

    private List<PhotoVO> photos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getQuoteAt() {
        return quoteAt;
    }

    public void setQuoteAt(Date quoteAt) {
        this.quoteAt = quoteAt;
    }

    public String getQuoteBy() {
        return quoteBy;
    }

    public void setQuoteBy(String quoteBy) {
        this.quoteBy = quoteBy;
    }

    public List<PhotoVO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVO> photos) {
        this.photos = photos;
    }
}
