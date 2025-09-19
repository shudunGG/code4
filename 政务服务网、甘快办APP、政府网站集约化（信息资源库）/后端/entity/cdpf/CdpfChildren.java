package com.hanweb.yyjr.zhangye.entity.cdpf;


import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 残疾儿童康复基本信息 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.CDPF_Children,comment = "残疾儿童康复基本信息")
public class CdpfChildren {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "GroupName", required = false)
    @NotNull
    @Column(name = "GroupName", type = ColumnType.VARCHAR, length = 255, comment = "机构名称")
    private String groupName;

    @ApiModelProperty(value = "Area", required = false)
    @NotNull
    @Column(name = "Area", type = ColumnType.VARCHAR, length = 255, comment = "所属区划")
    private String area;

    @ApiModelProperty(value = "Address", required = false)
    @NotNull
    @Column(name = "Address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
    private String address;

    @ApiModelProperty(value = "Functionary", required = false)
    @NotNull
    @Column(name = "Functionary", type = ColumnType.VARCHAR, length = 255, comment = "负责人员")
    private String functionary;

    @ApiModelProperty(value = "Telephone", required = false)
    @NotNull
    @Column(name = "Telephone", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String telephone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFunctionary() {
        return functionary;
    }

    public void setFunctionary(String functionary) {
        this.functionary = functionary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
