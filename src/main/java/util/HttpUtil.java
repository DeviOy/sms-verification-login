package util;

import config.CONST;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * @author : ylz
 * @date : 2020/12/22 21:51
 */
public class HttpUtil {
    /**
     * 构造通用参数timestamp、signature、respDataType
     *
     * @return
     */
    public static String createCommonParam() {
        //时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        //签名
        String signature = DigestUtils.md5Hex(CONST.ACCOUNT_SID + CONST.AUTH_TOKEN + timestamp);

        return "&timestamp=" + timestamp + "&signature=" + signature + "&rspDataType=" + CONST.RESP_DATA_TYPE;
    }

    /**
     * post 请求
     *
     * @param url  功能和操作
     * @param body 要post的数据
     * @return
     */
    public static String post(String url, String body) {
        System.out.println("url:" + System.getProperty("line.separator") + url);
        System.out.println("body:" + System.getProperty("line.separator") + body);
        String result = "";

        try {
            OutputStreamWriter osw = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection urlConnection = realUrl.openConnection();
            //设置连接参数
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //提交数据
            osw = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
            osw.write(body);
            osw.flush();
            //读取返回数据
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true;
            //读第一行不加换行符
            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    result += System.getProperty("line.separator");
                }
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 回调测试工具方法
     *
     * @param url
     * @param body
     * @return
     */
    public static String postCallBack(String url, String body) {
        String result = "";
        try {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection urlConnection = realUrl.openConnection();
            //设置连接参数
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(20000);
            out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();
            //读取返回数据
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true;
            //读第一行不加换行符
            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    result += System.getProperty("line.separator");
                }
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成四位数
     *
     * @return
     */
    public static String generateFourNumber() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            stringBuilder.append(ch);
        }
        String code = stringBuilder.toString();
        return code;
    }

    public static void main(String[] args) {
        System.out.println(HttpUtil.generateFourNumber());
    }
}
