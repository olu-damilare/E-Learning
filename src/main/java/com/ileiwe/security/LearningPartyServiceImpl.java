package com.ileiwe.security;

import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.repository.LearningPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LearningPartyServiceImpl implements UserDetailsService {


    @Autowired
    private LearningPartyRepository learningPartyRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            LearningParty user = learningPartyRepository.findByEmail(s);
            if(user == null){
                throw new UsernameNotFoundException("User with email does not exist");
            }

        List<SimpleGrantedAuthority> authorities = getAuthorities(user);

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(LearningParty user) {
        return user.getAuthorities().stream()
                .map((authority) -> new SimpleGrantedAuthority(authority.getAuthority().name()))
                .collect(Collectors.toList());
    }

}
