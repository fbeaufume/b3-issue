package com.adeliosys.b3issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Service1Controller {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/date")
    public String getDate() {
        return restTemplate.getForObject("http://localhost:8082/date", String.class);
    }
}
