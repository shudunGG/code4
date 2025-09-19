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
 * 星级饭店 实体类
 * @author xueyize
 * @since 2022-03-07
 */
@Table(name= Tables.ZY_StarLevelRestaurant,comment = "星级饭店")
public class StarLevelRestaurant {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "饭店名称")
    private String name;

    @ApiModelProperty(value = "level", required = false)
    @NotNull
    @Column(name = "level", type = ColumnType.VARCHAR, length = 255, comment = "星级")
    private String level;

    @ApiModelProperty(value = "certificateno", required = false)
    @NotNull
    @Column(name = "certificateno", type = ColumnType.VARCHAR, length = 255, comment = "证书编号")
    private String certificateNo;

    @ApiModelProperty(value = "time", required = false)
    @NotNull
    @Column(name = "time", type = ColumnType.VARCHAR, length = 255, comment = "认定时间")
    private String time;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "总机电话")
    private String tele;

    @ApiModelProperty(value = "guestroomnum", required = false)
    @NotNull
    @Column(name = "guestroomnum", type = ColumnType.VARCHAR, length = 255, comment = "客房床位")
    private String guestRoomNum;

    @ApiModelProperty(value = "bednum", required = false)
    @NotNull
    @Column(name = "bednum", type = ColumnType.VARCHAR, length = 255, comment = "总床位")
    private String bedNum;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getGuestRoomNum() {
        return guestRoomNum;
    }

    public void setGuestRoomNum(String guestRoomNum) {
        this.guestRoomNum = guestRoomNum;
    }

    public String getBedNum() {
        return bedNum;
    }

    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
