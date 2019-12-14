//package com.wangxinenpu.springbootdemo.config.shiro;
//
//
//import com.wangxinenpu.springbootdemo.dataobject.SysUser;
//import com.wangxinenpu.springbootdemo.service.SysUserService;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cache.CacheManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.web.mgt.CookieRememberMeManager;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//public class ShiroConfig {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);
//
//    @Autowired
//    SysUserService sysUserService;
//
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/logout", "logout");
//        filterChainDefinitionMap.put("/favicon.ico", "anon");
//        filterChainDefinitionMap.put("/druid/**", "anon");              // druid登录交给druid自己
//        filterChainDefinitionMap.put("/**", "authc");
//        //authc表示需要验证身份才能访问，还有一些比如anon表示不需要验证身份就能访问等。
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    @Bean
//    public SecurityManager securityManager(AuthorizingRealm myShiroRealm, CacheManager shiroRedisCacheManager) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setCacheManager(shiroRedisCacheManager);
//        securityManager.setRememberMeManager(cookieRememberMeManager());
//        securityManager.setRealm(myShiroRealm);
//        return securityManager;
//    }
//
//    @Bean
//     CacheManager getCacheManager() {
//        return new CustomerCacheManager();
//    }
//
//    @Bean
//    public AuthorizingRealm myShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
//        AuthorizingRealm  myShiroRealm = new AuthorizingRealm() {
//
//            @Override
//            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//                MyUserNamePassWordToken namePassWordToken = (MyUserNamePassWordToken) token;
//                SysUser sysUser = new SysUser();
//                sysUser.setUsername(namePassWordToken.getUsername());
//                sysUser = sysUserService.findUser(sysUser);
//                if (sysUser == null) {
//                    throw new UnknownAccountException();
//                }
//                return new SimpleAuthenticationInfo(namePassWordToken.getUsername(), "cdas", "dsdv");
//            }
//
//            @Override
//            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//                LOGGER.info("权限配置 --> MyShiroRealm.doGetAuthorizationInfo()");
//                SysUser user = new SysUser();
//                user = sysUserService.findUser(user);
//                SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
//                simpleAuthorInfo.addRole(user.getUserid().toString());
//                return simpleAuthorInfo;
//            }
//        };
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher); //设置加密规则
//        myShiroRealm.setCachingEnabled(true);
//        myShiroRealm.setAuthorizationCachingEnabled(true);
//        myShiroRealm.setAuthenticationCachingEnabled(true);
//        return myShiroRealm;
//    }
//
//    // 需要与存储密码时的加密规则一致
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//        return hashedCredentialsMatcher;
//    }
//
//    /**
//     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理<
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//        proxyCreator.setProxyTargetClass(true);
//        return proxyCreator;
//    }
//
//    /**
//     *  开启shiro aop注解支持.
//     *  使用代理方式;所以需要开启代码支持;
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    @Bean
//    public SimpleMappingExceptionResolver resolver() {
//        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//        Properties properties = new Properties();
//        properties.setProperty("UnauthorizedException", "/403");
//        exceptionResolver.setExceptionMappings(properties);
//        return exceptionResolver;
//    }
//
//    //cookie对象;
//    @Bean
//    public SimpleCookie rememberMeCookie() {
//        LOGGER.info("ShiroConfiguration.rememberMeCookie()");
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//
//        //<!-- 记住我cookie生效时间 ,单位秒;-->
//        simpleCookie.setMaxAge(60);
//        return simpleCookie;
//    }
//
//    //cookie管理对象;
//    @Bean
//    public CookieRememberMeManager cookieRememberMeManager() {
//        LOGGER.info("ShiroConfiguration.rememberMeManager()");
//        CookieRememberMeManager manager = new CookieRememberMeManager();
//        manager.setCookie(rememberMeCookie());
//        return manager;
//    }
//
//}
