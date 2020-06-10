package com.gateway.core;

import com.gateway.instance.Instance;
import com.gateway.instance.InstanceRepository;
import com.gateway.loadbalancer.Router;
import com.gateway.loadbalancer.RouterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jameshart on 6/4/20.
 */
@Component
public class DatabaseRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseRunner.class);
    Helper helper = new Helper();

    private final InstanceRepository instances;
    private final RouterRepository routers;

    @Autowired
    public DatabaseRunner(InstanceRepository instances, RouterRepository routers) {
        this.instances = instances;
        this.routers = routers;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        //StartInstance();
        StartRouter();

    }

    // - Pragma -

    public void StartRouter() {
        String createdAt = helper.CreatedAt();
        Router router = new Router(createdAt, 0, "alpha");
        logger.info(router.toString());
        routers.save(router);

    }

    public void StartInstance() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String createdAt = df.format(dateobj);

        Instance alpha = new Instance("null", createdAt, "alpha");
        instances.save(alpha);
        logger.info(alpha.toString());
    }
}