package com.gateway.loadbalancer;

import com.gateway.core.Helper;
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

    @RequestMapping(value = "gateway/v1/rr/**", produces = "application/json")
    public void RR(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest,
            @RequestParam("route") String body) throws IOException {
        //Check if IP address is okay (later)
        //Take request parameters to form a URL for the redirect
        logger.info("Some " + body);

        String testString = httpServletRequest.getParameter("route");
        logger.info("FROM PARAMS" + testString);


        //Get router information and determine which instance to send to
        Router router = routers.findBycallSign("alpha");
        
        //Update router info

        //Redirect the to the URL if all is well
        httpServletResponse.sendRedirect("https://jsonplaceholder.typicode.com/todos/1");

    }


    // - - Pragma - -  TESTING...

    @RequestMapping(value = "instance/{name}", method= RequestMethod.GET, produces = "application/json")
    public String Up(
            @PathVariable("name") String name)  {

        return "Hello " + name + ". The Instance controller is up.";

    }

    @RequestMapping(value = "test/{param}", method = RequestMethod.GET)
    public void Test(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest,
                     @PathVariable("param") String param) throws IOException {

        logger.info("Some " + param);

        String testString = httpServletRequest.getPathInfo();
        logger.info(testString.toString());
        httpServletResponse.sendRedirect("https://jsonplaceholder.typicode.com/todos/1");
    }
}
