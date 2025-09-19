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
 * 救助站 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_JiuZhuZhan,comment = "救助站")
public class JiuZhuZhan {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "groupname", required = false)
    @NotNull
    @Column(name = "groupname", type = ColumnType.VARCHAR, length = 255, comment = "福利院机构名")
    private String groupName;

    @ApiModelProperty(value = "range", required = false)
    @NotNull
    @Column(name = "yewufanwei", type = ColumnType.TEXT, comment = "业务范围")
    private String yeWuFanWei;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "电话")
    private String tele;

    @ApiModelProperty(value = "beizhu", required = false)
    @NotNull
    @Column(name = "beizhu", type = ColumnType.VARCHAR, length = 255, comment = "备注")
    private String beizhu;

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

    public String getYeWuFanWei() {
        return yeWuFanWei;
    }

    public void setYeWuFanWei(String yeWuFanWei) {
        this.yeWuFanWei = yeWuFanWei;
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

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
