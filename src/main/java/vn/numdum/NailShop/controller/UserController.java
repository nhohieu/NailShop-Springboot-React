package vn.numdum.NailShop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.domain.DTO.ResultPaginationDTO;
import vn.numdum.NailShop.service.UserService;
import vn.numdum.NailShop.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> CreateNewUser(@RequestBody User postManuser) {
        String hashPassword = this.passwordEncoder.encode(postManuser.getPassword());
        postManuser.setPassword(hashPassword);
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
    public ResponseEntity<ResultPaginationDTO> GetAllUser(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sCurrent = currentOptional.isPresent() ? currentOptional.get() : " ";
        String sPagesize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : " ";
        int current = Integer.parseInt(sCurrent);
        int pagesize = Integer.parseInt(sPagesize);
        Pageable pageable = PageRequest.of(current - 1, pagesize);
        return ResponseEntity.ok(this.userService.fetchAllUser(pageable));
    }

    @PutMapping("/users")
    public ResponseEntity<User> UpdateUserById(@RequestBody User user) {
        User hieuUSer = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(hieuUSer);
    }

}
