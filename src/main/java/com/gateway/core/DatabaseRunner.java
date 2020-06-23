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

        StartInstance();
        StartRouter();

    }

    // - Pragma -

    public void StartRouter() {
        String createdAt = helper.CreatedAt();
        Router router = new Router(createdAt, 0, "roundrobin");
        logger.info(router.toString());
        routers.save(router);

    }

    public void StartInstance() {
        String createdAt = helper.CreatedAt();

        Instance alpha = new Instance("calm-meadows-88.com", createdAt, "heroku 1");
        instances.save(alpha);
        logger.info(alpha.toString());

        Instance alpha2 = new Instance("brown-ceader-14.com", createdAt, "heroku 2");
        instances.save(alpha2);
        logger.info(alpha.toString());

        Instance alpha3 = new Instance("hot-fire-19.com", createdAt, "heroku 3");
        instances.save(alpha3);
        logger.info(alpha.toString());
    }
}