package com.hanweb.yyjr.zhangye.entity.ndrc;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 发改委权责清单 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.NDRC_quanzeList,comment = "发改委权责清单")
public class NdrcQuanzeList {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "EventName", required = false)
    @NotNull
    @Column(name = "EventName", type = ColumnType.VARCHAR, length = 255, comment = "事项名称")
    private String eventName;

    @ApiModelProperty(value = "EventChildName", required = false)
    @NotNull
    @Column(name = "EventChildName", type = ColumnType.VARCHAR, length = 255, comment = "子事项名称")
    private String eventChildName;

    @ApiModelProperty(value = "RightType", required = false)
    @NotNull
    @Column(name = "RightType", type = ColumnType.VARCHAR, length = 255, comment = "权利类型")
    private String rightType;

    @ApiModelProperty(value = "RightCode", required = false)
    @NotNull
    @Column(name = "RightCode", type = ColumnType.VARCHAR, length = 255, comment = "权利编码")
    private String rightCode;

    @ApiModelProperty(value = "ChildRightCode", required = false)
    @NotNull
    @Column(name = "ChildRightCode", type = ColumnType.VARCHAR, length = 255, comment = "子权利编码")
    private String childRightCode;

    @ApiModelProperty(value = "executor", required = false)
    @NotNull
    @Column(name = "executor", type = ColumnType.VARCHAR, length = 255, comment = "执行人")
    private String executor;

    @ApiModelProperty(value = "GroupName", required = false)
    @NotNull
    @Column(name = "GroupName", type = ColumnType.VARCHAR, length = 255, comment = "承办机构")
    private String groupName;

    @ApiModelProperty(value = "implement", required = false)
    @NotNull
    @Column(name = "implement", type = ColumnType.TEXT,  comment = "实施依据")
    private String implement;

    @ApiModelProperty(value = "respevent", required = false)
    @NotNull
    @Column(name = "respevent", type = ColumnType.VARCHAR, length = 255, comment = "责任事项")
    private String respevent;

    @ApiModelProperty(value = "zzqx", required = false)
    @NotNull
    @Column(name = "zzqx", type = ColumnType.VARCHAR, length = 255, comment = "追责情形")
    private String zzqx;

    @ApiModelProperty(value = "bscj", required = false)
    @NotNull
    @Column(name = "bscj", type = ColumnType.VARCHAR, length = 255, comment = "办事层级")
    private String bscj;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    public String getChildRightCode() {
        return childRightCode;
    }

    public void setChildRightCode(String childRightCode) {
        this.childRightCode = childRightCode;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }

    public String getRespevent() {
        return respevent;
    }

    public void setRespevent(String respevent) {
        this.respevent = respevent;
    }

    public String getZzqx() {
        return zzqx;
    }

    public void setZzqx(String zzqx) {
        this.zzqx = zzqx;
    }

    public String getBscj() {
        return bscj;
    }

    public void setBscj(String bscj) {
        this.bscj = bscj;
    }

    public String getEventChildName() {
        return eventChildName;
    }

    public void setEventChildName(String eventChildName) {
        this.eventChildName = eventChildName;
    }
}

