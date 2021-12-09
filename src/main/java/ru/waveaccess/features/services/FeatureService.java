package ru.waveaccess.features.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.waveaccess.features.dto.FeatureDto;
import ru.waveaccess.features.enums.FeatureRoles;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.exceptions.NoSuchFeatureException;
import ru.waveaccess.features.exceptions.NoSuchUserException;
import ru.waveaccess.features.forms.FeatureForm;
import ru.waveaccess.features.models.Feature;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.FeatureJpaRepository;
import ru.waveaccess.features.repositories.UserJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    private FeatureJpaRepository featureJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setFeatureServiceImpl(FeatureJpaRepository featureJpaRepository,
                              UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.featureJpaRepository = featureJpaRepository;
    }

    /**
     * Method for adding Feature`s object.
     * @param featureForm, must have next fields:
     * featureName, featureDescription. FeatureRoles will be in OPEN state by default.
     * @return method returns FeatureDto with next fields:
     * featureName, featureDescription.
     */
    public FeatureDto addFeature(FeatureForm featureForm) {
        Feature feature = Feature.builder().name(featureForm.getFeatureName()).description(featureForm.getFeatureDescription())
                .featureRoles(FeatureRoles.OPEN).build();
        featureJpaRepository.save(feature);
        return FeatureDto.fromModelToDto(feature);
    }

    /**
     * Method for getting all Feature`s objects.
     * @return List of FeatureDto with next FeatureDto`s fields:
     * id(Long), name(String), description(String), tasks(List of Task),
     * featureRoles(enum FeatureRoles), UserDto(DTO of User class).
     * Fields: tasks, userDto can be null.
     */
    public List<FeatureDto> getAllFeatures() {
        List<Feature> features = featureJpaRepository.findAll();
        return FeatureDto.fromModelToDto(features);
    }

    /**
     * Method for adding user to feature.
     * @param featureForm must have next fields:
     * userId(String), featureId(String).
     * @return FeatureDto with next FeatureDto`s fields:
     * id(Long), name(String), description(String), tasks(List of Task),
     * featureRoles(enum FeatureRoles), UserDto(DTO of User class).
     * Fields: tasks can be null.
     */
    public FeatureDto addUserToFeature(FeatureForm featureForm) {
        Feature feature;
        Optional<User> userOptional = userJpaRepository.findById(Long.parseLong(featureForm.getUserId()));
        Optional<Feature> featureOptional = featureJpaRepository.findById(Long.parseLong(featureForm.getFeatureId()));
        if (userOptional.isPresent()) {
            if (featureOptional.isPresent()) {
                feature = featureOptional.get();
                feature.setFeatureRoles(FeatureRoles.IN_PROGRESS);
                feature.setUser(userOptional.get());
                featureJpaRepository.save(feature);
            } else {
                throw new NoSuchFeatureException("No such feature");
            }
        } else {
            throw new NoSuchUserException("No such user");
        }
        return FeatureDto.fromModelToDto(feature);
    }


    /**
     * Method for getting Feature`s object by id.
     * @param id, has numeric type.
     * @return FeatureDto with next FeatureDto`s fields:
     * id(Long), name(String), description(String), tasks(List of Task),
     * featureRoles(enum FeatureRoles), UserDto(DTO of User class).
     * Fields: tasks, userDto can be null.
     */
    public FeatureDto getFeatureById(Long id) {
        Feature feature;
        Optional<Feature> featureOptional = featureJpaRepository.findById(id);
        if (featureOptional.isPresent()) {
            feature = featureOptional.get();
        } else {
            throw new NoSuchFeatureException("No such feature");
        }
        return FeatureDto.fromModelToDto(feature);
    }

    /**
     * Method for trying to close Feature`s object.
     * If parameter ACCEPTED Feature will be closed with state COMPLETED,
     * else Feature`s will change state in RESOLVED.
     * @param id, has numeric type.
     * @return FeatureDto with next FeatureDto`s fields:
     * id(Long), name(String), description(String), tasks(List of Task),
     * featureRoles(enum FeatureRoles), UserDto(DTO of User class).
     */
    public FeatureDto tryToCloseFeature(Long id) {
        Feature feature;
        Optional<Feature> featureOptional = featureJpaRepository.findById(id);
        if (featureOptional.isPresent()) {
            feature = featureOptional.get();
            boolean canComplete = true;
            for (Task task : feature.getTasks()) {
                if (!task.getTaskRole().equals(TaskRoles.COMPLETED)) {
                    canComplete = false;
                    break;
                }
            }
            if (canComplete) {
                feature.setFeatureRoles(FeatureRoles.COMPLETED);
                featureJpaRepository.save(feature);
            }
        } else {
            throw new NoSuchFeatureException("No such feature");
        }
        return FeatureDto.fromModelToDto(feature);
    }

}
