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
import io.swagger.annotations.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @ApiOperation(value = "Change adress data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\""),
            @ApiResponse(code = 401, message = "Permission Denied"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PutMapping(value = "/adressPatch")
    public ResponseTransfer adress(
            @RequestBody AdressData inputData) throws ObjectNotFoundException, InterruptedException, URISyntaxException, JSONException, IOException {
                adressManager.updateAdress(inputData);
        return new ResponseTransfer("Successfully updated!");

    }


    @ApiOperation(value = "Get logged user adress", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Data downloaded Succesfully"),
            @ApiResponse( code = 404, message = "Anno Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @GetMapping(value ="/adress")
    public Optional<Adress> getAdress() throws EmailExistException, PermissionDeniedDataAccessException {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user =userManager.getUserByEmail(email);
            return adressManager.getAdressUser(user);

    }

    @ApiOperation(value = "Get All Markers")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Object Not Found"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value ="/markers")
    public List<Marker> getMarkers() throws ObjectNotFoundException {
            return adressManager.allGeoAdresses();

    }

    @ApiOperation(value = "Get Group Markers")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Object Not Found"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value ="/groupMarkers/{id}")
    public Set<Marker> getGroupMakers(
            @ApiParam(value = "Required group id", required = true)
            @PathVariable Long id
    ) throws ObjectNotFoundException {
        return adressManager.groupMarkers(id);

    }


}
