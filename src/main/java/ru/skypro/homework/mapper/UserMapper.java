package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Service
public class UserMapper {


    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setRoleDto(user.getRoleDto());
        return userDto;
    }

    public User toUser(UserDto userDto) {
        User user=new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setRoleDto(userDto.getRoleDto());
        return user;
    }

    public User toRegisterUser(RegisterDto registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setRoleDto(registerDTO.getRoleDto());
        return user;
    }
}
