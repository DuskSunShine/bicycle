package com.bravesoft.bravegogo.bean;

import java.util.List;

/**
 * Created by SCY on 2017/3/27 20:18.
 */
public class bikesBean {

    private List<BikesBean> bikes;

    public List<BikesBean> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikesBean> bikes) {
        this.bikes = bikes;
    }

    public static class BikesBean {
        /**
         * id : 028001315
         * status : reserved
         * location : 104.073462,30.635483
         */

        private String id;
        private String status;
        private String location;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
