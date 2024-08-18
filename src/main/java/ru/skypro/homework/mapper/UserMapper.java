package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.controller.dto.UserDto;
import ru.skypro.homework.db.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}
