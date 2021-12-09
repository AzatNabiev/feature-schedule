package ru.waveaccess.features.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.waveaccess.features.models.Bug;

@Repository
public interface BugJpaRepository extends JpaRepository<Bug, Long> {
}
