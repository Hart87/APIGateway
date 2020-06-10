package com.gateway.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jameshart on 6/9/20.
 */
public class Helper {

    public String CreatedAt() {

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String created = df.format(dateobj);

        return created;
    }
}
