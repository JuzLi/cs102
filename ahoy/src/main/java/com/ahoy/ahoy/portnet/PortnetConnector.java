package com.ahoy.ahoy.portnet;

import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class PortnetConnector {

    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private VoyageService voyageService;

    private static String apiKey;

    @Value("${portnet.apikey}")
    public void setApiKey(String value)
    {
        PortnetConnector.apiKey = value;
    }

    public JsonArray getUpdate(String dateFrom, String dateTo) {

        List<Vessel> vessels = new ArrayList<>();

        String url = "https://api.portnet.com/vsspp/pp/bizfn/berthingSchedule/retrieveByBerthingDate/v1.2";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Apikey", apiKey);

        Map<String, Object> map = new HashMap<>();
        map.put("dateFrom", dateFrom);
        map.put("dateTo", dateTo);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            JsonArray postResults = (JsonArray) jsonObject.get("results").getAsJsonArray();
            return postResults;
        }
        return null;

    }

    public JsonObject getVesselDetails(Voyage voyage) {
        String url = "https://api.portnet.com/extapi/vessels/predictedbtr/?vslvoy=";


        String vslvoy = voyageService.generateVslvoy(voyage);
        String getURL = url + vslvoy;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Apikey", apiKey);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(getURL, HttpMethod.GET, entity, String.class);


        JsonObject j = new Gson().fromJson(response.getBody(),JsonObject.class);
        return j;
    }


}

