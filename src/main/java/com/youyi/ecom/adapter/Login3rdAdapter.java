package com.youyi.ecom.adapter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.youyi.ecom.config.GiteeConfigProperties;
import com.youyi.ecom.pojo.po.User;
import com.youyi.ecom.service.UserService;
import com.youyi.ecom.util.HttpUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 第三方登陆适配器
 * <p>
 * UserService 为 Adapter 模式中的 Adaptee 角色
 */
@Slf4j
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget {

    @Autowired
    private GiteeConfigProperties giteeConfigProperties;

    @Override
    public String loginByGitee(String code, String state) {
        if (!giteeConfigProperties.getState().equals(state)) {
            throw new RuntimeException("state不匹配");
        }

        String tokenUrl = String.format(giteeConfigProperties.getTokenUrl(), code);
        String tokenJson = HttpUtil.post(tokenUrl, null);
        Map<String, String> tokenMap = JSONUtil.toBean(tokenJson, new TypeReference<>() {
        }, false);
        String accessToken = tokenMap.get("access_token");

        String infoUrl = String.format(giteeConfigProperties.getInfoUrl(), accessToken);
        String infoJson = HttpUtil.get(infoUrl);
        Map<String, Object> infoMap = JSONUtil.toBean(infoJson, new TypeReference<>() {
        }, false);

        String username = giteeConfigProperties.getUserPrefix() + infoMap.get("login");
        return autoRegister3rdAndLogin(username, username);
    }

    private String autoRegister3rdAndLogin(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPwd(password);
        register(user);
        return login(username, password);
    }
}