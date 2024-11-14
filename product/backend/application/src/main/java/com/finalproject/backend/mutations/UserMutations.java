package com.finalproject.backend.mutations;

import com.finalproject.backend.dto.UserInput;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.mappers.UserMapper;
import com.finalproject.backend.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class UserMutations {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserMapper userMapper;

    public UserMutations(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @DgsMutation
    public UserEntity createUser(@InputArgument UserInput userInput) {

        return userService.createUser(userMapper.mapInputToUser(userInput));
    }
}
