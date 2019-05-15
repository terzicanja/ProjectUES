package rs.ac.uns.ftn.ues.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.entity.User;
import rs.ac.uns.ftn.ues.entity.UserTokenState;
import rs.ac.uns.ftn.ues.security.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ues.security.TokenHelper;
import rs.ac.uns.ftn.ues.service.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
	
	@Autowired
	TokenHelper tokenHelper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws AuthenticationException, IOException {
		
		System.out.println("ovo je nesto msm ko zeli da se uloguje: "+authenticationRequest);
		final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
		
		// Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        System.out.println("pokusao da se uloguje: "+authentication);
        
        // Kreiraj token
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken(user.getUsername());

        // Vrati token kao odgovor na uspesno autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jws));
	
	
	}
	
	
	
	
	

}
