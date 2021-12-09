package ru.waveaccess.features.forms;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AuthForm {
    private String login;
    private String password;
}
