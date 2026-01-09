package vn.numdum.NailShop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.repository.UserRepository;
import vn.numdum.NailShop.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
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
    public User CreateNewUser(@RequestBody User postManuser) {
        User hieuUSer = this.userService.handleCreateUser(postManuser);
        return hieuUSer;
    }

    @DeleteMapping("/users/{id}")
    public String DeleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "delete user";
    }

    @GetMapping("/users/{id}")
    public User GetUserById(@PathVariable("id") long id) {
        return this.userService.fetchUseByid(id);
    }

    @GetMapping("/users")
    public List<User> GetAllUser() {
        return this.userService.fetchAllUser();
    }

    @PutMapping("/users")
    public User UpdateUserById(@RequestBody User user) {
        User hieuUSer = this.userService.handleUpdateUser(user);
        return hieuUSer;
    }

}
