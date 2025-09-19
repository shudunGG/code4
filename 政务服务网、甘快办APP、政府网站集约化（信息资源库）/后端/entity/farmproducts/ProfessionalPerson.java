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
 * 农业职称专技人员 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.ZY_ProfessionalPerson,comment = "农业职称专技人员")
public class ProfessionalPerson {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "姓名")
    private String name;

    @ApiModelProperty(value = "sex", required = false)
    @NotNull
    @Column(name = "sex", type = ColumnType.VARCHAR, length = 255, comment = "性别")
    private String sex;

    @ApiModelProperty(value = "workunit", required = false)
    @NotNull
    @Column(name = "workunit", type = ColumnType.VARCHAR, length = 255, comment = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "job", required = false)
    @NotNull
    @Column(name = "job", type = ColumnType.VARCHAR, length = 255, comment = "职称")
    private String job;

    @ApiModelProperty(value = "skill", required = false)
    @NotNull
    @Column(name = "skill", type = ColumnType.VARCHAR, length = 255, comment = "擅长技能")
    private String skill;


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
