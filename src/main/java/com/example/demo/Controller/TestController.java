package com.example.demo.Controller;

import com.example.demo.DTO.JwksUriMetadata;
import com.example.demo.DTO.JsonWebKey;
import com.example.demo.DTO.SigningKeyMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @GetMapping("api/test")
    public @ResponseBody
    ResponseEntity getShiftList() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String openIdEndPoint = "https://login.microsoftonline.com/b3b9cfc4-0719-45ae-9178-946ebe5ac23f/.well-known/openid-configuration";
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<SigningKeyMetadata> response = restTemplate.getForEntity(openIdEndPoint, SigningKeyMetadata.class);
        if(response.getBody() != null){
            String jwksUri = response.getBody().getJwks_uri();
            ResponseEntity<String> keys = restTemplate.getForEntity(jwksUri, String.class);
            Gson gson = new Gson();
            JwksUriMetadata jwksUriMetadata  = gson.fromJson(keys.getBody(), JwksUriMetadata.class);
            for (JsonWebKey key :
                    jwksUriMetadata.keys) {
                if(key.kid.equals("nOo3ZDrODXEK1jKWhXslHR_KXEg")){

                }
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
