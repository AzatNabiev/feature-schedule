package ru.waveaccess.features.dto;


import lombok.*;
import ru.waveaccess.features.enums.FeatureRoles;
import ru.waveaccess.features.models.Feature;
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
public class FeatureDto {
    private Long id;
    private String name;
    private String description;
    private List<Task> tasks;
    private FeatureRoles featureRoles;
    private UserDto user;

    public static FeatureDto fromModelToDto(Feature feature) {
        return feature == null ? null : FeatureDto.builder()
                .id(feature.getId())
                .name(feature.getName())
                .description(feature.getDescription())
                .featureRoles(feature.getFeatureRoles())
                .tasks(feature.getTasks())
                .user(UserDto.fromModelToDto(feature.getUser()))
                .build();
    }

    public static List<FeatureDto> fromModelToDto(List<Feature> features) {
        return features == null ? new ArrayList<>() : features.stream().map(FeatureDto::fromModelToDto).collect(Collectors.toList());
    }
}
