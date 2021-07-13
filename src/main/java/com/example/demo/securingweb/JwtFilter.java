//package com.example.demo.securingweb;
//
//import com.example.demo.DTO.JwksUriMetadata;
//import com.example.demo.DTO.JsonWebKey;
//import com.example.demo.DTO.SigningKeyMetadata;
//import com.google.gson.Gson;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.SignatureException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Base64;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//            // Lấy jwt từ request
//            String jwt = getJwtFromRequest(request);
//            isValidateToken(jwt);
////            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
////                // Lấy id user từ chuỗi jwt
////                Long userId = tokenProvider.getUserIdFromJWT(jwt);
////                // Lấy thông tin người dùng từ id
////                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
////                if(userDetails != null) {
////                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
////                    UsernamePasswordAuthenticationToken
////                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////
////                    SecurityContextHolder.getContext().setAuthentication(authentication);
////                }
////            }
////        } catch (Exception ex) {
////            log.error("failed on set user authentication", ex);
////        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    private Jws<Claims> isValidateToken(String jwtToken){
//        SigningKeyResolver signingKeyResolver = new SigningKeyResolver("sda");
//        Jws<Claims> claims;
//        try{
//            claims = Jwts.parserBuilder()
//                    .setSigningKeyResolver(signingKeyResolver)
//                    .build().parseClaimsJws(jwtToken);
//            claims.getSignature();
//        }catch (SignatureException ex){
//            throw new JwtValidationException("JWT validation failed: invalid signature", ex);
//        }
//
//
////        Jws<Claims> claimsJws= Jwts.parserBuilder().setSigningKey().parseClaimsJws(jwtToken);
////        String[] tokenChunks = jwtToken.split("\\.");
////        Base64.Decoder decoder = Base64.getDecoder();
////
////        String header = new String(decoder.decode(tokenChunks[0]));
////        String payload = new String(decoder.decode(tokenChunks[1]));
////        tokenChunks[2] = tokenChunks[0]+"."+tokenChunks[1];
////        RestTemplate restTemplate = new RestTemplate();
////        String fooResourceUrl = "https://login.microsoftonline.com/b3b9cfc4-0719-45ae-9178-946ebe5ac23f/.well-known/openid-configuration";
////        HttpHeaders headers = new HttpHeaders();
////        ResponseEntity<SigningKeyMetadata> result = restTemplate.getForEntity(fooResourceUrl, SigningKeyMetadata.class);
////        if(result.getBody() != null){
////            String jwksUri = result.getBody().getJwks_uri();
////            ResponseEntity<String> keys = restTemplate.getForEntity(jwksUri, String.class);
////            Gson gson = new Gson();
////            JwksUriMetadata jwksUriMetadata  = gson.fromJson(keys.getBody(), JwksUriMetadata.class);
////            for (JsonWebKey key :
////                    jwksUriMetadata.keys) {
////                if(key.kid.equals("nOo3ZDrODXEK1jKWhXslHR_KXEg")){
////
////                }
////            }
////        }
//        return claims;
//    }
//}
