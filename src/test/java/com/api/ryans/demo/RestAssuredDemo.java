package com.api.ryans.demo;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class RestAssuredDemo {
    @Test
    public void testGET() {
        // 普通导入: RestAssured是个工具类，所有的方法都是通过"类名."的方式来调用
        // RestAssured.get();

        // 静态导入 + 链式编程
        // 方法的返回值是个对象
        given(). // 请求头、请求参数、请求体、cookie等
                queryParam("username", "zhangsan").
                queryParam("password", "123456").
        when().get("http://httpbin.org/get").
        then().log().body();
    }
}
