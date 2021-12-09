package ru.waveaccess.features.dto;

import lombok.*;
import ru.waveaccess.features.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String login;
    private String name;

    public static UserDto fromModelToDto(User user) {
        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> fromModelToDto(List<User> users) {
        return users == null ? new ArrayList<>() : users.stream().map(UserDto::fromModelToDto).collect(Collectors.toList());
    }
}
