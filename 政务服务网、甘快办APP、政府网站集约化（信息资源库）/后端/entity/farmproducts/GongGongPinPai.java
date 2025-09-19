package com.hanweb.yyjr.zhangye.entity.farmproducts;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 农产品公共品牌 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.ZY_GongGongPinPai,comment = "农产品公共品牌")
public class GongGongPinPai {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "品牌名称")
    private String name;

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "类型")
    private String type;

    @ApiModelProperty(value = "shenbaozhuti", required = false)
    @NotNull
    @Column(name = "shenbaozhuti", type = ColumnType.VARCHAR, length = 255, comment = "申报主体")
    private String shenBaoZhuTi;

    @ApiModelProperty(value = "time", required = false)
    @NotNull
    @Column(name = "time", type = ColumnType.VARCHAR, length = 255, comment = "认定时间")
    private String time;

    @ApiModelProperty(value = "beizhu", required = false)
    @NotNull
    @Column(name = "beizhu", type = ColumnType.VARCHAR, length = 255, comment = "备注")
    private String beiZhu;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShenBaoZhuTi() {
        return shenBaoZhuTi;
    }

    public void setShenBaoZhuTi(String shenBaoZhuTi) {
        this.shenBaoZhuTi = shenBaoZhuTi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }
}
