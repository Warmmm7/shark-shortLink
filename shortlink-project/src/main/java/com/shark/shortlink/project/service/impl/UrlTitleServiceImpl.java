package com.shark.shortlink.project.service.impl;

import com.shark.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * URL 标题接口实现层
 */
@Service
public class UrlTitleServiceImpl implements UrlTitleService {
    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection =(HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");//设置为get请求
        connection.connect();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){//链接成功
            Document document = Jsoup.connect(url).get();//Jsoup用于解析html
            return document.title();
        }
        return "Error while fetching title.";
    }
}
