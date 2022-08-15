package com.relay.protection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    public static void main(String[] args) {
        SecurityConfig test = new SecurityConfig();
        String encode = new BCryptPasswordEncoder().encode("1234");
        System.out.println(encode);
        boolean matches = new BCryptPasswordEncoder().matches("1234", "$2a$10$aNjPeXe2vnSVr4.i2ae5GOa5hsoRS8GAWzyh41FfDQTFutXbxX116");
        System.out.println(matches);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //ctrl+o可以查看一个类中所有的可被重写的方法
    //将AuthenticationManager注入到spring容器中
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //配置某个接口可以匿名访问
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                //关闭csrf    详细介绍:https://blog.csdn.net/weixin_40482816/article/details/114301717
                        csrf().disable()
                //不从session中获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                //放行swagger
                .antMatchers("/swagger-ui.html","/swagger-resources/**","/webjars/**","/v2/**","/api/**").permitAll()
                // 放行公共方法
                .antMatchers("/common/select", "/common/log").permitAll()
                //除了上面的请求全部需要鉴权认证
                .anyRequest().authenticated();
        //添加我们的自定义过滤器，并将其置于授权过滤器UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //配置自定义异常处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                accessDeniedHandler(accessDeniedHandler);
        //开启跨域请求
        http.cors();
    }

}

