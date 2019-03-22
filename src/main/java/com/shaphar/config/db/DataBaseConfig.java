package com.shaphar.config.db;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataBaseConfig {
    @Autowired
    private Environment env;

    //动态数据源
    public AtomikosDataSourceBean getDataSourceToAll(OracleXADataSource source, String URL , String User, String Pwd)
    {
        source.setURL(URL);
        source.setUser(User);
        source.setPassword(Pwd);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(source);
        return xaDataSource;
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = userTransaction();
        JtaTransactionManager manager = new JtaTransactionManager(userTransaction,atomikosTransactionManager());
        return manager;
    }

    @Bean(name = "testErpSitDB")
    public DataSource testErpSitDB() throws SQLException {
        OracleXADataSource source = new OracleXADataSource();
        return getDataSourceToAll(source ,
                env.getProperty("spring.datasource.url") ,
                env.getProperty("spring.datasource.username") ,
                env.getProperty("spring.datasource.password"));
    }
    @Bean(name = "testMobileErpDB")
    public DataSource testMobileErpDB() throws SQLException {
        OracleXADataSource source = new OracleXADataSource();
        return getDataSourceToAll(source ,
                env.getProperty("spring.datasource.mobile.url") ,
                env.getProperty("spring.datasource.mobile.username") ,
                env.getProperty("spring.datasource.mobile.password"));
    }

    @Bean
    @Profile("prod")
    public DynamicDataSource getDataSourceProd(
            @Qualifier("testErpSitDB") DataSource testErpSitDB,
            @Qualifier("testMobileErpDB") DataSource testMobileErpDB
    ) {
        Map<Object, Object> map = new HashMap<>();
        map.put("testErpSitDB",testErpSitDB);
        map.put("testMobileErpDB",testMobileErpDB);
        //采用是想AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(map);
        dataSource.setDefaultTargetDataSource(testErpSitDB);
        return dataSource;
    }
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory dBConfigSqlSessionFactory(@Autowired DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        return bean.getObject();
    }
}
