package com.controller;

import com.service.OpcUAScadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OpcUAScadaRead implements ApplicationRunner {


    @Autowired
    @Qualifier("opcUAScadaService")
    private OpcUAScadaService opcUAScadaService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        opcUAScadaService.opcUASubscription();
    }
}
