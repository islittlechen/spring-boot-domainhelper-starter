package com.cxj.domainhelper.boot.autoconfigure;

import com.github.domainhelper.configure.ConfigurationService;
import com.github.domainhelper.interceptor.DomainQueryInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class DomainHelperAutoConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private ConfigurationService configurationService;

    @PostConstruct
    public void addPageInterceptor() {
        DomainQueryInterceptor interceptor = new DomainQueryInterceptor();

        interceptor.setConfigurationService(this.configurationService);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
