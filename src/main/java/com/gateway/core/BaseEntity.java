package com.gateway.core;


import javax.persistence.*;

/**
 * Created by jameshart on 6/1/20.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @Version
    private Long version;

    protected BaseEntity(){
        id = null;
    }
}