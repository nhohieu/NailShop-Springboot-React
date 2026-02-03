package vn.numdum.NailShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.domain.DTO.Meta;
import vn.numdum.NailShop.domain.DTO.RestCreateUserDTO;
import vn.numdum.NailShop.domain.DTO.RestUpdateUserDTO;
import vn.numdum.NailShop.domain.DTO.RestUserDTO;
import vn.numdum.NailShop.domain.DTO.ResultPaginationDTO;
import vn.numdum.NailShop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUseByid(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional != null) {
            return userOptional.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());
        rs.setMeta(mt);

        List<RestUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> new RestUserDTO(
                        item.getId(),
                        item.getAddress(),
                        item.getEmail(),
                        item.getGender(),
                        item.getName()))

                .collect(Collectors.toList());
        rs.setResult(listUser);
        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUseByid(reqUser.getId());

        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setName(reqUser.getName());
            currentUser.setPassword(reqUser.getPassword());
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public boolean isEmailExist(String email) {

        return this.userRepository.existsBy(email);
    }

    public RestCreateUserDTO convertToResCreateUserDTO(User user) {
        RestCreateUserDTO res = new RestCreateUserDTO();
        res.setAddress(user.getAddress());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setId(user.getId());
        res.setName(user.getName());
        return res;
    }

    public RestUserDTO convertToResUserDTO(User user) {
        RestUserDTO res = new RestUserDTO();
        res.setAddress(user.getAddress());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setId(user.getId());
        res.setName(user.getName());
        return res;
    }

    public RestUpdateUserDTO convertToResUpdateUserDTO(User user) {
        RestUpdateUserDTO res = new RestUpdateUserDTO();
        res.setId(user.getId());
        res.setAddress(user.getAddress());
        res.setGender(user.getGender());
        res.setEmail(user.getEmail());
        res.setName(user.getName());

        return res;
    }

    public void updateUserTokne(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreToken(token);
            this.userRepository.save(currentUser);
        }
    }
}