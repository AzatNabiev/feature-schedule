package ru.waveaccess.features.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.waveaccess.features.models.Role;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    @Query(value = "select * from role where role.user_roles = :role", nativeQuery=true)
    Optional<Role> findByUserRoles(@Param("role") String name);
}
