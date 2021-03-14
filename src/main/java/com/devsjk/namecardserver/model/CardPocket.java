package com.devsjk.namecardserver.model;

import com.leshang.framework3.model.BaseModel;
import java.time.LocalDateTime;

public class CardPocket extends BaseModel {
    private Integer id;

    private String company_user_id;

    private String card_id;

    private Byte top;

    private LocalDateTime visit_time;

    private LocalDateTime update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(String company_user_id) {
        this.company_user_id = company_user_id == null ? null : company_user_id.trim();
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id == null ? null : card_id.trim();
    }

    public Byte getTop() {
        return top;
    }

    public void setTop(Byte top) {
        this.top = top;
    }

    public LocalDateTime getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(LocalDateTime visit_time) {
        this.visit_time = visit_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }
}