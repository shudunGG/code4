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
 * 社区养老机构信息 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_SheQuYangLaoJiGou,comment = "社区养老机构")
public class SheQuYangLaoJiGou {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "groupname", required = false)
    @NotNull
    @Column(name = "groupname", type = ColumnType.VARCHAR, length = 255, comment = "养老机构名")
    private String groupName;

    @ApiModelProperty(value = "groupno", required = false)
    @NotNull
    @Column(name = "groupno", type = ColumnType.VARCHAR, length = 255, comment = "机构门牌号")
    private String groupNo;

    @ApiModelProperty(value = "farenjiguan", required = false)
    @NotNull
    @Column(name = "farenjiguan", type = ColumnType.VARCHAR, length = 255, comment = "法人机关")
    private String faRenJiGuan;

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "类别")
    private String type;

    @ApiModelProperty(value = "nature", required = false)
    @NotNull
    @Column(name = "nature", type = ColumnType.VARCHAR, length = 255, comment = "单位性质")
    private String nature;

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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getFaRenJiGuan() {
        return faRenJiGuan;
    }

    public void setFaRenJiGuan(String faRenJiGuan) {
        this.faRenJiGuan = faRenJiGuan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
