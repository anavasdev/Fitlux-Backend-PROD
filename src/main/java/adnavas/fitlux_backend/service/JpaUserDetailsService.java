package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario userOptional = userRepository.findByUsername(username);

        if (userOptional == null) {
            throw new UsernameNotFoundException(("Username %s no existe"));
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userOptional.getRole()));


        return new org.springframework.security.core.userdetails.User(userOptional.getUsername(),
                userOptional.getPassword(),
                userOptional.isEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
