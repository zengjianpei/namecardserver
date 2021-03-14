package com.devsjk.namecardserver.utils.wxUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;


public class WxRequestUtil {
    public static String makeGetRequest(String url) {
        URI uri = URI.create(url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        String result = null;
        try {
            CloseableHttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }

                result = sb.toString().trim();

                reader.close();
                client.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==>"+result);
        return result;
    }

    /**
     *
     * @param urlStr:请求地址
     * @param xmlStr:xml字符串
     * @return 微信返回来的XML字符串，可以转换成map再解析
     */
    public static String makeXMLPostRequest(String urlStr, String xmlStr){
        String result = "";
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("Content-Type", "text/xml");

            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlStr.getBytes("UTF-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                result += line;
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 带证书的请求
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String doRefund(String url,String data,String password) throws Exception {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("/opt/server/t_wxCourse/cert/apiclient_cert.p12"));//P12文件在服务器磁盘中的目录
        try {
            /**
             * 此处要改成你的MCHID
             * */
            keyStore.load(instream, password.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        /**
         * 此处要改成你的MCHID
         * */
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     *
     * @param urlStr:请求地址
     * @param xmlStr:xml字符串
     * @return 微信返回来的XML字符串，可以转换成map再解析
     */
    public static byte[] makeXMLPostRequestByte(String urlStr, String xmlStr){
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("Content-Type", "text/xml");

            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlStr.getBytes("UTF-8")));
            out.flush();
            out.close();

            ByteArrayOutputStream outt=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024*4];
            int n=0;
            while ( (n=con.getInputStream().read(buffer)) !=-1) {
                outt.write(buffer,0,n);
            }
            return outt.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
