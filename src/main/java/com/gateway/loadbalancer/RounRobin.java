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

    @RequestMapping(value = "gateway/v1/rr/**", produces = "application/json")
    public void RR(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest,
            @RequestParam("route") String routeParam) throws IOException {
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
        //logger.info(instance.toString());

        //Update router info
        routers.save(router); //setting the rotation number for the next iteration
        //logger.info(router.toString());  //for debug


        //Redirect the to the URL if all is well
        String aString = "https://" + instance.getAddress() + routeParam;
        logger.info(aString);
        httpServletResponse.sendRedirect("https://" + instance.getAddress() + routeParam);

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
