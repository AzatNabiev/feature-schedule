package ru.waveaccess.features.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.features.dto.AuthDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.exceptions.NoSuchRoleException;
import ru.waveaccess.features.exceptions.NoSuchUserException;
import ru.waveaccess.features.forms.AuthForm;
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.forms.UserForm;
import ru.waveaccess.features.models.Role;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.RoleJpaRepository;
import ru.waveaccess.features.repositories.UserJpaRepository;
import ru.waveaccess.features.security.jwt.JwtProvider;

import java.util.Optional;

@Service
public class UserService{

    private UserJpaRepository userJpaRepository;
    private RoleJpaRepository roleJpaRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    @Autowired
    public void setUserServiceImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository,
                           PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public UserDto registerUser(RegistrationForm registrationForm) {
        User user = User.builder().login(registrationForm.getLogin()).name(registrationForm.getName())
                .password(passwordEncoder.encode(registrationForm.getPassword())).build();
        userJpaRepository.save(user);
        return UserDto.fromModelToDto(user);
    }

    public AuthDto authenticateUser(AuthForm authForm) {
        User user;
        Optional<User> userOptional = userJpaRepository.findByLogin(authForm.getLogin());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (passwordEncoder.matches(authForm.getPassword(), user.getPassword())) {
                String token = jwtProvider.generateToken(authForm.getLogin());
                return AuthDto.builder().token(token).build();
            } else {
                throw new IllegalArgumentException("Authentication rejected");
            }
        } else {
            throw new IllegalArgumentException("No such user");
        }
    }

    public UserDto giveUserGrade(UserForm userForm) {
        User user;
        Optional<User> userOptional = userJpaRepository.findById(Long.parseLong(userForm.getUserId()));
        Optional<Role> roleOptional = roleJpaRepository.findByUserRoles(userForm.getGrade());
        if (userOptional.isPresent()) {
            if (roleOptional.isPresent()) {
                user = userOptional.get();
                user.setRole(roleOptional.get());
                userJpaRepository.save(user);
            } else {
                throw new NoSuchRoleException("No such role");
            }
        } else {
            throw new NoSuchUserException("No such user");
        }
        return UserDto.fromModelToDto(user);
    }

    @Transactional
    public User findUserByLogin(String login) {
        User user;
        Optional<User> userOptional = userJpaRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new NoSuchUserException("No such user");
        }
        return user;
    }

}
