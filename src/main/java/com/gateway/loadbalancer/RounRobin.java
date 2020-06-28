package com.gateway.loadbalancer;

import com.gateway.core.Helper;
import com.gateway.instance.Instance;
import com.gateway.instance.InstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jameshart on 6/9/20.
 */
@RestController
public class RounRobin {

    // Dox
    // https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpServletRequest.html

    private static final Logger logger = LoggerFactory.getLogger(RounRobin.class);
    Helper helper = new Helper();

    private final InstanceRepository instances;
    private final RouterRepository routers;
    public RounRobin(InstanceRepository instances, RouterRepository routers) {
        this.instances = instances;
        this.routers = routers;
    }

    //Helper method
    public void DetermineInstance () {
        //use this to refactor the code below....
    }

    @RequestMapping(value = "gateway/v1/rr/**", method = RequestMethod.GET, produces = "application/json")
    public void RRGet(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) throws IOException {
        logger.info("G E T");
        long count = 0;
        //Check if IP address is okay (later)

        //Get router information and determine which instance to send to
        Router router = routers.findBycallSign("roundrobin");
        logger.info(router.toString());  //for debug


        //In the for and if block first determine how many instances there are.
        //Then determine what the rotation number will be based on how many instances there are.
        //The rotation number determines which instance of the server to route to.
        for (Instance anInstance : instances.findAll()) {
            count++;
            logger.info("ROUTE : " + anInstance.getAddress());
        }

        if (router.rotation >= count) {
            router.rotation = 1;
        } else {
            router.rotation++;
        }

        //Find and use Instance
        Instance instance = instances.findByid(router.rotation);
        logger.info(instance.toString());

        //Update router info
        routers.save(router); //setting the rotation number for the next iteration
        //logger.info(router.toString());  //for debug


        //Redirect the to the URL if all is well
        String theRoute = httpServletRequest.getHeader("route");
        String aString = "https://" + instance.getAddress() + "/" + theRoute  ;
        logger.info(aString);
        httpServletResponse.setStatus(307);
        httpServletResponse.addHeader("Authorization", httpServletRequest.getHeader("Authorization"));
        httpServletResponse.sendRedirect(aString);

    }


    @RequestMapping(value = "gateway/v1/rr/**", method = RequestMethod.POST, produces = "application/json")
    public void RRPost(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) throws IOException {
        logger.info("P O S T ");
        long count = 0;
        //Check if IP address is okay (later)

        //Get router information and determine which instance to send to
        Router router = routers.findBycallSign("roundrobin");
        logger.info(router.toString());  //for debug


        //In the for and if block first determine how many instances there are.
        //Then determine what the rotation number will be based on how many instances there are.
        //The rotation number determines which instance of the server to route to.
        for (Instance anInstance : instances.findAll()) {
            count++;
            logger.info("ROUTE : " + anInstance.getAddress());
        }

        if (router.rotation >= count) {
            router.rotation = 1;
        } else {
            router.rotation++;
        }

        //Find and use Instance
        Instance instance = instances.findByid(router.rotation);
        logger.info(instance.toString());

        //Update router info
        routers.save(router); //setting the rotation number for the next iteration
        //logger.info(router.toString());  //for debug

        logger.info(httpServletRequest.getQueryString());
        //use this query string to send new http requests that would return the results.

        //NO REDIRECT FOR POST.... THIS IS ONLY HERE FOR GUIDANCE RIGHT NOW
        //Redirect the to the URL if all is well
        String theRoute = httpServletRequest.getHeader("route");
        String aString = "https://" + instance.getAddress() + "/" + theRoute  ;
        logger.info(aString);

        httpServletResponse.addHeader("Authorization",
                httpServletRequest.getHeader("Authorization"));
        httpServletResponse.sendRedirect(aString);

    }



}
