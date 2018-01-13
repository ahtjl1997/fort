package com.fort.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fort.service.employee.LoginService;
import com.fort.webapp.authentication.EmployeeDetailsService;
import com.fort.webapp.authentication.FortAuthenticationFailureHandler;
import com.fort.webapp.authentication.FortAuthenticationSuccessHandler;
import com.fort.webapp.authentication.FortUsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String defaultFailureUrl = "/login?error";
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/imgCode").permitAll()
		//凡是登录成功的用户都可访问
		.antMatchers("/home").hasRole(RoleConfig.LOGIN_USER)
		//用户管理模块
		.antMatchers("/employee/query","/employee/edit",
				"/employee/insertPage").hasRole(RoleConfig.VIEW_EMP)
		.antMatchers("/employee/insert").hasRole(RoleConfig.NEW_EMP)
		.antMatchers("/employee/update","/employee/status",
				"/employee/statuss").hasRole(RoleConfig.UPDATE_EMP)
		.antMatchers("/employee/delete","/employee/deletes").hasRole(RoleConfig.DEL_EMP)
		.antMatchers("/employee/checkName").hasAnyRole(RoleConfig.VIEW_EMP,
				RoleConfig.NEW_EMP,RoleConfig.UPDATE_EMP)
		.antMatchers("/employee/import").hasRole(RoleConfig.IMPORT_EMP)
		.antMatchers("/employee/export").hasRole(RoleConfig.EXPORT_EMP)
		
		//用户分组模块
		.antMatchers("/employeeGroup/query","/employeeGroup/edit",
				"/employeeGroup/insertPage").hasRole(RoleConfig.VIEW_EMP_GROUP)
		.antMatchers("/employeeGroup/insert").hasRole(RoleConfig.NEW_EMP_GROUP)
		.antMatchers("/employeeGroup/update").hasRole(RoleConfig.UPDATE_EMP_GROUP)
		.antMatchers("/employeeGroup/delete","/employeeGroup/deletes").hasRole(RoleConfig.DEL_EMP_GROUP)
		.antMatchers("/employeeGroup/checkName").hasAnyRole(RoleConfig.VIEW_EMP_GROUP,
				RoleConfig.NEW_EMP_GROUP,RoleConfig.UPDATE_EMP_GROUP)
		
		//角色模块
		.antMatchers("/role/query","/role/edit",
				"/role/insertPage").hasRole(RoleConfig.VIEW_ROLE)
		.antMatchers("/role/insert").hasRole(RoleConfig.NEW_ROLE)
		.antMatchers("/role/update").hasRole(RoleConfig.UPDATE_ROLE)
		.antMatchers("/role/delete","/role/deletes").hasRole(RoleConfig.DEL_ROLE)
		.antMatchers("/role/checkName").hasAnyRole(RoleConfig.VIEW_ROLE,
				RoleConfig.NEW_ROLE,RoleConfig.UPDATE_ROLE)
		
		//资源模块
		.antMatchers("/resource/query","/resource/edit",
				"/resource/insertPage").hasRole(RoleConfig.VIEW_RES)
		.antMatchers("/resource/insert").hasRole(RoleConfig.NEW_RES)
		.antMatchers("/resource/update").hasRole(RoleConfig.UPDATE_RES)
		.antMatchers("/resource/delete","/resource/deletes").hasRole(RoleConfig.DEL_RES)
		.antMatchers("/resource/checkName","/resource/queryOs","/resource/queryType").hasAnyRole(RoleConfig.VIEW_RES,
				RoleConfig.NEW_RES,RoleConfig.UPDATE_RES)
		
		//授权模块
		.antMatchers("/rule/query","/rule/edit",
				"/rule/insertPage").hasAnyRole(RoleConfig.VIEW_RULE)
		.antMatchers("/rule/insert").hasRole(RoleConfig.VIEW_RULE)
		.antMatchers("/rule/update").hasRole(RoleConfig.VIEW_RULE)
		.antMatchers("/rule/delete","/rule/deletes").hasRole(RoleConfig.VIEW_RULE)
		.antMatchers("/rule/checkName").hasRole(RoleConfig.VIEW_RULE)
		
		.and().headers().frameOptions().disable()
		
		//用户登录页面 所有人均可访问
		.and().formLogin().loginPage("/login").permitAll()
		.and().logout().invalidateHttpSession(true)
		.and().addFilterBefore(getUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);;
	}
	
	public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter authFilter = new FortUsernamePasswordAuthenticationFilter(loginService);
		authFilter.setAuthenticationManager(this.authenticationManager());
		authFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		return authFilter;
	}
	
	@Bean("authenticationFailureHandler")
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return new FortAuthenticationFailureHandler(defaultFailureUrl);
	}
	
	@Bean("authenticationSuccessHandler")
	public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
		return new FortAuthenticationSuccessHandler();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean("userDetailsService")
	public UserDetailsService getUserDetailsService() {
		return new EmployeeDetailsService();
	}
	
}
