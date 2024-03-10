package com.iam.demo.web.services;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    @PreAuthorize("hasAuthority('SCOPE_myapi')")
    @GetMapping("/api/rest/vegetables")
    public List<String> getVegetabkes() {
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthorizedClient authorizedClient =
            this.authorizedClientService.loadAuthorizedClient(
                auth.getAuthorizedClientRegistrationId(),
                auth.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        String token = accessToken.getTokenValue();
        System.out.println("--->\n---> TOKEN IS: "+token+"\n--->");
        Authentication authFromCtx = SecurityContextHolder.getContext().getAuthentication();

        try {
            URL nextCall = new URL("http://localhost:8080/api/rest/fruits");
            HttpURLConnection conn = (HttpURLConnection) nextCall.openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Bearer "+token);
            String response = new String(conn.getInputStream().readAllBytes());
            System.out.println("Next call response is: "+response);
        }
        catch (Exception e) {
            System.out.println("Next call failed: "+e.getMessage());
        }
        finally {
            SecurityContextHolder.getContext().setAuthentication(authFromCtx);
        }
        
        return List.of("carrot", "potato", "tomato");
    }

    @PreAuthorize("hasAuthority('SCOPE_myapi')")
    @GetMapping("/api/rest/fruits")
    public List<String> getFruits() {
        return List.of("apple", "orange", "banana");
    }
}
