package com.example.multipledatasource.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang Junwei
 * @date 2023/5/31 17:32
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private SqlSession sqlSession;

    @DS("master")
    @GetMapping("/m1")
    public List<Object> getM1() {
        return sqlSession.selectList("SELECT * FROM users");
    }

    @DS("slave_1")
    @GetMapping("/m2")
    public List<Object> getM2() {
        return sqlSession.selectList("SELECT * FROM users");
    }
}
