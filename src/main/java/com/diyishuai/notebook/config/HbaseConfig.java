package com.diyishuai.notebook.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Configuration
public class HbaseConfig {

    @Autowired
    private Environment env;

    @Autowired
    private org.apache.hadoop.conf.Configuration config;

    @Bean
    public org.apache.hadoop.conf.Configuration getConfig(){
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", env.getProperty("hbase.zookeeper.quorum"));
        return config;
    }

    @Bean
    public Connection getConn() throws IOException {
        return ConnectionFactory.createConnection(config);
    }

}
