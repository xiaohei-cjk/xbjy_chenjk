package com.cjk.sys.entity;

import java.io.Serializable;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/11/29 12:04
 * @description 菜单实体类
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 主键ID
     */
    private Integer id;

    private Integer pId;
    private String type;
    private String name;
    private String menuUrl;
    private Integer orderBy;
    private String createTime;
    private Integer createBy;
    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pId=" + pId +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", orderBy=" + orderBy +
                ", createTime='" + createTime + '\'' +
                ", createBy=" + createBy +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
