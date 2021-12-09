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
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
@Api(tags = {SwaggerConfig.REG_TAG})
public class RegRestController {

    private final UserService userService;

    @Autowired
    public RegRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Registration of User", response = UserDto.class)
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegistrationForm registrationForm) {
        return ResponseEntity.ok(userService.registerUser(registrationForm));
    }
}
