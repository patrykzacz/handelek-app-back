package com.engthesis.demo.manager;


import com.engthesis.demo.dao.AdressData;
import com.engthesis.demo.dao.Marker;
import com.engthesis.demo.dao.UserData;
import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.Group;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.exception.UserNotFoundException;
import com.engthesis.demo.repository.AdressRepository;
import com.engthesis.demo.repository.GroupRepository;
import com.engthesis.demo.repository.UserRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdressManager {


    private final AdressRepository adressRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;
    private final GroupManager groupManager;
    private final GroupRepository groupRepository;

    @Autowired

    public AdressManager(AdressRepository adressRepository, UserManager userManager, UserRepository userRepository, GroupManager groupManager, GroupRepository groupRepository) {
        this.adressRepository = adressRepository;
        this.userManager = userManager;
        this.userRepository = userRepository;
        this.groupManager = groupManager;
        this.groupRepository = groupRepository;
    }

    public void addCleanAdress(String email){
        User user= userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Adress adress = new Adress();
        adress.setLat(null);
        adress.setLang(null);
        adress.setUser(user);
        adressRepository.save(adress);
    }
    public void updateAdress(AdressData inputData) throws InterruptedException, URISyntaxException, JSONException, IOException, ObjectNotFoundException {
        User user= userManager.getLoggedUser();
        Adress adress = adressRepository.findById(user.getId()).orElseThrow(ObjectNotFoundException::new);
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

        return adressRepository.allMarkers().stream()
                .filter(m->Objects.nonNull(m.getLat()))
                .filter(m->Objects.nonNull(m.getLang()))
                .collect(Collectors.toList());
    }

    public Set<Marker> groupMarkers(Long id){
        Set<UserData> users = groupManager.getGroupUsers(id);
        Set<Marker> markers = new HashSet<>();
        for (UserData user: users){
            User x= userRepository.findByEmail(user.getEmail()).orElseThrow();
            markers.add(adressRepository.markerData(x.getId()).orElseThrow(ObjectNotFoundException::new));
        }
        return markers.stream().filter(m->Objects.nonNull(m.getLat()))
                .filter(m->Objects.nonNull(m.getLang()))
                .collect(Collectors.toSet());
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
