package com.jm.xpproject.database;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 网络数据
 *
 * @author jinXiong.Xie
 * @date 2019/2/13
 */
public class NetworkData extends LitePalSupport {

    @Column(unique = true)
    private String link;
    private String linkData;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkData() {
        return linkData;
    }

    public void setLinkData(String linkData) {
        this.linkData = linkData;
    }
}
