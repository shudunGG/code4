package com.hanweb.yyjr.zhangye.entity.travel;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 导游信息 实体类
 * @author xueyize
 * @since 2022-03-07
 */
@Table(name= Tables.ZY_TourGuide,comment = "导游信息")
public class TourGuide {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "导游姓名")
    private String name;

    @ApiModelProperty(value = "cardno", required = false)
    @NotNull
    @Column(name = "cardno", type = ColumnType.VARCHAR, length = 255, comment = "导游证号")
    private String cardNo;

    @ApiModelProperty(value = "zigeno", required = false)
    @NotNull
    @Column(name = "zigeno", type = ColumnType.VARCHAR, length = 255, comment = "资格证号")
    private String zigeNo;

    @ApiModelProperty(value = "language", required = false)
    @NotNull
    @Column(name = "language", type = ColumnType.VARCHAR, length = 255, comment = "语种")
    private String language;

    @ApiModelProperty(value = "level", required = false)
    @NotNull
    @Column(name = "level", type = ColumnType.VARCHAR, length = 255, comment = "等级")
    private String level;

    @ApiModelProperty(value = "organization", required = false)
    @NotNull
    @Column(name = "organization", type = ColumnType.VARCHAR, length = 255, comment = "所属机构")
    private String organization;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getZigeNo() {
        return zigeNo;
    }

    public void setZigeNo(String zigeNo) {
        this.zigeNo = zigeNo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
