package com.example.storm.service;

import com.example.storm.domain.MapBean;
import com.example.storm.utils.Config;
import com.example.storm.utils.HBaseUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 从hbase中访问数据，并且将其格式化为MapBean
* */
@Service
public class MapService {

    //查询数据
    public List<MapBean> query() throws IOException {
        List<MapBean> list = null;

        //取hbase中获取day中的所有课程以及课程访问量
        list = HBaseUtils.getInstance().query(Config.HBaseTableName);

        return list;
    }

    @Test
    public void query_test() throws IOException {
        List<MapBean> list = null;

        //取hbase中获取day中的所有课程以及课程访问量
        list = HBaseUtils.getInstance().query(Config.HBaseTableName);

        System.out.println(list.get(0).getCount());
    }
}
