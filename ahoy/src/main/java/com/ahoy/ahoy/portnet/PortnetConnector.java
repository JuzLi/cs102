package com.ahoy.ahoy.portnet;

import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    private static String apiKey;

    @Value("${portnet.apikey}")
    public void setApiKey(String value)
    {
        PortnetConnector.apiKey = value;
    }

    public static List<Vessel> getUpdate(String dateFrom, String dateTo) {

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
            JsonArray vesselArray = (JsonArray) jsonObject.get("results").getAsJsonArray();
            Gson gson = new Gson();

//            for (int i = 0; i < vesselArray.size(); i++) {
//                Vessel v = gson.fromJson(vesselArray.get(i).toString(), Vessel.class);
//                vessels.add(v);
//            }

            for ( int i=0; i< vesselArray.size(); i++){
                Vessel v = new Vessel();
                v.setFullName(vesselArray.get(i).getAsString());
                v.setLongName(vesselArray.get(i).getAsString());
                v.setShortName(vesselArray.get(i).getAsString());

                vessels.add(v);
            }
            return vessels;
            }
        return null;
        }

}

