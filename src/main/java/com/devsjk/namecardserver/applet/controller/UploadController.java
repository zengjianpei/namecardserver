package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.utils.tencentUtils.OssUtils;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Auther: zjp
 * @Date: 2020/11/11 23:25
 * @Description:
 */
@LeshangController("appletUploadController")
public class UploadController extends BaseController {

    @Autowired
    private OssUtils ossUtils;

    @PostMapping("/admin/v1/upload")
    @AutoReturn
    public void upload(MultipartFile file) throws IOException {
        this.setResult(ossUtils.uploadFile(file));
    }
}
