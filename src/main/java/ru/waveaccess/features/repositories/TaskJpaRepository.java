package ru.waveaccess.features.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM task as a where a.task_role = :role and a.user_id = :id", nativeQuery = true)
    Optional<List<Task>> getAllByTaskRoleAndUser(@Param("role") String role, @Param("id") Long userId);

    @Query(value = "SELECT a FROM Task a where (:name is null or a.name in :name) and " +
            "(:task_role is null or a.taskRole in :task_role) and (:user is null or a.user in :user)")
    Optional<Task> getTaskByParams(@Param("name") String name,@Param("task_role") TaskRoles task_role,@Param("user") User user);
}