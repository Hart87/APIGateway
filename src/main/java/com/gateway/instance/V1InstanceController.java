package com.gateway.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jameshart on 6/1/20.
 */

@RestController
public class V1InstanceController {

    private static final Logger logger = LoggerFactory.getLogger(V1InstanceController.class);

    @RequestMapping(value = "instance/{name}", method= RequestMethod.GET, produces = "application/json")
    public String Up(
            @PathVariable("name") String name) {

        return "Hello " + name + ". The Instance controller is up.";

    }
}
