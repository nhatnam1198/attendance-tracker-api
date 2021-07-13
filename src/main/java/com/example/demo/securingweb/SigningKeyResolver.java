//package com.example.demo.securingweb;
//
//import com.example.demo.DTO.JsonWebKey;
//import com.example.demo.DTO.JwksUriMetadata;
//import com.example.demo.DTO.SigningKeyMetadata;
//import com.google.gson.Gson;
//import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.SingleKeyResolver;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwsHeader;
//import io.jsonwebtoken.SigningKeyResolverAdapter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigInteger;
//import java.security.Key;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.spec.RSAPublicKeySpec;
//import java.util.Base64;
//
//public class SigningKeyResolver  extends SigningKeyResolverAdapter {
//    private JwksUriMetadata keySet;
//    SigningKeyResolver(String tentanId){
//        keySet = getSigningKeys(tentanId);
//    }
//    private JwksUriMetadata getSigningKeys(String tentanId){
//        tentanId = "b3b9cfc4-0719-45ae-9178-946ebe5ac23f";
//        RestTemplate restTemplate = new RestTemplate();
//        String openIdEndPoint = "https://login.microsoftonline.com/b3b9cfc4-0719-45ae-9178-946ebe5ac23f/.well-known/openid-configuration";
//        HttpHeaders headers = new HttpHeaders();
//        ResponseEntity<SigningKeyMetadata> response = restTemplate.getForEntity(openIdEndPoint, SigningKeyMetadata.class);
//        if (response.getBody() != null) {
//            String jwksUri = response.getBody().getJwks_uri();
//            ResponseEntity<String> keys = restTemplate.getForEntity(jwksUri, String.class);
//            Gson gson = new Gson();
//            JwksUriMetadata jwksUriMetadata  = gson.fromJson(keys.getBody(), JwksUriMetadata.class);
//            return jwksUriMetadata;
//        }
//        return null;
//    }
//
//    @Override
//    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
//        String tokenKeyId = jwsHeader.getKeyId();
//        for(JsonWebKey key: keySet.getKeys()){
//            if(key.getKid().equalsIgnoreCase(tokenKeyId)){
//                return generatePublicKey(key);
//            }
//        }
//        return null;
//    }
//
//    private PublicKey generatePublicKey(JsonWebKey key){
//        try {
//            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getN()));
//            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getE()));
//
//            RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(modulus, exponent);
//            KeyFactory factory = KeyFactory.getInstance("RSA");
//            return factory.generatePublic(publicSpec);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new JwtValidationException("Public key can not be generated");
//        }
//    }
//}
