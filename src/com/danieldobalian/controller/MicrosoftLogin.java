package com.danieldobalian.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/* OAuth Imports */
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.kevinsawicki.http.HttpRequest;

@Controller
public class MicrosoftLogin {
	
    /* The context and data around our Auth */
    private OAuth20Service service;
    
    private static final String PROTECTED_RESOURCE_URL = "https://graph.microsoft.com/v1.0/me/";
    private final String apiKey = "Register your app at https://identity.microsoft.com/#/appList";						
    private final String apiSecret = "Register your app at https://identity.microsoft.com/#/appList";
    
	@RequestMapping(value = "/login")
	public String printHello (WebRequest request, HttpSession session) {

		/* Configure the context of your Auth */
        service = new ServiceBuilder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .scope("offline_access User.Read")
                .callback("http://localhost:8080/AADv2JavaWebApp/")
                .build(AADv2Api.instance());

        /* Generate a state */
        String state = UUID.randomUUID().toString();
        session.setAttribute("state", state);
   
        /* Generate the Authorization Url */
        final String authorizationUrl = service.getAuthorizationUrl(null) + "&state=" + state;
        System.out.println("Auth Code: " + authorizationUrl);
        
        /* Redirect to the Authorization URL to obtain Authz Code */
        return "redirect:" + authorizationUrl;
	}
         
     /* Callback after obtaining the Auth Code from Azure AD */
     @RequestMapping(value={""}, method = RequestMethod.GET)
     public String callback(WebRequest request, ModelMap model,
    		 @RequestParam(value="oauth_token", required=false) String oauthToken, 
    		 @RequestParam(value="code", required=false) String oauthVerifier, 
    		 @RequestParam(value="state") String state, HttpSession session) throws IOException {
    	
    	 /* Verify the State has not been changed */
    	 if (!state.equals(session.getAttribute("state"))) {
    		 model.addAttribute("message", "Authorization Denied: State has been modified.");
    		 return "login";
    	 }
    	 
        /* Trade the Request Token and Verifier for the Access Token */
        final OAuth2AccessToken tokens = service.getAccessToken(oauthVerifier);
        
        /* Print out some of the data being passed around */
        System.out.println("\n\n----Data Returned from Auth Code exchange--------\n" + tokens.toString());
        System.out.println("Access Token: " + tokens.getAccessToken().toString());

        /* Construct Http Request to the Microsoft Graph /me endpoint using Access Token */
        HttpRequest req = new HttpRequest(PROTECTED_RESOURCE_URL, HttpRequest.METHOD_GET);
        req = req.authorization("Bearer " + tokens.getAccessToken()).acceptJson();
        
        /* Check the response */
        String meEndpoint = "";
        if(req.ok()) {
        	meEndpoint = req.body().toString();
        	System.out.println("\n\n------Response---------\n" + meEndpoint);
        } else {
        	System.out.println("Http Request failed");
        }
          
        /* Stick the response into UI */
        model.addAttribute("message", meEndpoint);
		return "login";
	}
}
