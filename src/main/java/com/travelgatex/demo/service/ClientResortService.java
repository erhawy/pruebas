package com.travelgatex.demo.service;

import com.travelgatex.demo.service.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientResortService {

    private static final String URLHOTELS =  "http://www.mocky.io/v2/5e4e43272f00006c0016a52b";
    private static final String URLMEAL = "http://www.mocky.io/v2/5e4a7dd02f0000290097d24b";

    public ResortsHotels obtenerHoteles(){
        WebClient client = WebClient.create(URLHOTELS);
        return client.get()
                .retrieve()
                .bodyToFlux(ResortsHotels.class).blockFirst();
    }


    public RegimenesResort obtenerRegimenes(){
        WebClient client = WebClient.create(URLMEAL);
        return client.get()
                .retrieve()
                .bodyToFlux(RegimenesResort.class).blockFirst();
    }

}
