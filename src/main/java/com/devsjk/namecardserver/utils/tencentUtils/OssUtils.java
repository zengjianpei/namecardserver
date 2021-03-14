package com.devsjk.namecardserver.utils.tencentUtils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: zjp
 * @Date: 2020/11/16 22:23
 * @Description:
 */
@Service
public class OssUtils {

    @Value("${bucketName}")
    private String bucketName;

    @Value("${secretId}")
    private String secretId;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${cosRegion}")
    private String region;

    private String url="test/";

    public String uploadFile(MultipartFile file) throws IOException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = this.secretId;
        String secretKey =this.secretKey;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置 Content type, 默认是 application/octet-stream
        objectMetadata.setContentType("image/jpeg");
        String fileName=System.currentTimeMillis()+".jpg";
        PutObjectResult putObjectResult =cosClient.putObject(this.bucketName,url+fileName,file.getInputStream(),objectMetadata);
        //System.out.println(putObjectResult.getVersionId());
        return "https://"+this.bucketName+".cos."+this.region+".myqcloud.com/"+url+fileName;
    }

    public String uploadFile(InputStream inputStream) throws IOException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = this.secretId;
        String secretKey =this.secretKey;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置 Content type, 默认是 application/octet-stream
        objectMetadata.setContentType("image/jpeg");
        String fileName=System.currentTimeMillis()+".jpg";
        PutObjectResult putObjectResult =cosClient.putObject(this.bucketName,url+fileName,inputStream,objectMetadata);
        //System.out.println(putObjectResult.getVersionId());
        return "https://"+this.bucketName+".cos."+this.region+".myqcloud.com/"+url+fileName;
    }
}
