package cn.sxhengrun.purchase.entity;

import org.eulerframework.web.core.base.entity.IDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "headline_album")
public class HeadlineAlbum extends IDEntity<HeadlineAlbum> {
    @Column(name = "headline_id")
    private Long headlineId;
    @Column(name = "image_id")
    private String imageId;
    @Column(name = "order_index")
    private Integer orderIndex;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "modify_at")
    private Date modifyAt;
    @Column(name = "deleted")
    private Boolean deleted;

    public String getImageId() {
        return imageId;
    }

    public Long getHeadlineId() {
        return headlineId;
    }

    public void setHeadlineId(Long headlineId) {
        this.headlineId = headlineId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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
