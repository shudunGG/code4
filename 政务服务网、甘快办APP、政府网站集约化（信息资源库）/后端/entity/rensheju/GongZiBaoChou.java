package com.hanweb.yyjr.zhangye.entity.rensheju;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 人社局工资报酬 实体类
 * @author xueyize
 * @since 2022-02-22
 */
@Table(name= Tables.GongZiBaoChou,comment = "人社局工资报酬")
public class GongZiBaoChou {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "PositionName", required = false)
    @NotNull
    @Column(name = "PositionName", type = ColumnType.VARCHAR, length = 255, comment = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "AveSalary", required = false)
    @NotNull
    @Column(name = "AveSalary", type = ColumnType.VARCHAR, length = 255, comment = "平均工资")
    private String aveSalary;

    @ApiModelProperty(value = "HighSalary", required = false)
    @NotNull
    @Column(name = "HighSalary", type = ColumnType.VARCHAR, length = 255, comment = "工资高位数")
    private String highSalary;

    @ApiModelProperty(value = "HighFourSalary", required = false)
    @NotNull
    @Column(name = "HighFourSalary", type = ColumnType.VARCHAR, length = 255, comment = "工资上四位数")
    private String highFourSalary;

    @ApiModelProperty(value = "MiddleSalary", required = false)
    @NotNull
    @Column(name = "MiddleSalary", type = ColumnType.VARCHAR, length = 255, comment = "工资中位数")
    private String middleSalary;

    @ApiModelProperty(value = "LowFourSalary", required = false)
    @NotNull
    @Column(name = "LowFourSalary", type = ColumnType.VARCHAR, length = 255, comment = "工资下四位数")
    private String lowFourSalary;

    @ApiModelProperty(value = "LowSalary", required = false)
    @NotNull
    @Column(name = "LowSalary", type = ColumnType.VARCHAR, length = 255, comment = "工资低位数")
    private String lowSalary;

    @ApiModelProperty(value = "StatisticsYear", required = false)
    @NotNull
    @Column(name = "StatisticsYear", type = ColumnType.VARCHAR, length = 255, comment = "统计年分")
    private String statisticsYear;

    @ApiModelProperty(value = "TimeStamp", required = false)
    @NotNull
    @Column(name = "TimeStamp", type = ColumnType.VARCHAR, length = 255, comment = "时间戳")
    private String timeStamp;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getAveSalary() {
        return aveSalary;
    }

    public void setAveSalary(String aveSalary) {
        this.aveSalary = aveSalary;
    }

    public String getHighSalary() {
        return highSalary;
    }

    public void setHighSalary(String highSalary) {
        this.highSalary = highSalary;
    }

    public String getHighFourSalary() {
        return highFourSalary;
    }

    public void setHighFourSalary(String highFourSalary) {
        this.highFourSalary = highFourSalary;
    }

    public String getMiddleSalary() {
        return middleSalary;
    }

    public void setMiddleSalary(String middleSalary) {
        this.middleSalary = middleSalary;
    }

    public String getLowFourSalary() {
        return lowFourSalary;
    }

    public void setLowFourSalary(String lowFourSalary) {
        this.lowFourSalary = lowFourSalary;
    }

    public String getLowSalary() {
        return lowSalary;
    }

    public void setLowSalary(String lowSalary) {
        this.lowSalary = lowSalary;
    }

    public String getStatisticsYear() {
        return statisticsYear;
    }

    public void setStatisticsYear(String statisticsYear) {
        this.statisticsYear = statisticsYear;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
