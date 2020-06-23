package com.gateway.instance;

import com.gateway.core.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by jameshart on 6/1/20.
 */

@RestController
public class V1InstanceController {

    private static final Logger logger = LoggerFactory.getLogger(V1InstanceController.class);
    Helper helper = new Helper();

    private final InstanceRepository instances;

    public V1InstanceController(InstanceRepository instances) {
        this.instances = instances;
    }

    //Create Instance
    @RequestMapping(value = "/api/v1/instance", method = RequestMethod.POST, produces = "application/json")
    public Instance CreateInstance(
            @RequestParam("address") String address,
            @RequestParam("callsign") String callSign) {

        String createdAt = helper.CreatedAt();
        Instance newInstance = new Instance(address, createdAt , callSign);
        instances.save(newInstance);

        return newInstance;
    }


    //Get all Instances
    @RequestMapping(value = "/api/v1/instance/all", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Instance> GetAllInstances() {
        ArrayList<Instance> theInstances = new ArrayList<>();

        for (Instance instance : instances.findAll()) {
            Instance addInstance = instance;
            theInstances.add(addInstance);
        }

        return theInstances;
    }



    @RequestMapping(value = "/api/v1/instance/{id}", method = RequestMethod.GET, produces = "application/json")
    public Instance FindInstance(@PathVariable("id") long id) {

        Instance theInstance = null;

        try {
            theInstance  = instances.findByid(id);
            return theInstance;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            Instance badInstance = new Instance("no", "no", "no");
            return badInstance;
        }

    }

    //Delete Instance
    @RequestMapping(value = "/api/v1/instance/delete/{callsign}", method= RequestMethod.DELETE, produces = "application/json")
    public String DeleteLink(@PathVariable("unique") String callSign) {

        Instance instanceToDelete = null;

        try {
            instanceToDelete  = instances.findByCallSign(callSign);
        } catch (NullPointerException e) {
            return e.toString();
        }

        instances.delete(instanceToDelete);

        return "deleted";
    }


}
