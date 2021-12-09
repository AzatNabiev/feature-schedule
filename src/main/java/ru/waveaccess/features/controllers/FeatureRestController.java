package ru.waveaccess.features.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.features.configuration.SwaggerConfig;
import ru.waveaccess.features.dto.FeatureDto;
import ru.waveaccess.features.forms.FeatureForm;
import ru.waveaccess.features.services.FeatureService;

import java.util.List;

@RestController
@RequestMapping("/api/feature")
@Api(tags = {SwaggerConfig.FEATURE_TAG})
public class FeatureRestController {

    private final FeatureService featureService;

    @Autowired
    public FeatureRestController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/all/features")
    @ApiOperation(value = "Getting all Feature objects", response = FeatureDto.class)
    public ResponseEntity<List<FeatureDto>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @GetMapping("/all/features/{id}")
    @ApiOperation(value = "Getting Feature by ID", response = FeatureDto.class)
    public ResponseEntity<FeatureDto> getFeature(@PathVariable Long id) {
        return ResponseEntity.ok(featureService.getFeatureById(id));
    }

    @PostMapping
    @ApiOperation(value = "Creating Feature", response = FeatureDto.class)
    public ResponseEntity<FeatureDto> createFeature(@RequestBody FeatureForm featureForm) {
        return ResponseEntity.ok(featureService.addFeature(featureForm));
    }

    @PostMapping("/user")
    @ApiOperation(value = "Adding User to Feature", response = FeatureDto.class)
    public ResponseEntity<FeatureDto> addUserToFeature(@RequestBody FeatureForm featureForm) {
        return ResponseEntity.ok(featureService.addUserToFeature(featureForm));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Trying to close Feature", response = FeatureDto.class)
    public ResponseEntity<FeatureDto> tryToClose(@PathVariable Long id) {
        return ResponseEntity.ok(featureService.tryToCloseFeature(id));
    }
}
