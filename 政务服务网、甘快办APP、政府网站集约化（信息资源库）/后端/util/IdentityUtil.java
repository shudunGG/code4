package com.hanweb.yyjr.zhangye.util;

import com.hanweb.common.util.StringUtil;

import com.hanweb.gsszf.member.IndividualsAndCorporation;
import com.hanweb.yyjr.member.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 *  身份验证工具类
 *
 */
@Component
public class IdentityUtil {

    @Autowired
    private LoginService loginService;

    /**
     *  获取用户信息
     *
     * @param token
     * @param authCode
     * @return
     */
    public IndividualsAndCorporation getUserInfo (String token, String authCode){
        if (StringUtil.isEmpty(token) && StringUtil.isEmpty(authCode)){
            throw new RuntimeException("通过token获取用户信息失败！");
        }
        IndividualsAndCorporation member = new IndividualsAndCorporation();
        member = loginService.transformUserInfoBean(authCode, member, token);
        if (ObjectUtils.isEmpty(member)){
            throw new RuntimeException("获取用户信息失败！");
        }
        return member;
    }
}
