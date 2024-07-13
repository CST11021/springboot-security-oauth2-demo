package com.whz.spring.security.oauth2.demo.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 令牌增强器,为JWT 添加更多内容
 */
@Component
public class TokenEnhancerConfig implements TokenEnhancer {

    /**
     * 获取令牌的请求，服务端除了返回令牌，还可以返回多个信息
     *
     * 例如请求：
     * 密码模式：curl -XPOST 'http://localhost:8000/oauth/token?grant_type=password&username=admin&password=admin&client_id=oauth2&scope=all&redirect_uri=http://example.com' \
     *     -H 'Authorization: Basic b2F1dGgyOm9hdXRoMg=='
     *
     * 出参：
     * {
     *   "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyIiwicmVzb3VyY2VPbmUiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwib3JnYW5pemF0aW9uIjoiYWRtaW4iLCJ3aHpUZXN0UGFyYW0iOiJ3aHrmtYvor5UiLCJleHAiOjE3MjA4NjEzNTYsImF1dGhvcml0aWVzIjpbIm1lbWJlcjphZGQiXSwianRpIjoiZTRhNzI5Y2QtYmRkNy00MzVhLTkyMTUtZmM1NDRmYmYzNzZkIiwiY2xpZW50X2lkIjoib2F1dGgyIn0.HTCnnsVbIJu7lB2iWoqEzxquFGR1ya3pTgh-XXqACyGYAKu8lXLYq79DVtjFJUV-gcJiBZ_r-gVCrtcBliAqCmqUzSnCALkyBKfx5SXS1F-sMUQAeOEQIXd1JApZd4o-JONhZ01erRYHZb1a9hwoLdfB3Wp1ZMOmOFzU_M6elf5u35M7nQ7lxdTquhqg-xVBPNX6vmntP_vzwOFhXmguOVPKzjU_vB0geL1vR5nmb2TXoghGg_9CuWdg9DIwjBAQZnmq1D-DxS2VumhAlCGJez1ALGtCelG1Iyb-AsJZp5Vhy7o2R-DKTUeTG6gqcx3IRDdQo_RjTyGTR7w45VOmLQ",
     *   "token_type": "bearer",
     *   "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyIiwicmVzb3VyY2VPbmUiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwib3JnYW5pemF0aW9uIjoiYWRtaW4iLCJhdGkiOiJlNGE3MjljZC1iZGQ3LTQzNWEtOTIxNS1mYzU0NGZiZjM3NmQiLCJ3aHpUZXN0UGFyYW0iOiJ3aHrmtYvor5UiLCJleHAiOjE3MjM0NDYxNTYsImF1dGhvcml0aWVzIjpbIm1lbWJlcjphZGQiXSwianRpIjoiY2RmMjlkMDUtNjFhMC00Zjk3LWIyMjAtYTlkYTcyOWUyNWQ2IiwiY2xpZW50X2lkIjoib2F1dGgyIn0.rKchUR2oeYI7EgafcSo0PJAC7j_28KONmySyDmRsccx0KGHPSyjiyVfmouZ1eWFyk1XjkFxFNMlSewonS7nZNt-WGtvR7IUHHctO7kmzmptHzGgj0TWFv7lQFBGKiD6nBLxYRxYijwCIhvgENrLJpu5j6m_CS_0gy4PRZvHvC199SbthExV-qmUf6ZZsToDlhvl8__AgNw9iZRQog3V19Q2ooFFFxdTuEYEOmw8PLyeHPI2JN9bC8Dty0T10WhRMY0q7OhZEgsIH5M0byoEfUvXbjpboFQzhZEKXQzMdf8Bz_Kjh9NFAWm_v03-TXKtNCj-ozW8uFaaus3DHyOr1xg",
     *   "expires_in": 7199,
     *   "scope": "all",
     *   "whzTestParam": "whz测试",
     *   "organization": "admin",
     *   "jti": "e4a729cd-bdd7-435a-9215-fc544fbf376d"
     * }
     *
     * @param accessToken the current access token with its expiration and refresh token
     * @param authentication the current authentication including client and user details
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 创建一个自定义信息
        Map<String, Object> extInfo = new HashMap<>(1);
        // 设置值，添加额外的内容到JWT
        extInfo.put("organization", authentication.getName());
        extInfo.put("whzTestParam", "whz测试");
        // 存进去
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extInfo);
        // 返回
        return accessToken;
    }

}
