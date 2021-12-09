package ru.waveaccess.features.dto;

import lombok.*;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private TaskRoles taskRoles;
    private UserDto user;

    public static TaskDto fromModelToDto(Task task) {
        return task == null ? null : TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .taskRoles(task.getTaskRole())
                .user(UserDto.fromModelToDto(task.getUser()))
                .build();
    }

    public static List<TaskDto> fromModelToDto(List<Task> tasks) {
        return tasks == null ? new ArrayList<>() : tasks.stream().map(TaskDto::fromModelToDto).collect(Collectors.toList());
    }

}
