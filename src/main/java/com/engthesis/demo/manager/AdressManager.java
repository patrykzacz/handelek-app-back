package com.engthesis.demo.manager;


import com.engthesis.demo.dao.AdressData;
import com.engthesis.demo.dao.Marker;
import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.AdressNotFoundException;
import com.engthesis.demo.exception.UserNotFoundException;
import com.engthesis.demo.repository.AdressRepository;
import com.engthesis.demo.repository.UserRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdressManager {


    private final AdressRepository adressRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;

    @Autowired

    public AdressManager(AdressRepository adressRepository, UserManager userManager, UserRepository userRepository) {
        this.adressRepository = adressRepository;
        this.userManager = userManager;
        this.userRepository = userRepository;
    }

    public void addCleanAdress(String email){
        User user= userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Adress adress = new Adress();
        adress.setLat("0");
        adress.setLang("0");
        adress.setUser(user);
        adressRepository.save(adress);
    }
    public void updateAdress(AdressData inputData) throws InterruptedException, URISyntaxException, JSONException, IOException {
        User user= userManager.getLoggedUser();

        Adress adress = adressRepository.findById(user.getId()).orElseThrow();
        adress.setLine_1(inputData.getLine_1());
        adress.setLine_2(inputData.getLine_2());
        adress.setLine_3(inputData.getLine_3());
        adress.setCity(inputData.getCity());
        adress.setZipCode(inputData.getZipCode());
        String query = inputData.getLine_1() + " "+ inputData.getLine_2() + inputData.getLine_3() + ","+ inputData.getZipCode()+" "+ inputData.getCity();
        List<String> geo= getGeoCord(query);
        adress.setLang(geo.get(1));
        adress.setLat(geo.get(0));
        adressRepository.save(adress);
    }


    public Optional<Adress> getAdressUser(User user )   {
        Long id =user.getId();
        return adressRepository.findByUserId(id);

    }

    public List<Marker> allGeoAdresses(){
        return adressRepository.allMarkers();
    }

    public List<String> getGeoCord(String query ) throws IOException, InterruptedException, URISyntaxException, JSONException {

        HttpClient client = HttpClient.newHttpClient();
        URI builder = new URIBuilder("https://nominatim.openstreetmap.org/search").
                setParameter("adressdetails", "0").setParameter("q", query).setParameter("format","json").setParameter("limit","1").build();
        ;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(builder)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body(); ;
        JSONArray obj = new JSONArray(jsonString);
        String latitude="";
        String longitude="";
        for (int i = 0; i < obj.length(); i++)
        {
             latitude = obj.getJSONObject(i).getString("lat");
             longitude = obj.getJSONObject(i).getString("lon");

        }
        List<String> list = new ArrayList<>();
        list.add(latitude);
        list.add(longitude);
        return list;
    }

}
