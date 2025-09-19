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
 * 发改委民生价格 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.NDRC_minshengjiage,comment = "发改委民生价格")
public class NdrcMinshengjiage {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "GoodsProject", required = false)
    @NotNull
    @Column(name = "GoodsProject", type = ColumnType.VARCHAR, length = 255, comment = "商品项目")
    private String goodsProject;

    @ApiModelProperty(value = "GoodsContent", required = false)
    @NotNull
    @Column(name = "GoodsContent", type = ColumnType.VARCHAR, length = 255, comment = "商品内容")
    private String goodsContent;

    @ApiModelProperty(value = "GoodsPrice", required = false)
    @NotNull
    @Column(name = "GoodsPrice", type = ColumnType.TEXT , comment = "商品价格")
    private String goodsPrice;

    @ApiModelProperty(value = "Department", required = false)
    @NotNull
    @Column(name = "Department", type = ColumnType.VARCHAR, length = 255, comment = "定价部门")
    private String department;

    @ApiModelProperty(value = "Document", required = false)
    @NotNull
    @Column(name = "Document", type = ColumnType.TEXT , comment = "定价文件")
    private String document;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getGoodsProject() {
        return goodsProject;
    }

    public void setGoodsProject(String goodsProject) {
        this.goodsProject = goodsProject;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
