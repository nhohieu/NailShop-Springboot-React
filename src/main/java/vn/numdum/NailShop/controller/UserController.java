package vn.numdum.NailShop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.domain.DTO.RestCreateUserDTO;
import vn.numdum.NailShop.domain.DTO.RestUpdateUserDTO;
import vn.numdum.NailShop.domain.DTO.RestUserDTO;
import vn.numdum.NailShop.domain.DTO.ResultPaginationDTO;
import vn.numdum.NailShop.service.UserService;
import vn.numdum.NailShop.util.annotation.ApiMessage;
import vn.numdum.NailShop.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    @ApiMessage("create new user")
    public ResponseEntity<RestCreateUserDTO> CreateNewUser(@Valid @RequestBody User postManuser)
            throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(postManuser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException("Email" + postManuser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác");
        }
        String hashPassword = this.passwordEncoder.encode(postManuser.getPassword());
        postManuser.setPassword(hashPassword);
        User hieuUSer = this.userService.handleCreateUser(postManuser);
        return ResponseEntity.ok(this.userService.convertToResCreateUserDTO(hieuUSer));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("delete user")
    public ResponseEntity<Void> DeleteUser(@PathVariable("id") long id) throws IdInvalidException {
        User currentUser = this.userService.fetchUseByid(id);
        if (currentUser == null) {
            throw new IdInvalidException("User voi id  = " + id + "không tồn tại");
        }

        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("get user by id")
    public ResponseEntity<RestUserDTO> GetUserById(@PathVariable("id") long id) throws IdInvalidException {
        User fetchUser = this.userService.fetchUseByid(id);
        if (fetchUser == null) {
            throw new IdInvalidException("User voi id  = " + id + "không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> GetAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.userService.fetchAllUser(spec, pageable));
    }

    @PutMapping("/users")
    @ApiMessage("update user ")
    public ResponseEntity<RestUpdateUserDTO> UpdateUserById(@RequestBody User user) throws IdInvalidException {
        User hieuUSer = this.userService.handleUpdateUser(user);
        if (hieuUSer == null) {
            throw new IdInvalidException("User voi id " + user.getId() + "không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(hieuUSer));
    }

}
