// Copyright 2020 ADTIMING TECHNOLOGY COMPANY LIMITED
// Licensed under the GNU Lesser General Public License Version 3

package com.adtiming.om.ds;

import com.adtiming.om.ds.web.filter.ResponseFilter;
import com.github.pagehelper.PageInterceptor;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"com.adtiming.om.ds"})
@EnableScheduling
@MapperScan({"com.adtiming.om.ds.dao"})
public class Application {
	
	/**
	 * cors
	 *
	 * @return the CorsFilter
	 */
	   @Bean
	@Order(0)
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    // 允许cookies跨域
	    config.setAllowCredentials(true);
	    // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
	    config.addAllowedOrigin("*");
	    // #允许访问的头信息,*表示全部
	    config.addAllowedHeader("*");
	    // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
	    config.setMaxAge(18000L);
	    // 允许提交请求的方法，*表示全部允许
	    config.addAllowedMethod("OPTIONS");
	    config.addAllowedMethod("HEAD");
	    // 允许Get的请求方法
	    config.addAllowedMethod("GET");
	    config.addAllowedMethod("PUT");
	    config.addAllowedMethod("POST");
	    config.addAllowedMethod("DELETE");
	    config.addAllowedMethod("PATCH");
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager txManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Scheduled(cron = "0 1 * * * ?")
    private void gzipAccessLogHourly() {
        log.info("gzip access log start");
        try {
            String[] cmd = {"bash", "-c", "cd log; ls access.*.log|xargs gzip"};
            Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            IOUtils.copy(p.getInputStream(), System.out);
            log.info("gzip access log finished {}", p.waitFor());
        } catch (Exception e) {
            log.error("gzip access log error", e);
        }
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(@Autowired DataSource dataSource) throws IOException {
        SqlSessionFactoryBean b = new SqlSessionFactoryBean();
        b.setDataSource(dataSource);

        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        b.setMapperLocations(resourceResolver.getResources("classpath*:mappers/*.xml"));
        PageInterceptor pi = new PageInterceptor();
        Properties prop = new Properties();
        prop.setProperty("helperDialect", "mysql");
        prop.setProperty("reasonable", "true");
        prop.setProperty("supportMethodsArguments", "true");
        prop.setProperty("params", "count=countSql");
        prop.setProperty("autoRuntimeDialect", "true");
        pi.setProperties(prop);
        b.setPlugins(pi);
        return b;
    }

    @Bean
    public FilterRegistrationBean<ResponseFilter> someFilterRegistration() {
        FilterRegistrationBean<ResponseFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ResponseFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("responseFilter");
        return registration;
    }
}
