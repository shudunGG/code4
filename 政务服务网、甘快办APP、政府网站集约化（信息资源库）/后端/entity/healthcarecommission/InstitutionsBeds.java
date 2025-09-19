package com.hanweb.yyjr.zhangye.entity.healthcarecommission;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 卫生委机构床位 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.HC_InstitutionsBeds,comment = "卫生委机构床位")
public class InstitutionsBeds {


    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "area", required = false)
    @NotNull
    @Column(name = "area", type = ColumnType.VARCHAR, length = 255, comment = "地区")
    private String area;

    @ApiModelProperty(value = "synhospnum", required = false)
    @NotNull
    @Column(name = "synhospnum", type = ColumnType.VARCHAR, length = 255, comment = "综合医院数目")
    private String synHospNum;

    @ApiModelProperty(value = "tcmhospitalnum", required = false)
    @NotNull
    @Column(name = "tcmhospitalnum", type = ColumnType.VARCHAR, length = 255, comment = "中医医院数目")
    private String tcmHospitalNum;

    @ApiModelProperty(value = "spechospital", required = false)
    @NotNull
    @Column(name = "spechospital", type = ColumnType.VARCHAR, length = 255, comment = "专科医院数目")
    private String specHospital;

    @ApiModelProperty(value = "weishengservicenum", required = false)
    @NotNull
    @Column(name = "weishengservicenum", type = ColumnType.VARCHAR, length = 255, comment = "卫生服务数目")
    private String weiShengServiceNum;

    @ApiModelProperty(value = "weishengyuannum", required = false)
    @NotNull
    @Column(name = "weishengyuannum", type = ColumnType.VARCHAR, length = 255, comment = "卫生院数目")
    private String weiShengYuanNum;

    @ApiModelProperty(value = "cunweishengshinum", required = false)
    @NotNull
    @Column(name = "cunweishengshinum", type = ColumnType.VARCHAR, length = 255, comment = "村卫生室数目")
    private String cunWeiShengShiNum;

    @ApiModelProperty(value = "yiwushinum", required = false)
    @NotNull
    @Column(name = "yiwushinum", type = ColumnType.VARCHAR, length = 255, comment = "医务室数目")
    private String yiWuShiNum;

    @ApiModelProperty(value = "jibingyufangnum", required = false)
    @NotNull
    @Column(name = "jibingyufangnum", type = ColumnType.VARCHAR, length = 255, comment = "疾病预防中心")
    private String jiBingYuFangNum;

    @ApiModelProperty(value = "fuyoubaojiannum", required = false)
    @NotNull
    @Column(name = "fuyoubaojiannum", type = ColumnType.VARCHAR, length = 255, comment = "妇幼保健数目")
    private String fuYouBaoJianNum;

    @ApiModelProperty(value = "weishengjiandunum", required = false)
    @NotNull
    @Column(name = "weishengjiandunum", type = ColumnType.VARCHAR, length = 255, comment = "卫生监督数目")
    private String weiShengJianDuNum;

    @ApiModelProperty(value = "jihuashengyunum", required = false)
    @NotNull
    @Column(name = "jihuashengyunum", type = ColumnType.VARCHAR, length = 255, comment = "计划生育数目")
    private String jiHuaShengYuNum;

    @ApiModelProperty(value = "xuezhannum", required = false)
    @NotNull
    @Column(name = "xuezhannum", type = ColumnType.VARCHAR, length = 255, comment = "血站数目")
    private String xuezhannum;

    @ApiModelProperty(value = "jiankangjiaoyusuonum", required = false)
    @NotNull
    @Column(name = "jiankangjiaoyusuonum", type = ColumnType.VARCHAR, length = 255, comment = "健康教育所数目")
    private String jianKangJiaoYuSuoNum;

    @ApiModelProperty(value = "qitaweishengjigounum", required = false)
    @NotNull
    @Column(name = "qitaweishengjigounum", type = ColumnType.VARCHAR, length = 255, comment = "其他卫生机构数目")
    private String qiTaWeiShengJiGouNum;

    @ApiModelProperty(value = "zhiyeyishinum", required = false)
    @NotNull
    @Column(name = "zhiyeyishinum", type = ColumnType.VARCHAR, length = 255, comment = "执业医师和执业助理医师数目")
    private String zhiYeYiShiNum;

    @ApiModelProperty(value = "zhucehushinum", required = false)
    @NotNull
    @Column(name = "zhucehushinum", type = ColumnType.VARCHAR, length = 255, comment = "注册护士数目")
    private String zhuCeHuShiNum;

    @ApiModelProperty(value = "yiyuanchuangweinum", required = false)
    @NotNull
    @Column(name = "yiyuanchuangweinum", type = ColumnType.VARCHAR, length = 255, comment = "医院床位数")
    private String yiYuanChuangWeiNum;

    @ApiModelProperty(value = "weishengyuanchuangweinum", required = false)
    @NotNull
    @Column(name = "weishengyuanchuangweinum", type = ColumnType.VARCHAR, length = 255, comment = "卫生院床位数")
    private String weiShengYuanChuangWeiNum;

    @ApiModelProperty(value = "fuyoubaojianyuannum", required = false)
    @NotNull
    @Column(name = "fuyoubaojianyuannum", type = ColumnType.VARCHAR, length = 255, comment = "妇幼保健院床位数")
    private String fuYouBaoJianYuanNum;

    @ApiModelProperty(value = "treatmentnum", required = false)
    @NotNull
    @Column(name = "treatmentnum", type = ColumnType.VARCHAR, length = 255, comment = "治疗人数")
    private String treatmentNum;

    @ApiModelProperty(value = "chuyuannum", required = false)
    @NotNull
    @Column(name = "chuyuannum", type = ColumnType.VARCHAR, length = 255, comment = "出院人数")
    private String chuYuanNum;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSynHospNum() {
        return synHospNum;
    }

    public void setSynHospNum(String synHospNum) {
        this.synHospNum = synHospNum;
    }

    public String getTcmHospitalNum() {
        return tcmHospitalNum;
    }

    public void setTcmHospitalNum(String tcmHospitalNum) {
        this.tcmHospitalNum = tcmHospitalNum;
    }

    public String getSpecHospital() {
        return specHospital;
    }

    public void setSpecHospital(String specHospital) {
        this.specHospital = specHospital;
    }

    public String getWeiShengServiceNum() {
        return weiShengServiceNum;
    }

    public void setWeiShengServiceNum(String weiShengServiceNum) {
        this.weiShengServiceNum = weiShengServiceNum;
    }

    public String getWeiShengYuanNum() {
        return weiShengYuanNum;
    }

    public void setWeiShengYuanNum(String weiShengYuanNum) {
        this.weiShengYuanNum = weiShengYuanNum;
    }

    public String getCunWeiShengShiNum() {
        return cunWeiShengShiNum;
    }

    public void setCunWeiShengShiNum(String cunWeiShengShiNum) {
        this.cunWeiShengShiNum = cunWeiShengShiNum;
    }

    public String getYiWuShiNum() {
        return yiWuShiNum;
    }

    public void setYiWuShiNum(String yiWuShiNum) {
        this.yiWuShiNum = yiWuShiNum;
    }

    public String getJiBingYuFangNum() {
        return jiBingYuFangNum;
    }

    public void setJiBingYuFangNum(String jiBingYuFangNum) {
        this.jiBingYuFangNum = jiBingYuFangNum;
    }

    public String getFuYouBaoJianNum() {
        return fuYouBaoJianNum;
    }

    public void setFuYouBaoJianNum(String fuYouBaoJianNum) {
        this.fuYouBaoJianNum = fuYouBaoJianNum;
    }

    public String getWeiShengJianDuNum() {
        return weiShengJianDuNum;
    }

    public void setWeiShengJianDuNum(String weiShengJianDuNum) {
        this.weiShengJianDuNum = weiShengJianDuNum;
    }

    public String getJiHuaShengYuNum() {
        return jiHuaShengYuNum;
    }

    public void setJiHuaShengYuNum(String jiHuaShengYuNum) {
        this.jiHuaShengYuNum = jiHuaShengYuNum;
    }

    public String getXuezhannum() {
        return xuezhannum;
    }

    public void setXuezhannum(String xuezhannum) {
        this.xuezhannum = xuezhannum;
    }

    public String getJianKangJiaoYuSuoNum() {
        return jianKangJiaoYuSuoNum;
    }

    public void setJianKangJiaoYuSuoNum(String jianKangJiaoYuSuoNum) {
        this.jianKangJiaoYuSuoNum = jianKangJiaoYuSuoNum;
    }

    public String getQiTaWeiShengJiGouNum() {
        return qiTaWeiShengJiGouNum;
    }

    public void setQiTaWeiShengJiGouNum(String qiTaWeiShengJiGouNum) {
        this.qiTaWeiShengJiGouNum = qiTaWeiShengJiGouNum;
    }

    public String getZhiYeYiShiNum() {
        return zhiYeYiShiNum;
    }

    public void setZhiYeYiShiNum(String zhiYeYiShiNum) {
        this.zhiYeYiShiNum = zhiYeYiShiNum;
    }

    public String getZhuCeHuShiNum() {
        return zhuCeHuShiNum;
    }

    public void setZhuCeHuShiNum(String zhuCeHuShiNum) {
        this.zhuCeHuShiNum = zhuCeHuShiNum;
    }

    public String getYiYuanChuangWeiNum() {
        return yiYuanChuangWeiNum;
    }

    public void setYiYuanChuangWeiNum(String yiYuanChuangWeiNum) {
        this.yiYuanChuangWeiNum = yiYuanChuangWeiNum;
    }

    public String getWeiShengYuanChuangWeiNum() {
        return weiShengYuanChuangWeiNum;
    }

    public void setWeiShengYuanChuangWeiNum(String weiShengYuanChuangWeiNum) {
        this.weiShengYuanChuangWeiNum = weiShengYuanChuangWeiNum;
    }

    public String getFuYouBaoJianYuanNum() {
        return fuYouBaoJianYuanNum;
    }

    public void setFuYouBaoJianYuanNum(String fuYouBaoJianYuanNum) {
        this.fuYouBaoJianYuanNum = fuYouBaoJianYuanNum;
    }

    public String getTreatmentNum() {
        return treatmentNum;
    }

    public void setTreatmentNum(String treatmentNum) {
        this.treatmentNum = treatmentNum;
    }

    public String getChuYuanNum() {
        return chuYuanNum;
    }

    public void setChuYuanNum(String chuYuanNum) {
        this.chuYuanNum = chuYuanNum;
    }
}
