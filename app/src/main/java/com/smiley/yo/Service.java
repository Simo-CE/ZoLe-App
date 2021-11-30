package com.smiley.yo;

import java.util.List;

public class Service {
    private String service_title;
    private int service_price;

    Service(String service_title, int service_price) {
        this.service_title = service_title;
        this.service_price = service_price;
    }

    // Getter
    public String getServiceTitle() {
        return service_title;
    }
    public Integer getServicePrice() {
        return service_price;
    }
    // Setters
    public void setServiceTitle(String newTitle) {
        this.service_title = newTitle;
    }
    public void setServicePrice(Integer newPrice) {
        this.service_price = newPrice;
    }

}