package com.hanweb.yyjr.zhangye.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.util.StringUtil;
import com.hanweb.gsszf.constant.ProjectConstants;
import com.hanweb.yyjr.zhangye.service.ZhangYeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 总控 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/GeneralAll")
public class GeneralnterfaceController {

    @Autowired
    com.hanweb.yyjr.zhangye.controller.cdpfcontroller.CdpfChildrenController cdpfChildrenController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.cdpfcontroller.CdpfLianhehuiController cdpfLianhehuiController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.cdpfcontroller.CdpfWentihuodongController cdpfWentihuodongController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.ndrcontroller.NdrcQuanzeListController ndrcQuanzeListController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.ndrcontroller.NdrcXiangmushenheController ndrcXiangmushenheController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.ndrcontroller.NdrcXingzhengxukeController ndrcXingzhengxukeController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.spadcontroller.SpadBridgeInfoController spadBridgeInfoController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.spadcontroller.SpadDaoLuHuoCheController spadDaoluhuocheController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.spadcontroller.SpadShouFeiZhanController spadShoufeizhanController;

    @Autowired
    com.hanweb.yyjr.zhangye.controller.weathercontroller.WbFangLeiJianCeController wbFangleijianceController;

    @Autowired
    ZhangYeListService zhangYeListService;

    @RequestMapping("switch")
    @ResponseBody
    public String switchAllInterface(HttpServletResponse response, String type, String param, String fdr, String fdrsfz){
        JSONObject result1 = new JSONObject();
        String result ="";
        if (StringUtil.isEmpty(type)){
            result1.put("error","type参数不能为空");
              return  result1.toJSONString();
        }
        try{
            if (StringUtil.equals(type, ProjectConstants.STR_1)){
                //启用儿童残联接口
                result = cdpfChildrenController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_2)){
                //启用残联会接口
                result = cdpfLianhehuiController.findAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_3)){
                //启用残联文体接口
                result = cdpfWentihuodongController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_4)){
                //启用发改委权责清单详情接口
                result = ndrcQuanzeListController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_5)){
                //启用获取发改委权责子项名称接口
                result = ndrcQuanzeListController.findEventChildNameList(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_6)){
                //启用发改委项目审核接口
                result = ndrcXiangmushenheController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_7)){
                //启用发改委行政许可接口
                result = ndrcXingzhengxukeController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_8)){
                //启用获取发改委行政许可详情接口
                result = ndrcXingzhengxukeController.findxzxkjdwsmcList(fdr,fdrsfz);
            }else if (StringUtil.equals(type, ProjectConstants.STR_9)){
                //启用获取桥梁名称接口
                result = spadBridgeInfoController.findBridgeNameList(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_10)){
                //启用获取桥梁详细信息接口
                result = spadBridgeInfoController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_11)){
                //启用交委道路货车接口
                result = spadDaoluhuocheController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_12)){
                //启用交委收费站信息接口
                result = spadShoufeizhanController.findAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_13)){
                //启用气象防雷信息接口
                result = wbFangleijianceController.findAllData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_14)){
                //启用人社局工资报酬接口
                result = zhangYeListService.renSheJufindAllSalaryData(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_15)){
                //启用获取发改委民生价格信息列表接口
                result = zhangYeListService.findMinshengjiageList(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_16)){
                //启用获取发改委民生价格详细信息接口
                result = zhangYeListService.findMinshengjiageListInfo(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_17)){
                //启用获取基层法律服务所详细信息接口
                result = zhangYeListService.findBasicLawListInfo();
            }else if (StringUtil.equals(type, ProjectConstants.STR_18)){
                //启用获取律师事务所信息接口
                result = zhangYeListService.findLawOfficeListInfo();
            }else if (StringUtil.equals(type, ProjectConstants.STR_19)){
                //启用获取红十字会信息接口
                result = zhangYeListService.findRedCrossListInfo();
            }else if (StringUtil.equals(type, ProjectConstants.STR_20)){
                //启用获取医疗机构信息接口
                result = zhangYeListService.findYiliaojigouInfo(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_21)){
                //启用获取医疗机构类型列表接口
                result = zhangYeListService.findYiliaojigouList();
            }else if (StringUtil.equals(type, ProjectConstants.STR_22)){
                //启用获取疫情防控联系电话接口
                result = zhangYeListService.findyiqingfangkongteleList();
            }else if (StringUtil.equals(type, ProjectConstants.STR_23)){
                //启用获取助产机构信息接口
                result = zhangYeListService.findzhuchanxinxiList();
            }else if (StringUtil.equals(type, ProjectConstants.STR_24)){
                //启用获取保健机构信息接口
                result = zhangYeListService.findbaojianxinxiList();
            }else if (StringUtil.equals(type, ProjectConstants.STR_25)){
                //启用获取接种疫苗地点列表信息接口
                result = zhangYeListService.findJieZhongList(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_26)){
                //启用获取接种疫苗地点详细信息接口
                result = zhangYeListService.findJieZhongInfo(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_27)){
                //启用获取卫生中心名称列表接口
                result = zhangYeListService.findWeiShengNameList(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_28)){
                //启用获取卫生中心详细信息接口
                result = zhangYeListService.findWeiShengCenterInfo(param);
            }else if (StringUtil.equals(type, ProjectConstants.STR_29)){
                //启用获取司法鉴定所信息
                result = zhangYeListService.findSiFaAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_30)){
                //启用获取法律援助信息
                result = zhangYeListService.findFaLvYuanZhuAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_31)){
                //启用获取公证机构信息
                result = zhangYeListService.findNotaryOrgAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_32)){
                //启用获取农产品公共品牌信息
                result = zhangYeListService.findGongGongPinPaiAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_33)){
                //启用获取农产品商标品牌信息
                result = zhangYeListService.findShangBiaoPinPaiAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_34)){
                //启用获取农业龙头企业信息
                result = zhangYeListService.findLongHeaderAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_35)){
                //启用获取农业职称专技人员
                result = zhangYeListService.findProfessionalPersonAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_36)){
                //启用获取旅行社信息
                result = zhangYeListService.findLvXingSheAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_37)){
                //启用获取导游信息
                result = zhangYeListService.findTourGuideAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_38)){
                //启用获取景区信息
                result = zhangYeListService.findJingQuAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_39)){
                //启用获取星级饭店信息
                result = zhangYeListService.findStarLevelRestaurantAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_40)){
                //启用获取星级农家乐信息
                result = zhangYeListService.findStarLevelNongJiaLeAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_41)){
                //启用获取文化馆信息
                result = zhangYeListService.findWenHuaGuanAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_42)){
                //启用获取图书馆信息
                result = zhangYeListService.findLibraryAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_43)){
                //启用获取博物馆信息
                result = zhangYeListService.findMuseumAllData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_44)){
                //启用获取殡葬服务信息
                result = zhangYeListService.findBinZangFuWuData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_45)){
                //启用获取婚姻登记服务信息
                result = zhangYeListService.findHunYinDengJiData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_46)){
                //启用获取社会组织登记服务信息
                result = zhangYeListService.findSheHuiZuZhiDengJiData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_47)){
                //启用获取社会组织信息
                result = zhangYeListService.findSheHuiZuZhiXinXiData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_48)){
                //启用获取社区养老机构信息
                result = zhangYeListService.findSheQuYangLaoJiGouData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_49)){
                //启用获取儿童福利院信息
                result = zhangYeListService.findChildrenFuLiYuanData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_50)){
                //启用获取救助站信息
                result = zhangYeListService.findJiuZhuZhanData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_51)){
                //启用获取社会福利院信息
                result = zhangYeListService.findSocialFuLiYuanData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_52)){
                //启用获取养老机构信息
                result = zhangYeListService.findYangLaoJiGouData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_53)){
                //启用获取居委会信息
                result = zhangYeListService.findJuWeiHuiData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_54)){
                //启用获取福彩站点信息
                result = zhangYeListService.findFuCaiZhanDianData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_55)){
                //启用获取民办社会信息
                result = zhangYeListService.findMinBanSheHuiData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_56)){
                //启用获取公立医院
                result = zhangYeListService.findGongLiYiYuanData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_57)){
                //启用获取民营医院
                result = zhangYeListService.findMinYingYiYuanData();
            }else if (StringUtil.equals(type, ProjectConstants.STR_58)){
                //启用获取核酸检测
                result = zhangYeListService.findHeSuanJianCeData();
            }

            result1.put("success",true);
            result1.put("msg","成功");
            result1.put("data",result);

        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result1.put("success",false);
            result1.put("msg",e.getMessage());
        }
        return   result1.toJSONString();
    }
}
