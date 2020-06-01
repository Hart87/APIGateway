package com.gateway.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping(value = "test/{param}", method = RequestMethod.GET)
    public void Test(HttpServletResponse httpServletResponse,
                     @PathVariable("param") String param) throws IOException {
        logger.info("Some " + param);
        httpServletResponse.sendRedirect("https://jsonplaceholder.typicode.com/todos/1");
    }
}
