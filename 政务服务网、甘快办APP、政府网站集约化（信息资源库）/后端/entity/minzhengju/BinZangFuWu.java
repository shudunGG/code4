package com.hanweb.yyjr.zhangye.entity.minzhengju;


import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 殡葬服务 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_BinZangFuWu,comment = "殡葬服务")
public class BinZangFuWu {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "groupname", required = false)
    @NotNull
    @Column(name = "groupname", type = ColumnType.VARCHAR, length = 255, comment = "殡葬机构名")
    private String groupName;

    @ApiModelProperty(value = "area", required = false)
    @NotNull
    @Column(name = "area", type = ColumnType.VARCHAR, length = 255, comment = "所属区县")
    private String area;

    @ApiModelProperty(value = "grouptype", required = false)
    @NotNull
    @Column(name = "grouptype", type = ColumnType.VARCHAR, length = 255, comment = "机构类别")
    private String grouptype;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
}
