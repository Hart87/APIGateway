package com.gateway.instance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jameshart on 6/1/20.
 */
@RepositoryRestResource()
public interface InstanceRepository extends CrudRepository<Instance, Long> {

    //Methodz here
    Instance findByAddress(String address);
    //Instance findByid(Long id);
    //Instance findByCallSign(String name);
}
