package com.gateway.core;

import com.gateway.instance.Instance;
import com.gateway.instance.InstanceRepository;
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


    private final InstanceRepository instances;

    @Autowired
    public DatabaseRunner(InstanceRepository instances) {
        this.instances = instances;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        StartInstance();

    }

    // - Pragma -

    public void StartInstance() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String createdAt = df.format(dateobj);

        Instance alpha = new Instance("null", createdAt, "alpha");
        instances.save(alpha);
        logger.info(alpha.toString());
    }
}