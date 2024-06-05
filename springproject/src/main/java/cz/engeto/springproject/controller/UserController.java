package cz.engeto.springproject.controller;

import cz.engeto.springproject.UserDTO;
import cz.engeto.springproject.UserMapper;
import cz.engeto.springproject.UserRequest;
import cz.engeto.springproject.entity.User;
import cz.engeto.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserRequest userRequest) {
        try {
            User user = new User();
            user.setUsername(userRequest.getName() + " " + userRequest.getSurname());
            user.setPassword(generateRandomPassword());
            user.setRole("user");
            user.setPersonId(userRequest.getPersonId());
            user.setUuid(UUID.randomUUID().toString());

            // Uložení uživatele do databáze
            User savedUser = userService.createUser(user);

            // Mapování na UserDTO a vrácení v odpovědi
            UserDTO responseDTO = UserMapper.toDTO(savedUser);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String generateRandomPassword() {
                return "random_password";
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(user -> {
            UserDTO userDTO = UserMapper.toDTO(user);
            return ResponseEntity.ok(userDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }




    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }




    @GetMapping("/{personId}")
    public ResponseEntity<UserDTO> getUserByPersonId(@PathVariable String personId) {
        User user = userService.findByPersonId(personId);
        if (user != null) {
            UserDTO userDTO = UserMapper.toDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User existingUser = userService.getUserById(id).orElse(null);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }


            existingUser.setUsername(userDTO.getUsername());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setRole(userDTO.getRole());


            User updatedUser = userService.updateUser(id, existingUser);


            UserDTO updatedUserDTO = UserMapper.toDTO(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }








}





