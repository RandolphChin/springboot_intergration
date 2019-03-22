package com.shaphar.base;

/**
 * Created by v054 on 2019/3/4.
 */
public enum DataBaseSource {
    ERP_SIT("ERP_SIT","testErpSitDB"),
    ERP_MOBILE("ERP_MOBILE","testMobileErpDB");
    private String desc;
    private String dbName;

    DataBaseSource(String desc, String dbName) {
        this.desc = desc;
        this.dbName = dbName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
