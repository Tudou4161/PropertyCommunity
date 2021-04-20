package com.service.PropertyService.Service;
import com.service.PropertyService.Repository.UserRepository;
import com.service.PropertyService.domain.User;
import com.service.PropertyService.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    @Transactional
    public void join(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        userDto.setPassword(encoder.encode(userDto.getPassword()));

        userRepository.save(User.builder()
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .name(userDto.getName())
                .build());
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public User findOneByUsernameAndPhoneNumber(String userEmail, String phoneNumber) {
        return userRepository.findByUsernameAndPhoneNumber(userEmail, phoneNumber);
    }

    @Transactional
    public void deleteUser(Long userId) {

        userRepository.delete(userId);
    }

    public String getAuthCode() {
        Random random = new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호

        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;

        return key;
    };
}