package com.youyi.ecom.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class HttpUtil {

    public static String get(String url) {
        try (HttpResponse response = HttpRequest.get(url)
                .execute()) {
            return response.body();
        }
    }

    public static String post(String url, String body) {

        try (HttpResponse response = HttpRequest.post(url)
                .body(body)
                .execute()) {
            return response.body();
        }
    }

}
