package com.rbu.erp_wms.diagnose;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/15 9:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Config {

    private String url;
    //文件访问权限
    private String fileAccess;
    //是否可以缩放
    private String zoom;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileAccess() {
        return fileAccess;
    }

    public void setFileAccess(String fileAccess) {
        this.fileAccess = fileAccess;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }
}
