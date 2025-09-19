package com.hanweb.yyjr.zhangye.entity.healthcarecommission;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 民营医院 实体类
 * @author xueyize
 * @since 2022-03-16
 */
@Table(name= Tables.ZY_MinYingYiYuan,comment = "民营医院")
public class MinYingYiYuan {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "医院名称")
    private String name;

    @ApiModelProperty(value = "districts", required = false)
    @NotNull
    @Column(name = "districts", type = ColumnType.VARCHAR, length = 255, comment = "区划")
    private String districts;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }
}
