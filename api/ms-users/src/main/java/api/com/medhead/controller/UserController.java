package api.com.medhead.controller;

import api.com.medhead.Utils.UserUtils;
import api.com.medhead.model.User;
import api.com.medhead.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("generate")
    public List<User> generateUsersList() throws IOException, InterruptedException {

        final UserUtils userUtils = new UserUtils();
        final List<User> users = userUtils.findUsers();

        for (User u : users) {
            userRepository.save(u);
        }
        return users;
    }

    @GetMapping("all")
    public List<User> getUsersList(){
        return userRepository.findAll();
    }

    @GetMapping("{id:[\\d]+}")
    public User getUser(int id){
        return userRepository.getOne(id);
    }
}
