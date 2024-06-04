package cz.engeto.springproject;

import cz.engeto.springproject.entity.User;

public class UserMapper {

        public static UserDTO toDTO(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            userDTO.setPersonId(user.getPersonId());
            return userDTO;
        }

        public static User toEntity(UserDTO userDTO) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setRole(userDTO.getRole());
            user.setPersonId(userDTO.getPersonId());
            return user;
        }
    }


