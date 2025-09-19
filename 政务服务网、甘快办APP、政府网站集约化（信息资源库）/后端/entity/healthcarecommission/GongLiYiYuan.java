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
 * 公立医院 实体类
 * @author xueyize
 * @since 2022-03-16
 */
@Table(name= Tables.ZY_GongLiYiYuan,comment = "公立医院")
public class GongLiYiYuan {

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

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "企业类型 1：政府办公立医院，2：社会事业办")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
