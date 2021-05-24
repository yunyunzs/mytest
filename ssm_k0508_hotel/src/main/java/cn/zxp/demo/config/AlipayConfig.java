package cn.zxp.demo.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：  ksfxhw3818@sandbox.com   111111
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117657432";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4FGKnIx3UH5xXLdAS1atcXAmRuWmDBxISrnp1f2mC8BAciiCeHetLeHBF83jfTKTRY4ahNOWLyidMaK9JKKmjSG+lab0QDRIQvEXGPXfHDynhQHewaA1OyRkMLud75RWuCcRkcqp5I61FtKFE4p9RVK9WRDKZj/khsSgZyUKfie2bwy2XWU9O77fW4nEa4DWQR6AVaOcT29yrA+4jgFqD+eMM/JhY2Kmuo4ZxUK1Eq2E7lxSgVGFQa6ULW+J13h7d1Ps6W3dV/yZGuWJJm8b5c24Mjmu347hedhbsWXNUOg/jOC4sLok+tMMrkBL+T6yFfzzAmSzjvJhyNU3dA+BHAgMBAAECggEBAJLiuWHS9K9aVf9+sAUHeMinLH+aquYCE1rS3Sa696+i7chINATXrPS48yXuROoyF2Z5+2jRc8WbFsay1gQTrWF8oJImkNWpCFxQT4EDdXtizzFNpvQd41iVwSXmm5JSV31bj9nzYbJuVIpFu1XjW+ujHpJAsvu0VcCbw3roDQCNy8523hgnOTlo01usGLxdh82jvlICNkP5SSRIjaFtZfGLH7F3XAftpzfFIsOSTyNBpWTC7N5blh6IYHG0xeQkVsJCnyxVVrAYD9GAl0DTIl5oysPZ/DxhpLOw8VlPTUxWED6SqCEe177b4ubTwfdiL/GNRq7vI1Ktbji82D4daMkCgYEA8FJL4V+JlfBn8LTDhlGbPE5CFi8DqIftrurngi8JnrC2ntjQP/0wkCA+NsuuTR0qIAI2y2axKDwkB8RablRDCs1VFTSWaeVAXfNfHyKOEFei9RivYAowa380jXhV8oOC9PuO44032X4hGpDLCTc8oc+SIGPzsTPIo6aHNA/TlV0CgYEAxBbFleqvMCQVyDKxrSgtpY6gJvZROyD3G3BvwACl6G56VmMQqib8FT3MNnULCeVxnbHHjKbVvr10RPr3QPiJtddxZYGdEotJ79j3y2CIR/dVpLzsNWtDC63/DHcKatKMmekXWWVlHE/fvNucE0YaFuf4R92OLONz6QRGPfke7fMCgYA9YZe1+bPnkx7JMTJbhU50Cb0Bc4Kosgc4TRFSg/ceDB6o1dHi9iVIOqQJVN6xHYjb4hTiwNjw4OOHBFTgpy70fFuF7geZBo/5LycVibrlW6zokySAEDoryrK+i4TQS1zQe8wUPMaOC5Qi9+aT9QLMhXraQQ1GLmbJvcf8uSoelQKBgAUWIiH1aH3zMa1zwzAerrBwtq7MDOdfv4kyrXNpkOQqmXnMMA553Gggpj9zYcnr7bqNdgOsADdmarGvYOKKj+frosFjT1O/0TcrhaH86wwS2mHflLD+C0suRENqgxaqR4NmjHiBLNJDMCaR+8Z0NxEJC2Ox15JKhCMzPS82WxsJAoGALVbOiCGDa5/EZoNYSDrKxWLmjO4y8WER0LPcfcFCJVjVuYvJto6imm+Rwn2WrbUrO+ZimgB6LrvDvf17dmi0kvln83JixxPmViu6gjciV4gRH0OIsKb3KH9u3qppUG89CXQuLUHMyLr21mT8fZey1KoBsRTfNwaeOmFJfcMeYDc=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuBRipyMd1B+cVy3QEtWrXFwJkblpgwcSEq56dX9pgvAQHIognh3rS3hwRfN430yk0WOGoTTli8onTGivSSipo0hvpWm9EA0SELxFxj13xw8p4UB3sGgNTskZDC7ne+UVrgnEZHKqeSOtRbShROKfUVSvVkQymY/5IbEoGclCn4ntm8Mtl1lPTu+31uJxGuA1kEegFWjnE9vcqwPuI4Bag/njDPyYWNiprqOGcVCtRKthO5cUoFRhUGulC1vidd4e3dT7Olt3Vf8mRrliSZvG+XNuDI5rt+O4XnYW7FlzVDoP4zguLC6JPrTDK5AS/k+shX88wJks47yYcjVN3QPgRwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/orders/afterOrdersPay";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

