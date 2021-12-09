package ru.waveaccess.features.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskForm {

    private String taskId;
    private String featureId;
    private String taskName;
    private String taskDescription;
    private String toUserById;
    private String taskState;
}
