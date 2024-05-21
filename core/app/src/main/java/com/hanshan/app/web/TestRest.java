package com.hanshan.app.web;

import com.hanshan.app.model.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestRest {
    public static final Logger logger = LoggerFactory.getLogger(TestRest.class);


    @GetMapping("test1")
    public TestVo getTest1() {
        return TestVo.builder()
                .id(1).name("test").build();
    }

    @GetMapping("test2")
    public Object getTest2() {
//        logger.info(mysql8Configuration.getDriver());
//        logger.info(mysql8Configuration.getServerUrl());
//        logger.info(mysql8Configuration.getHasDatabase() + "");
        return "";
    }
}
