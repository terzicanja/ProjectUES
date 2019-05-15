package rs.ac.uns.ftn.ues.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.ac.uns.ftn.ues.security.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.ues.security.TokenAuthenticationFilter;
import rs.ac.uns.ftn.ues.security.TokenHelper;
import rs.ac.uns.ftn.ues.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
//proveriti sta zagrada znaci
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    //Neautorizovani pristup zastcenim resursima
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    
    //Definisemo nacin autentifikacije
    //Svaki 
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( jwtUserDetailsService )
            .passwordEncoder( passwordEncoder() );
    }

    @Autowired
    TokenHelper tokenHelper;

    
    //Definisemo prava pristupa odredjenim URL-ovima
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("jel ulazi vamo uopste");
    	
    	http.formLogin()
    	.loginPage("/login.html")
//    	.loginProcessingUrl("/perform_login")
    	//true uvek salje na tu stranicu kad se uloguje
    	//a false salje na onu stranicu kojoj je hteo da pristupi pa ga poslala na auth
        .defaultSuccessUrl("/index.html", false)
        .failureUrl("/login.html?error=true");
    	
        http
        		//komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                //za neautorizovane zahteve posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
                .authorizeRequests()
                //svim korisnicima dopusti da pristupe putanjama /auth/**
                .antMatchers("/auth/**").permitAll()
                
                .and()
                
                //svaki zahtev mora biti autorizovan
//                .anyRequest().authenticated().and()
                
                
//                .formLogin()
//                .loginPage("/html/login.html")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll().and()
                //presretni svaki zahtev filterom
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);
//
        http.csrf().disable();
    }


    //Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/login"
        );
        web.ignoring().antMatchers(
                HttpMethod.POST,"/api/users/create"
        );
        web.ignoring().antMatchers(
                HttpMethod.POST,"/api/users/photo"
        );
        web.ignoring().antMatchers(
                HttpMethod.GET,"/api/demo/**"
        );
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.css",
                "/**/*.js"
            );

    }
    
    
    
    
    

}
