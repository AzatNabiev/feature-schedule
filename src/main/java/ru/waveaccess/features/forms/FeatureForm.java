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
public class FeatureForm {
    private String featureId;
    private String featureName;
    private String featureDescription;
    private String userId;
    private String userName;
    private String userLogin;

}
