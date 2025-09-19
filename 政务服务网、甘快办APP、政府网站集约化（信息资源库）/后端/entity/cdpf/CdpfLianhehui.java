package com.hanweb.yyjr.zhangye.entity.cdpf;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 残疾人联合会 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.CDPF_lianhehui,comment = "残疾人联合会基本信息")
public class CdpfLianhehui {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "GroupName", required = false)
    @NotNull
    @Column(name = "GroupName", type = ColumnType.VARCHAR, length = 255, comment = "机构名称")
    private String groupName;

    @ApiModelProperty(value = "Tel", required = false)
    @NotNull
    @Column(name = "Tel", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String telephone;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
