package ru.waveaccess.features.forms;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class RegistrationForm {

    private String login;
    private String password;
    private String name;
}
