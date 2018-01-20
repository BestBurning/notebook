package com.diyishuai.notebook.dao;

import com.diyishuai.notebook.bean.HbaseData;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HbaseDao {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Connection connection;

    public boolean insertData(String tableName, String rowKey, List<HbaseData> data){
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            List<Put> puts = new ArrayList<>();
            for (HbaseData hbaseData : data) {
                Put put = new Put(Bytes.toBytes(rowKey));
                put.addColumn(hbaseData.getFamily(),hbaseData.getQualifier(),hbaseData.getValue());
                puts.add(put);
            }
            table.put(puts);
            logger.debug("插入数据成功!" + table.getTableDescriptor()+"===="+tableName+"/"+rowKey+"/"+data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 通过rowKey正则表达式查询
     * tableName：表名
     * reg：正则表达式
     * ResultScanner：结果集
     * IOException：IO异常
     */
    public ResultScanner queryByReg(String tableName, String reg)
            throws IOException {
        Table table =  connection.getTable(TableName.valueOf(tableName));// 从连接池中获取表
        Scan scan = new Scan();// 创建scan，用于查询
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(reg));// 创建正则表达式filter
        scan.setFilter(filter);// 设置filter
        ResultScanner scanner = table.getScanner(scan);// 通过scan查询结果
        table.close();// 关闭table
        return scanner;
    }

}
