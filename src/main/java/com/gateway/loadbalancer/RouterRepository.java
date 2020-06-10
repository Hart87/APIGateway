package com.gateway.loadbalancer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jameshart on 6/9/20.
 */
@RepositoryRestResource()
public interface RouterRepository extends CrudRepository<Router, Long> {

    //Methodz here
    Router findByRotation(long rotation);
    Router findBycallSign(String callSign);
    Router findByid(long id);
}