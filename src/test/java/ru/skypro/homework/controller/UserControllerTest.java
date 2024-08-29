package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.db.repository.UserRepository;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private UserRepository userRepository;

    @Test
    void setPassword() {
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void testUpdateUser() {
    }

    @Test
    void updateUserImage() {
    }

    @Test
    void getImages() {
    }
}