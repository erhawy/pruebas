package com.travelgatex.demo.service;

import com.travelgatex.demo.service.data.AtalayaMealsResponse;
import com.travelgatex.demo.service.data.AtalayaResponse;
import com.travelgatex.demo.service.data.AtalayaRoomsResponse;
import com.travelgatex.demo.service.data.HotelesAtalaya;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientAtalayaService {

    private static final String URLHOTELS =  "http://www.mocky.io/v2/5e4a7e4f2f00005d0097d253";
    private static final String URLROOMS = "https://run.mocky.io/v3/132af02e-8beb-438f-ac6e-a9902bc67036";
    private static final String URLMEAL = "http://www.mocky.io/v2/5e4a7e282f0000490097d252";

    public AtalayaResponse obtenerHoteles(){
        WebClient client = WebClient.create(URLHOTELS);
        return client.get()
                .retrieve()
                .bodyToFlux(AtalayaResponse.class).blockFirst();
    }

    public AtalayaRoomsResponse obtenerHabitaciones(){
        WebClient client = WebClient.create(URLROOMS);
        return client.get()
                .retrieve()
                .bodyToFlux(AtalayaRoomsResponse.class).blockFirst();
    }

    public AtalayaMealsResponse obtenerRegimenes(){
        WebClient client = WebClient.create(URLMEAL);
        return client.get()
                .retrieve()
                .bodyToFlux(AtalayaMealsResponse.class).blockFirst();
    }

}
