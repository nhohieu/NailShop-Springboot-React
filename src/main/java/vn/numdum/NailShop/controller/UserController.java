package vn.numdum.NailShop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.service.UserService;
import vn.numdum.NailShop.service.error.IdInvalidException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> CreateNewUser(@RequestBody User postManuser) {
        User hieuUSer = this.userService.handleCreateUser(postManuser);
        return ResponseEntity.ok(hieuUSer);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> DeleteUser(@PathVariable("id") long id) throws IdInvalidException {

        if (id >= 1500) {
            throw new IdInvalidException("id lon qua roi");
        }

        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok("delete success");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchUseByid(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> GetAllUser() {
        return ResponseEntity.ok(this.userService.fetchAllUser());
    }

    @PutMapping("/users")
    public ResponseEntity<User> UpdateUserById(@RequestBody User user) {
        User hieuUSer = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(hieuUSer);
    }

}
