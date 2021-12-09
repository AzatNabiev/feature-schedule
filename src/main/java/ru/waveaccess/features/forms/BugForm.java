package ru.waveaccess.features.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BugForm {
    private String taskId;
    private String bugName;
    private String description;
    private String status;
}
