package com.gateway.instance;

import com.gateway.core.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jameshart on 6/1/20.
 */
@Entity
public class Instance extends BaseEntity{

    @NotNull
    @Size(min = 1, max = 50)
    private String address;
    private String createdAt;
    private String callSign;

    public Instance(String address, String createdAt, String callSign) {
        this.address = address;
        this.createdAt = createdAt;
        this.callSign = callSign;
    }

    protected Instance() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "address='" + address + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", callSign='" + callSign + '\'' +
                '}';
    }
}
