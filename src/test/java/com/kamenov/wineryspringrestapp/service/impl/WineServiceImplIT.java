package com.kamenov.wineryspringrestapp.service.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock.*;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import com.kamenov.wineryspringrestapp.service.WineService;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.http.client.methods.RequestBuilder.get;


@SpringBootTest
@EnableWireMock(@ConfigureWireMock(name = "wine-service"))
public class WineServiceImplIT {


    @InjectWireMock("wine-service")
    private WireMockServer wireMockServer;
    @Autowired
    private WineService service;

    @BeforeEach
    void setUp(){




    }

    @Test
    void testFetchWInes(){
     //   wireMockServer.stubFor(get("/test-wines"))


        WIneViewModel wIneViewModel = service.findAllWinesView().get(0);
        Assertions.assertEquals("Wine", wIneViewModel.getName());
        Assertions.assertEquals("Wine", wIneViewModel.getDescription());


    }

}
