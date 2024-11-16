package com.finalproject.backend.mutations;


import com.finalproject.backend.dto.UserInput;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.mappers.UserMapper;
import com.finalproject.backend.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import lombok.extern.log4j.Log4j2;

@DgsComponent
@Log4j2
public class UserMutations {

    private final UserService userService;
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
