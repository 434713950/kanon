package com.github.kanon.common.utils.net;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/9/13
 */
public class URLUtil {

    /**
     * 获得path部分<br>
     *
     * @param uriStr URI路径
     * @return path
     */
    public static String getPath(String uriStr) {
        URI uri = null;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new RuntimeException("uri格式错误");
        }
        return uri.getPath();
    }
}
