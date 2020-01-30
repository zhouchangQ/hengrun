package cn.sxhengrun.purchase.remote.vo;

import java.io.Serializable;

public class HengRunUserProfileVO implements Serializable {
    private String scope;
    private String companyName;

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
}
