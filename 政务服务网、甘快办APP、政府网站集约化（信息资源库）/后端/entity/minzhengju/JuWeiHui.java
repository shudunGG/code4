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
 * 居委会 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_JuWeiHui,comment = "居委会")
public class JuWeiHui {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "street", required = false)
    @NotNull
    @Column(name = "street", type = ColumnType.VARCHAR, length = 255, comment = "所属街道 1：东街街道、2：南街街道、3：西街街道、4：北街街道、" +
            "5：火车站街道、6：梁家墩镇、7：上秦镇、8：大满镇、9：沙井镇、10：乌江镇、11：甘浚镇、12：新墩镇、13：党寨镇、" +
            "14：碱滩镇、15：明永镇、16：平山湖蒙古族乡、17：龙渠乡、18：安阳乡、19：花寨乡、20：靖安乡、21：三闸镇、" +
            "22：小满镇、23：长安镇、24：沙河镇、25：新华镇、26：蓼泉镇、27：平川镇、28：板桥镇、29：鸭暖镇、30：倪家营镇、" +
            "31：城关镇、32：宣化镇、33：南华镇、34：巷道镇、35：新坝镇、36：骆驼城镇、37：合黎镇、38：黑泉镇、39：罗城镇、" +
            "40：东乐镇、41：位奇镇、42：霍城镇、43：陈户镇、44：大马营镇、45：老军乡、46：李桥乡、47：清泉镇、48：洪水镇、" +
            "49：六坝镇、50：新天镇、51：南古镇、52：南丰镇、53：民联镇、54：顺化镇、55：丰乐镇、56：永固镇、57：三堡镇、" +
            "58：红湾寺镇、59：皇城镇、60：康乐镇、61：马蹄藏族乡、62：白银蒙古族乡、63：大河乡、64：明花乡、65：祁丰藏族乡")
    private String street;

    @ApiModelProperty(value = "groupname", required = false)
    @NotNull
    @Column(name = "groupname", type = ColumnType.VARCHAR, length = 255, comment = "组织名称")
    private String groupName;

    @ApiModelProperty(value = "grouptype", required = false)
    @NotNull
    @Column(name = "grouptype", type = ColumnType.VARCHAR, length = 255, comment = "机构类型")
    private String groupType;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细住所")
    private String address;

    @ApiModelProperty(value = "fdr", required = false)
    @NotNull
    @Column(name = "fdr", type = ColumnType.VARCHAR, length = 255, comment = "法定人")
    private String fdr;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFdr() {
        return fdr;
    }

    public void setFdr(String fdr) {
        this.fdr = fdr;
    }
}
