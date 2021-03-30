package utils;

import com.alibaba.fastjson.JSONObject;

public class ResponseData {
    // 响应体状态吗
    private int statusCode;

    private JSONObject body;


    public ResponseData(){

    }

    public ResponseData(int statusCode, JSONObject body){
        this.statusCode = statusCode;
        this.body = body;
    }


    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
