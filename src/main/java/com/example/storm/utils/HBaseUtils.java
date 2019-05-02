package com.example.storm.utils;

import com.example.storm.domain.MapBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HBase操作工具类：Java工具类建议采用单例模式封装
 */
public class HBaseUtils {


    HBaseAdmin admin = null;
    Configuration configuration = null;


    /**
     * 私有改造方法
     */
    private HBaseUtils(){
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "192.168.48.138:2181");
        configuration.set("hbase.rootdir", "hdfs://192.168.48.138:8020/hbase");

        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;

    public  static synchronized HBaseUtils getInstance() {
        if(null == instance) {
            instance = new HBaseUtils();
        }
        return instance;
    }


    /**
     * 根据表名获取到HTable实例
     */
    public HTable getTable(String tableName) {

        HTable table = null;

        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /*
     * @author qianliu on 2019/3/27 20:50
     * @param tablename 表名
     * @return 返回列名的查询出来的结果,返回全表
     * @Discription:
     */
    public List<MapBean> query(String tablename) throws IOException {
        List<MapBean> list = new ArrayList<MapBean>();
        HTable table = getTable(tablename);

        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            //mapBean的lng和lat
            MapBean mapBean = new MapBean();
            String local = Bytes.toString(result.getRow());
            String split[] = local.split(",");
            mapBean.setLng(Double.parseDouble(split[0]));
            mapBean.setLat(Double.parseDouble(split[1]));

            //mapBean的count
            mapBean.setCount(Long.parseLong(Bytes.toString(result.getValue(Bytes.toBytes(Config.HBaseColumn1),Bytes.toBytes(Config.col1_info1))))*1000);
            list.add(mapBean);
        }


        return list;
    }

}
