package com.gateway.loadbalancer;

import com.gateway.core.BaseEntity;

import javax.persistence.Entity;

/**
 * Created by jameshart on 6/9/20.
 */
@Entity
public class Router extends BaseEntity {

    private String createdAt;
    private long rotation = 0;
    private String callSign;

    public Router(String createdAt, long rotation, String callSign) {
        this.createdAt = createdAt;
        this.rotation = rotation;
        this.callSign = callSign;
    }

    protected Router() {
        super();
    }

    public String getCallSign() {
        return callSign;
    }


    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getRotation() {
        return rotation;
    }

    public void setRotation(long rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "Router{" +
                "createdAt='" + createdAt + '\'' +
                ", rotation=" + rotation +
                ", callSign=" + callSign +
                '}';
    }
}