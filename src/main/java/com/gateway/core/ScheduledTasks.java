package com.gateway.core;

import com.gateway.instance.Instance;
import com.gateway.instance.InstanceRepository;
import com.gateway.loadbalancer.RouterRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jameshart on 6/1/20.
 */
@Component
public class ScheduledTasks {

    private final InstanceRepository instances;
    private  final RouterRepository routers;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    OkHttpClient client = new OkHttpClient();

    @Autowired
    public ScheduledTasks(InstanceRepository instances, RouterRepository routers) {

        this.instances = instances;
        this.routers = routers;
    }

    @Scheduled(fixedRate = 20000)  //20 seconds
    public void TestInstances() {

        log.info("Test Instances : {}", dateFormat.format(new Date()));

        //Find all instances
        for (Instance anInstance : instances.findAll()) {

            //Send a GET reuqest to /health - an actuator endpoint with no security
            String url = "https://" + anInstance.getAddress();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response httpResponse = null;
            try {
                httpResponse = client.newCall(request).execute();

                //determine if they're alive
                if (httpResponse.code() == 200) {
                    //If the instance check returns a 200 code then keep the instance
                    log.info("Instance " + anInstance.getCallSign() + " is operational" );
                }

            } catch (IOException e) {
                log.info("Instance " + anInstance.getCallSign() + "is not working. Removing it.");
                //Send a notification to root, possibly an SMS with Twilio. A text would be beneficial
                //because this is likely the first point that finds out an instance is down.
                //e.printStackTrace();
                log.info("DELETING INSTANCE : " + anInstance.toString());
                instances.delete(anInstance);
            }
        }
    }

    @Scheduled(fixedRate = 60000)  //60 seconds
    public void ResetRequestsCount() {

        log.info("Reset the rate limiting : {}", dateFormat.format(new Date()));


    }
}