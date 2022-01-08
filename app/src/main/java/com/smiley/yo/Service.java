package com.smiley.yo;

import java.util.List;

public class Service {
    private String title;
    private String price;
    private String desc;
    private boolean expandables;

    Service(){}

     Service(String service_title, String service_price,String desc) {
        this.title = service_title;
        this.price = service_price;
        this.desc = desc;
        this.expandables=false;

    }

    public boolean isExpandables(){
        return expandables;
    }
    // Getter
    public String getTitle() {
        return title;
    }
    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }
    //Setter
    public void setTitle(String title) {
        this.title=title;
    }
    public void setPrice(String price) { this.price=price; }
    public void setDesc(String desc) { this.desc=desc; }
    public void setExpandables(boolean expandables){
        this.expandables=expandables;
    }


}