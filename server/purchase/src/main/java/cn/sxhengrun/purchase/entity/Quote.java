package cn.sxhengrun.purchase.entity;

import org.eulerframework.web.core.base.entity.IDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "quote")
public class Quote extends IDEntity<Quote> {
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Column(name = "tel")
    private String tel;
    @Column(name = "details")
    private String details;
    @Column(name = "quote_at")
    private Date quoteAt;
    @Column(name = "quote_by")
    private String quoteBy;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "modify_at")
    private Date modifyAt;
    @Column(name = "deleted")
    private Boolean deleted;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
