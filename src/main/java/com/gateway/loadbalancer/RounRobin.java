package com.gateway.loadbalancer;

import com.gateway.core.Helper;
import com.gateway.instance.Instance;
import com.gateway.instance.InstanceRepository;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Helper helper = new Helper();

    private final InstanceRepository instances;
    private final RouterRepository routers;
    public RounRobin(InstanceRepository instances, RouterRepository routers) {
        this.instances = instances;
        this.routers = routers;
    }


    @RequestMapping(value = "gateway/v1/rr/", method = RequestMethod.GET, produces = "application/json")
    public String RRGet(
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

        String authHeader = httpServletRequest.getHeader("Authorization");
        logger.info(authHeader);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(aString)
                .addHeader("Authorization", authHeader)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();

    }


    @RequestMapping(value = "gateway/v1/rr/", method = RequestMethod.POST, produces = "application/json")
    public String RRPost(
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

        String theRoute = httpServletRequest.getHeader("route");
        String theQuery = httpServletRequest.getQueryString();
        String aString = "https://" + instance.getAddress() + "/" + theRoute + "/?" + theQuery  ;
        logger.info(aString);

        //Send it and return the output
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build(); //.add for parameters but query string has them
        Request request = new Request.Builder()
                .addHeader("Authorization", httpServletRequest.getHeader("Authorization"))
                .url(aString)
                .post(formBody)
                .build();
        Response response = client
                .newCall(request)
                .execute();

        return response.body().string();

    }

    @RequestMapping(value = "gateway/v1/rr/", method = RequestMethod.PUT, produces = "application/json")
    public String RRPut(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) throws IOException {
        logger.info("P U T ");
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

        String theRoute = httpServletRequest.getHeader("route");
        String theQuery = httpServletRequest.getQueryString();
        String aString = "https://" + instance.getAddress() + "/" + theRoute + "/?" + theQuery  ;
        logger.info(aString);

        //Send it and return the output
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build(); //.add for parameters but query string has them
        Request request = new Request.Builder()
                .addHeader("Authorization", httpServletRequest.getHeader("Authorization"))
                .url(aString)
                .put(formBody)
                .build();
        Response response = client
                .newCall(request)
                .execute();

        return response.body().string();

    }

    @RequestMapping(value = "gateway/v1/rr/", method = RequestMethod.DELETE, produces = "application/json")
    public String RRDelete(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest) throws IOException {
        logger.info("D E L E T E ");
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

        String theRoute = httpServletRequest.getHeader("route");
        String theQuery = httpServletRequest.getQueryString();
        String aString = "https://" + instance.getAddress() + "/" + theRoute + "/?" + theQuery  ;
        logger.info(aString);

        //Send it and return the output
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build(); //.add for parameters but query string has them
        Request request = new Request.Builder()
                .addHeader("Authorization", httpServletRequest.getHeader("Authorization"))
                .url(aString)
                .delete(formBody)
                .build();
        Response response = client
                .newCall(request)
                .execute();

        return response.body().string();

    }



}
