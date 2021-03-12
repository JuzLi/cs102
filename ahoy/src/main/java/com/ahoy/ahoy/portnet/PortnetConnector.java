package com.ahoy.ahoy.portnet;

import com.ahoy.ahoy.repo.VesselRepository;
<<<<<<< HEAD
<<<<<<< HEAD
import com.ahoy.ahoy.vessel.Vessel;
import com.google.gson.*;
=======
=======
>>>>>>> parent of acadcd2 (save to sql v1.1 a lot errors)
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
>>>>>>> parent of acadcd2... save to sql v1.1 a lot errors
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

<<<<<<< HEAD
<<<<<<< HEAD
    public List<Vessel> getUpdate(String dateFrom, String dateTo) {

        List<Vessel> vessels = new ArrayList<>();

=======
    public static void getUpdate(String dateFrom, String dateTo) {
>>>>>>> parent of acadcd2... save to sql v1.1 a lot errors
=======
    public static void getUpdate(String dateFrom, String dateTo) {
>>>>>>> parent of acadcd2 (save to sql v1.1 a lot errors)
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
<<<<<<< HEAD
<<<<<<< HEAD
//            System.out.println(vesselArray);
            Gson gson = new Gson();

            for (int i = 0; i < vesselArray.size(); i++) {
                JsonObject j = vesselArray.get(i).getAsJsonObject();
                Vessel v = new Vessel(j.get("abbrVslM").getAsString(),j.get("fullVslM").getAsString());
                vessels.add(v);
                vesselRepository.save(v);
            }

//            for ( int i=0; i< vesselArray.size(); i++){
//                Vessel v = new Vessel();
//                v.setFullName(vesselArray.get(i).getAsString());
//                v.setLongName(vesselArray.get(i).getAsString());
//                v.setShortName(vesselArray.get(i).getAsString());
//
//                vessels.add(v);
//            }
            return vessels;
            }
        return null;
=======
            vesselRepository.save(vesselArray);
>>>>>>> parent of acadcd2... save to sql v1.1 a lot errors
=======
            vesselRepository.save(vesselArray);
>>>>>>> parent of acadcd2 (save to sql v1.1 a lot errors)
        }
    }
}
