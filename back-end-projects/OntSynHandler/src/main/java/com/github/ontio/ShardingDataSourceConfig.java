package com.github.ontio;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Maps;
import io.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
@EnableConfigurationProperties(ShardingMasterSlaveConfig.class)
//@ConditionalOnProperty({"sharding.jdbc.datasource.master.url", "sharding.jdbc.config.masterslave.master-data-source-name"})
public class ShardingDataSourceConfig {

    @Autowired(required = false)
    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;

    @Bean("dataSource")
    public DataSource masterSlaveDataSource() throws SQLException {
        shardingMasterSlaveConfig.getDataSources().forEach((k, v) -> configDataSource(v));
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.putAll(shardingMasterSlaveConfig.getDataSources());
        Map<String, Object> propertiesConstantMap = Maps.newHashMap();
        propertiesConstantMap.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        Properties properties = new Properties();
        properties.putAll(propertiesConstantMap);
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, shardingMasterSlaveConfig.getMasterSlaveRule(), propertiesConstantMap, properties);
        log.info("masterSlaveDataSource config complete");
        return dataSource;
    }

    private void configDataSource(DruidDataSource druidDataSource) {
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("select 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxOpenPreparedStatements(20);
        druidDataSource.setUseGlobalDataSourceStat(true);
        try {
            druidDataSource.setFilters("stat,wall,slf4j");
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
    }

    public ServletRegistrationBean getServletRegistrationBean(String username,  String password) throws Exception {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");//配置访问URL
        reg.addInitParameter("loginUsername", username);  //配置用户名，这里使用数据库账号。
        reg.addInitParameter("loginPassword", password);  //配置用户名，这里使用数据库密码
        reg.addInitParameter("logSlowSql", "true");   //是否启用慢sql
        return reg;
    }


    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");  //配置那些资源不被拦截
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }
    @Bean
    public ServletRegistrationBean druidServlet() throws Exception {
        return getServletRegistrationBean("yin",  "yin");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        return getFilterRegistrationBean();
    }

}
