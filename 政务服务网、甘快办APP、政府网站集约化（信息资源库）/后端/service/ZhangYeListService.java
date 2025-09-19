package com.hanweb.yyjr.zhangye.service;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.yyjr.zhangye.dao.farmproductsdao.GongGongPinPaiDao;
import com.hanweb.yyjr.zhangye.dao.farmproductsdao.LongHeaderDao;
import com.hanweb.yyjr.zhangye.dao.farmproductsdao.ProfessionalPersonDao;
import com.hanweb.yyjr.zhangye.dao.farmproductsdao.ShangBiaoPinPaiDao;
import com.hanweb.yyjr.zhangye.dao.hc.*;
import com.hanweb.yyjr.zhangye.dao.law.*;
import com.hanweb.yyjr.zhangye.dao.minzhengjudao.*;
import com.hanweb.yyjr.zhangye.dao.ndrc.NdrcMinshengjiageDao;
import com.hanweb.yyjr.zhangye.dao.traveldao.*;
import com.hanweb.yyjr.zhangye.dao.rensheju.RenSheJuGongZiBaoBiaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统合 Service
 * @author xueyize
 * @since 2022-02-22
 */
@Service
public class ZhangYeListService {

    /**
     * 人社局工资报酬信息
     */
    @Autowired
    RenSheJuGongZiBaoBiaoDao renSheJuGongZiBaoBiaoDao;

    /**
     * 发改委民生价格
     */
    @Autowired
    NdrcMinshengjiageDao ndrcMinshengjiageDao;

    /**
     * 基层法律服务所
     */
    @Autowired
    BasicLawDao basicLawDao;

    /**
     * 律师事务所
     */
    @Autowired
    LawOfficeInfoDao lawOfficeInfoDao;

    /**
     * 红十字会信息
     */
    @Autowired
    RedCrossInfoDao redCrossInfoDao;

    /**
     * 医疗机构信息
     */
    @Autowired
    YiliaojigouListDao yiliaojigouListDao;

    /**
     * 疫情防控联系电话
     */
    @Autowired
    YiQingTeleDao yiQingTeleDao;

    /**
     * 助产机构信息
     */
    @Autowired
    ZhuChanJiGouDao zhuChanJiGouDao;

    /**
     * 保健机构信息
     */
    @Autowired
    BaoJianJiGouDao baoJianJiGouDao;

    /**
     * 疫苗接种信息
     */
    @Autowired
    YiMiaoJieZhongDao yiMiaoJieZhongDao;

    /**
     * 卫生中心信息
     */
    @Autowired
    WenShengCenterDao wenShengCenterDao;

    /**
     * 机构床位信息
     */
    @Autowired
    InstitutionsBedsDao institutionsBedsDao;

    /**
     * 获取司法鉴定所信息
     */
    @Autowired
    JudicialExpertiseDao judicialExpertiseDao;

    /**
     * 获取法律援助
     */
    @Autowired
    LegalAidDao legalAidDao;

    /**
     * 获取公证机构信息
     */
    @Autowired
    ZyNotaryOrgDao notaryOrgDao;

    /**
     * 获取农产品公共品牌信息
     */
    @Autowired
    GongGongPinPaiDao gongGongPinPaiDao;

    /**
     * 获取农产品商标品牌信息
     */
    @Autowired
    ShangBiaoPinPaiDao shangBiaoPinPaiDao;

    /**
     * 获取农业龙头产业信息
     */
    @Autowired
    LongHeaderDao longHeaderDao;

    /**
     * 获取农业职称专技人员信息
     */
    @Autowired
    ProfessionalPersonDao professionalPersonDao;

    /**
     * 获取文广旅行社信息
     */
    @Autowired
    LvXingSheDao lvXingSheDao;

    /**
     * 获取文广导游信息
     */
    @Autowired
    TourGuideDao tourGuideDao;

    /**
     * 获取文广景区信息
     */
    @Autowired
    JingQuDao jingQuDao;

    /**
     * 获取文广星级饭店信息
     */
    @Autowired
    StarLevelRestaurantDao starLevelRestaurantDao;

    /**
     * 获取文广星级农家乐信息
     */
    @Autowired
    StarLevelNongJiaLeDao starLevelNongJiaLeDao;

    /**
     * 获取文广文化馆信息
     */
    @Autowired
    WenHuaGuanDao wenHuaGuanDao;

    /**
     * 获取文广图书馆信息
     */
    @Autowired
    LibraryDao libraryDao;

    /**
     * 获取文广博物馆信息
     */
    @Autowired
    MuseumDao museumDao;

    /**
     * 获取殡葬服务机构信息
     */
    @Autowired
    com.hanweb.yyjr.zhangye.dao.minzhengjudao.BinZangFuWuDao binZangFuWuDao;

    /**
     * 获取婚姻登记服务机构信息
     */
    @Autowired
    com.hanweb.yyjr.zhangye.dao.minzhengjudao.HunYinDengJiDao hunYinDengJiDao;

    /**
     * 获取社会组织登记机构信息
     */
    @Autowired
    com.hanweb.yyjr.zhangye.dao.minzhengjudao.SheHuiZuZhiDengJiDao sheHuiZuZhiDengJiDao;

    /**
     * 获取社会组织信息
     */
    @Autowired
    com.hanweb.yyjr.zhangye.dao.minzhengjudao.SheHuiZuZhiXinXiDao sheHuiZuZhiXinXiDao;

    /**
     * 获取社区养老机构信息
     */
    @Autowired
    com.hanweb.yyjr.zhangye.dao.minzhengjudao.SheQuYangLaoJiGouDao sheQuYangLaoJiGouDao;

    /**
     * 获取儿童福利院信息
     */
    @Autowired
    ChildrenFuLiYuanDao childrenFuLiYuanDao;

    /**
     * 获取救助站信息
     */
    @Autowired
    JiuZhuZhanDao jiuZhuZhanDao;

    /**
     * 获取社会福利院信息
     */
    @Autowired
    SocialFuLiYuanDao socialFuLiYuanDao;

    /**
     * 获取养老机构
     */
    @Autowired
    YangLaoJiGouDao yangLaoJiGouDao;

    /**
     * 获取居委会信息
     */
    @Autowired
    JuWeiHuiDao juWeiHuiDao;

    /**
     * 获取福彩站点信息
     */
    @Autowired
    FuCaiZhanDianDao fuCaiZhanDianDao;

    /**
     * 获取民办社会信息
     */
    @Autowired
    MinBanSheHuiDao minBanSheHuiDao;

    /**
     * 获取公立医院
     */
    @Autowired
    GongLiYiYuanDao gongLiYiYuanDao;

    /**
     * 获取民营医院
     */
    @Autowired
    MinYingYiYuanDao minYingYiYuanDao;

    /**
     * 获取核酸检测
     */
    @Autowired
    HeSuanJianCeDao heSuanJianCeDao;


    /**
     * 获取人社局工资报酬信息
     * @param positionName
     * @return
     */
    public String renSheJufindAllSalaryData(String positionName) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = renSheJuGongZiBaoBiaoDao.findAllData(positionName);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取发改委民生价格信息列表
     * @param goodsContent
     * @return
     */
    public String findMinshengjiageList(String goodsContent) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = ndrcMinshengjiageDao.findGoodsContentList(goodsContent);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取发改委民生价格详细信息
     * @param goodsContent
     * @return
     */
    public String findMinshengjiageListInfo(String goodsContent) {

        JsonModelResult result = JsonModelResult.getInstance();
        Map<String, Object> param =new HashMap<>(StaticValues.NUM_16);
        try{
            param = ndrcMinshengjiageDao.findAllData(goodsContent);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取基层法律服务所详细信息
     * @return
     */
    public String findBasicLawListInfo() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = basicLawDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取律师事务所信息
     * @return
     */
    public String findLawOfficeListInfo() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = lawOfficeInfoDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取红十字会信息
     * @return
     */
    public String findRedCrossListInfo() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = redCrossInfoDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取医疗机构信息
     * @param type
     * @return
     */
    public String findYiliaojigouInfo(String type) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = yiliaojigouListDao.findAllData(type);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取医疗机构类型
     * @return
     */
    public String findYiliaojigouList() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = yiliaojigouListDao.findAllTypeList();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取疫情防控联系电话
     * @return
     */
    public String findyiqingfangkongteleList() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = yiQingTeleDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取助产机构信息
     * @return
     */
    public String findzhuchanxinxiList() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = zhuChanJiGouDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }


    /**
     * 获取保健机构信息
     * @return
     */
    public String findbaojianxinxiList() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = baoJianJiGouDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取疫苗接种地点列表信息
     * @param jieZhongDianName
     * @return
     */
    public String findJieZhongList(String jieZhongDianName) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = yiMiaoJieZhongDao.findJieZhongList(jieZhongDianName);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);

    }

    /**
     * 获取疫苗接种地点详细信息
     * @param jieZhongDianName
     * @return
     */
    public String findJieZhongInfo(String jieZhongDianName) {

        JsonModelResult result = JsonModelResult.getInstance();
        Map<String, Object> param = new HashMap<>(StaticValues.NUM_16);
        try{
            param = yiMiaoJieZhongDao.findAllData(jieZhongDianName);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取卫生中心名称信息列表
     * @param name
     * @return
     */
    public String findWeiShengNameList(String name) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = wenShengCenterDao.findWeiShengNameList(name);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取卫生中心名称详细信息
     * @param name
     * @return
     */
    public String findWeiShengCenterInfo(String name) {

        JsonModelResult result = JsonModelResult.getInstance();
        Map<String, Object> param = new HashMap<>(StaticValues.NUM_16);
        try{

            param = wenShengCenterDao.findAllData(name);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取地区卫生机构床位信息
     * @param area
     * @return
     */
    public String findDataByArea(String area) {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = institutionsBedsDao.findDataByArea(area);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取司法鉴定所信息
     * @return
     */
    public String findSiFaAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = judicialExpertiseDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }


    /**
     * 获取法律援助信息
     * @return
     */
    public String findFaLvYuanZhuAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = legalAidDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取公证机构信息
     * @return
     */
    public String findNotaryOrgAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = notaryOrgDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取农产品公共品牌信息
     * @return
     */
    public String findGongGongPinPaiAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = gongGongPinPaiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取农产品商标品牌信息
     * @return
     */
    public String findShangBiaoPinPaiAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = shangBiaoPinPaiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取农业龙头企业信息
     * @return
     */
    public String findLongHeaderAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = longHeaderDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取农业职称专技人员信息
     * @return
     */
    public String findProfessionalPersonAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = professionalPersonDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广旅行社信息
     * @return
     */
    public String findLvXingSheAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = lvXingSheDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广导游信息
     * @return
     */
    public String findTourGuideAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = tourGuideDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广景区信息
     * @return
     */
    public String findJingQuAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = jingQuDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广星级饭店信息
     * @return
     */
    public String findStarLevelRestaurantAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = starLevelRestaurantDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广星级农家乐信息
     * @return
     */
    public String findStarLevelNongJiaLeAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = starLevelNongJiaLeDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广文化馆信息
     * @return
     */
    public String findWenHuaGuanAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = wenHuaGuanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广图书馆信息
     * @return
     */
    public String findLibraryAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = libraryDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取文广博物馆信息
     * @return
     */
    public String findMuseumAllData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = museumDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取殡葬服务信息
     * @return
     */
    public String findBinZangFuWuData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = binZangFuWuDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取婚姻登记服务信息
     * @return
     */
    public String findHunYinDengJiData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = hunYinDengJiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取社会组织登记服务信息
     * @return
     */
    public String findSheHuiZuZhiDengJiData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = sheHuiZuZhiDengJiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取社会组织信息
     * @return
     */
    public String findSheHuiZuZhiXinXiData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = sheHuiZuZhiXinXiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取社区养老机构信息
     * @return
     */
    public String findSheQuYangLaoJiGouData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = sheQuYangLaoJiGouDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取儿童福利院信息
     * @return
     */
    public String findChildrenFuLiYuanData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = childrenFuLiYuanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取救助站信息
     * @return
     */
    public String findJiuZhuZhanData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = jiuZhuZhanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }


    /**
     * 获取社会福利院信息
     * @return
     */
    public String findSocialFuLiYuanData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = socialFuLiYuanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取养老机构
     * @return
     */
    public String findYangLaoJiGouData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = yangLaoJiGouDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }


    /**
     * 获取居委会信息
     * @return
     */
    public String findJuWeiHuiData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = juWeiHuiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取福彩站点信息
     * @return
     */
    public String findFuCaiZhanDianData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = fuCaiZhanDianDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取民办社会
     * @return
     */
    public String findMinBanSheHuiData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = minBanSheHuiDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取公立医院
     * @return
     */
    public String findGongLiYiYuanData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = gongLiYiYuanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取民营医院
     * @return
     */
    public String findMinYingYiYuanData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = minYingYiYuanDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

    /**
     * 获取核酸检测
     * @return
     */
    public String findHeSuanJianCeData() {

        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = heSuanJianCeDao.findAllData();
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }

}
