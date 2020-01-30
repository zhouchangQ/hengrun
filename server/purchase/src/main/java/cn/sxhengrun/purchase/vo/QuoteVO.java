package cn.sxhengrun.purchase.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QuoteVO implements Serializable {
    private String id;
    private String tel;
    private String details;
    private String type;
    private String companyName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
