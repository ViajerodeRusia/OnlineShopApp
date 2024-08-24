package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.dto.UserDto;
import ru.skypro.homework.db.entity.CreatedByUser;
import ru.skypro.homework.db.entity.User;
import ru.skypro.homework.db.repository.UserRepository;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    // Разрешить доступ только админу или владельцу записи
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @PreAuthorize("#id == authentication.principal.id")
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateEntityFromDto(userDto, user);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    // Только пользователи с ролью ADMIN могут выполнить этот метод или сам владелец записи
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Метод возвращает текущего авторизованного пользователя
     *
     * @return User         Текущий авторизованный пользователь
     */
    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findByEmail(name);
    }

    /**
     * Метод проверяет соответсвие ID текущего авторизованного пользователя с ID создателя сущности
     *
     * @return boolean
     */
    public boolean hasPermission(CreatedByUser entity) {
        User currentUser = getCurrentUser();
        var currentUserId = currentUser.getId();
        var authorId = entity.getUser().getId();

        return currentUserId.equals(authorId);
    }

}
