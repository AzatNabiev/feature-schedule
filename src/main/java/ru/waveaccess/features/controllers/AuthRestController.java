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
import ru.waveaccess.features.dto.AuthDto;
import ru.waveaccess.features.forms.AuthForm;
import ru.waveaccess.features.services.UserService;

@RestController
@RequestMapping("/api/auth")
@Api(tags = {SwaggerConfig.AUTH_TAG})
public class AuthRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Authentication", response = AuthDto.class)
    public ResponseEntity<AuthDto> auth(@RequestBody AuthForm authForm) {
        return ResponseEntity.ok(userService.authenticateUser(authForm));
    }
}
