package cn.sxhengrun.purchase.entity;

import org.eulerframework.web.core.base.entity.NonIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "uc", name = "hengrun_user_profile")
public class HengRunUserProfileEntity extends NonIDEntity<HengRunUserProfileEntity, String> {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "berth")
    private String berth;
    @Column(name = "scope")
    private String scope;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "avatar")
    private String avatar;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBerth() {
        return berth;
    }

    public void setBerth(String berth) {
        this.berth = berth;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return getUserId();
    }

    @Override
    public void setId(String id) {
        this.setUserId(id);
    }

    @Override
    public int compareTo(HengRunUserProfileEntity o) {
        return this.getId().compareTo(o.getId());
    }
}
