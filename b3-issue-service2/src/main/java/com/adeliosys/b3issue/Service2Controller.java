package com.adeliosys.b3issue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Service2Controller {

    @GetMapping("/date")
    public String getDate() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // NOOP
        }
        return new Date().toString();
    }
}
