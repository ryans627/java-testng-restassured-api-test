package com.api.ryans.demo;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RestAssuredDemo {
    @Test
    public void testGET() {
        // 普通导入: RestAssured是个工具类，所有的方法都是通过"类名."的方式来调用
        // RestAssured.get();

        // 静态导入 + 链式编程
        // 方法的返回值是个对象
        given(). // 请求头、请求参数、请求体、cookie等
                queryParam("username", "zhangsan").queryParam("password", "123456").when().get("http://httpbin.org/get").then().log().body();
    }

    @Test
    public void testPOSTForm() {
        // post请求的参数不是写在url里的，而是在body里
        given(). // form表单参数: 参数的数据类型
                header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"). // 请求头
                body("username=zhangsan&password=123456").when().post("http://httpbin.org/post").then().log().body();
    }

    // JSON参数
    @Test
    public void testPOSTJson() {
        String jsonData = "{\"mobilephone\":\"13323234545\",\"pwd\":\"123456\"}";
        given().
                header("Content-Type", "application/json").body(jsonData).
                when().
                post("http://httpbin.org/post").
                then().
                log().body();
    }

    // XML参数
    @Test
    public void testPOSTXML() {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<suite>\n" +
                " <class>测试xml</class>\n" +
                "</suite>";
        given().
                header("Content-Type", "application/xml").body(xmlStr).
                when().
                post("http://httpbin.org/post").
                then().
                log().body();
    }

    @Test
    public void testPOSTFileUpload() {
        File file = new File("src/test/resources/liuyifei.jpg");

        given().
                header("Content-Type", "multipart/form-data").
                multiPart(file).
                when().
                post("http://httpbin.org/post").
                then().
                log().
                body();
    }

    @Test
    public void testPUT() {
        given().
                when().
                put("http://httpbin.org/put").
                then().
                log().body();
    }

    @Test
    public void testPOSTWithMapQueryParams() {
        String jsonData = "{'page': 1}";
        Map<String, Object> map = new HashMap<>();
        map.put("application", "app");
        map.put("application_client_type", "weixin");
        map.put("s", "index/index");

        given().
                header("Content-Type", "application/json").
                body(jsonData).
                queryParams(map).
                when().
                post("http://httpbin.org/post").
                then().
                log().
                body();
    }

    @Test
    public void testResponse() {
        Response response = given().
                when().
                post("http://httpbin.org/post").
                then().
                extract().response();
        // 响应行 状态码 状态行
        System.out.println("====== status code ======");
        System.out.println(response.statusCode());
        System.out.println("====== status line ======");
        System.out.println(response.statusLine());
        // 响应头
        System.out.println("====== headers ======");
        System.out.println(response.getHeaders());
        // cookie
        System.out.println("====== cookies ======");
        System.out.println(response.getCookies());
        System.out.println("======= response as string =====");
        System.out.println(response.asString());

        String str = response.jsonPath().get("origin").toString();
        String str2 = response.jsonPath().get("headers.Host").toString();
        System.out.println(str);
        System.out.println(str2);
    }
}
