package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.Industry;

import java.util.List;

public interface IndustryService {

    List<Industry> selectOptions(Industry industry);

}
