package api.com.medhead.controller;

import api.com.medhead.Utils.UserUtils;
import api.com.medhead.model.User;
import api.com.medhead.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/init")
public class InitController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("users")
    public List<User> generateUsersList() throws IOException, InterruptedException {

        final UserUtils userUtils = new UserUtils();
        final List<User> users = userUtils.findUsers();

        for (User u : users) {
            userRepository.save(u);
        }
        return users;
    }
}
