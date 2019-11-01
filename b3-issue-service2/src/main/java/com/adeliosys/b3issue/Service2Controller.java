package com.adeliosys.b3issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Service2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Service2Controller.class);

    @GetMapping("/date")
    public String getDate() {
        logger.info("Executing 'getDate'");
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // NOOP
        }
        return new Date().toString();
    }
}
