package ru.waveaccess.features.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.waveaccess.features.configuration.SwaggerConfig;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.forms.UserForm;
import ru.waveaccess.features.services.UserService;

@RestController
@RequestMapping("/api/grade")
@Api(tags = {SwaggerConfig.USER_TAG})
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/status")
    @ApiOperation(value = "Giving grade to User By Manager", response = UserDto.class)
    public ResponseEntity<UserDto> addGradeToUser(@RequestBody UserForm userForm) {
        return ResponseEntity.ok(userService.giveUserGrade(userForm));
    }
}
