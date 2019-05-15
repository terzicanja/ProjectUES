package rs.ac.uns.ftn.ues.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.ues.entity.User;
import rs.ac.uns.ftn.ues.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = (User) userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Nije pronadjen korisnik sa korisnickim imenom: '%s'.", username));
		}else {
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			return user;
		}
	}
	
	
	
	//Funkcija pomocu koje korisnik menja svoju lozinku
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '"+ email + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");

            return;
        }

        LOGGER.debug("Changing password for user '"+ email + "'");

        User user = (User) loadUserByUsername(email);

        //pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
        //ne zelimo da u bazi cuvamo lozinke u plain text formatu
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

}
