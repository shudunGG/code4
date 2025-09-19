package com.hanweb.yyjr.zhangye.entity.spad;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 交委收费站 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.SPAD_shoufeizhan,comment = "交委收费站")
public class SpadShouFeiZhan {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "ssgs", required = false)
    @NotNull
    @Column(name = "ssgs", type = ColumnType.VARCHAR, length = 255, comment = "所属高速")
    private String ssgs;

    @ApiModelProperty(value = "sfzm", required = false)
    @NotNull
    @Column(name = "sfzm", type = ColumnType.VARCHAR, length = 255, comment = "收费站名")
    private String sfzm;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSsgs() {
        return ssgs;
    }

    public void setSsgs(String ssgs) {
        this.ssgs = ssgs;
    }

    public String getSfzm() {
        return sfzm;
    }

    public void setSfzm(String sfzm) {
        this.sfzm = sfzm;
    }
}
