package me.zhouruikang.lovecanfly.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zhouruikang.lovecanfly.entity.RepMsg;
import me.zhouruikang.lovecanfly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-02-19 6:53 下午
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(  "/","/css/**","/js/**","/fonts/**","/img/**", "/index.html", "/favicon.ico","/upload/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                .and()
                .formLogin()
                .loginPage("/login_p") //未登录但 访问需登录接口时 的重定向接口
                .loginProcessingUrl("/login") //作为前端登录接口（username,password）
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                httpServletResponse.setContentType("application/json;charset=utf-8");
                                ObjectMapper om = new ObjectMapper();
                                PrintWriter out = httpServletResponse.getWriter();
                                out.write(om.writeValueAsString(new RepMsg("success","登陆成功！")));
                                out.flush();
                                out.close();
                            }
                        })
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                httpServletResponse.setContentType("application/json;charset=utf-8");
                                String msg = "";
                                if (e instanceof BadCredentialsException ||
                                        e instanceof UsernameNotFoundException) {
                                    msg = "账户名或者密码输入错误!";
                                } else if (e instanceof LockedException) {
                                    msg = "账户被锁定，请联系管理员!";
                                } else if (e instanceof CredentialsExpiredException) {
                                    msg = "证书过期，请联系管理员!";
                                } else if (e instanceof AccountExpiredException) {
                                    msg = "账户过期，请联系管理员!";
                                } else if (e instanceof DisabledException) {
                                    msg = "账户暂未审核通过，请联系管理员!";
                                } else {
                                    msg = "登录失败!";
                                }
                                httpServletResponse.setStatus(401);
                                ObjectMapper om = new ObjectMapper();
                                PrintWriter out = httpServletResponse.getWriter();
                                out.write(om.writeValueAsString(new RepMsg("error",msg)));
                                out.flush();
                                out.close();
                            }
                        })
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(om.writeValueAsString(new RepMsg("success","注销成功！")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable()//禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler(){
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(om.writeValueAsString(new RepMsg("error","权限不足，请联系管理员！")));
                        out.flush();
                        out.close();
                    }
                });
    }
}
