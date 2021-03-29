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
import java.util.List;


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

//        validateDuplicateUser(userDto);

        userRepository.save(User.builder()
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword())
                .build());
    }

//    private void validateDuplicateUser(UserDto userDto) {
//        User findMembers = userRepository.findByUserEmail(userDto.getEmail());
//        if (findMembers != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다!");
//        }
//    }


    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public User findOneByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }
}