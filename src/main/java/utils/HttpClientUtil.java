package utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final CloseableHttpClient httpClient;


    // 初始化http客户端对象
    static {
        httpClient = HttpClientBuilder.create().build();
    }

    public static String buildUrl(String url, Map<String, String> queryParams) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (queryParams != null) {
            for (String key :
                    queryParams.keySet()) {
                uriBuilder.addParameter(key, queryParams.get(key));
            }
        }
        return uriBuilder.toString();
    }


    public static ResponseData get(String url, Map<String, String> queryParams) throws URISyntaxException, IOException {


        // 构造URL
        String url2 = buildUrl(url, queryParams);

        // 创建get请求
        HttpGet httpGet = new HttpGet((url2));

        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 构造响应数据
        ResponseData responseData = buildResponseData(response);

        // 释放资源
        response.close();

        return responseData;


    }

    private static ResponseData buildResponseData(CloseableHttpResponse response) throws IOException {

        ResponseData responseData = new ResponseData();
        // 响应状态码
        responseData.setStatusCode(response.getStatusLine().getStatusCode());
        // 响应体数据

        String responseStr = EntityUtils.toString(response.getEntity());
        if (responseStr != null && responseStr.startsWith("{")){
            responseData.setBody(JSONObject.parseObject(responseStr));
        }
        return responseData;
    }

}
