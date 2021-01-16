package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.AdressData;
import com.engthesis.demo.dao.Marker;
import com.engthesis.demo.dao.ResponseTransfer;
import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.manager.AdressManager;
import com.engthesis.demo.manager.UserManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adress")
public class AdressController {

    private final AdressManager adressManager;
    private final UserManager userManager;

    @Autowired
    public AdressController(AdressManager adressManager, UserManager userManager) {
        this.adressManager = adressManager;
        this.userManager = userManager;
    }

    @PutMapping(value = "/adressPatch")
    public ResponseTransfer adress(
            @RequestBody AdressData inputData) throws ObjectNotFoundException, InterruptedException, URISyntaxException, JSONException, IOException {
                adressManager.updateAdress(inputData);
        return new ResponseTransfer("Successfully updated!");

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/adress")
    public Optional<Adress> getAdress() throws EmailExistException {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user =userManager.getUserByEmail(email);
            return adressManager.getAdressUser(user);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/markers")
    public List<Marker> getMarkers() throws ObjectNotFoundException {
            return adressManager.allGeoAdresses();

    }


}
