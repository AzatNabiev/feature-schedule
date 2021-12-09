package ru.waveaccess.features.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.waveaccess.features.models.Feature;

@Repository
public interface FeatureJpaRepository extends JpaRepository<Feature, Long> {
}
