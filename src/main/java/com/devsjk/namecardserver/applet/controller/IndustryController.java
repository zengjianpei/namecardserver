package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.IndustryService;
import com.devsjk.namecardserver.model.Industry;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: zjp
 * @Date: 2020/9/5 16:30
 * @Description:
 */
@LeshangController("appletIndustryController")
public class IndustryController extends BaseController {

    @Autowired
    private IndustryService industryService;

    @PostMapping(value = "/applet/v1/industry/selectOptions")
    @AutoReturn
    public void selectOptions(Industry industry) {
        this.setResult(industryService.selectOptions(industry));
    }

}
