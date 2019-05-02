package com.example.storm.controller;

import com.example.storm.domain.MapBean;
import com.example.storm.service.MapService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class StateApp {

    @Autowired
    MapService mapService;

    @RequestMapping(value = "/map",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
    public ModelAndView map(){
        return new ModelAndView("map");
    }

    @RequestMapping(value = "/map_state",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
    public ModelAndView map_state() throws IOException {
        ModelAndView mapstate = new ModelAndView("map_state");
        //得到查询数据的json串
        List<MapBean> mapBeans = mapService.query();
        JSONArray jsonArray = JSONArray.fromObject(mapBeans);

        mapstate.addObject("data_json",jsonArray);
        return mapstate;
    }
}
