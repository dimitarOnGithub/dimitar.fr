package me.sudosuwinter.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.usersRepository.findByUsername(username);
        return user
                .map(u -> new AuthenticatedUser(u.getUsername(), u.getPassword()))
                .orElseThrow(() -> UsernameNotFoundException.fromUsername(username));
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        this.usersRepository.save(user);
    }
}
