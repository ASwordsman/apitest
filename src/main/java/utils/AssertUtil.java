package utils;

import org.testng.Assert;

public class AssertUtil {

    public static void commonAssert(ResponseData responseData, int statusCode, int status, String description) {
        // 响应状态码
        Assert.assertEquals(responseData.getStatusCode(), statusCode);
        // 响应体-status
        Assert.assertEquals(responseData.getBody().getIntValue("status"), status);
        // 响应体-description
        Assert.assertEquals(responseData.getBody().getString("description"), description);
    }

}
