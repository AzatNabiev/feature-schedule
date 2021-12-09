package ru.waveaccess.features.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.waveaccess.features.dto.TaskDto;
import ru.waveaccess.features.enums.BugRoles;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.exceptions.NoSuchFeatureException;
import ru.waveaccess.features.exceptions.NoSuchTaskException;
import ru.waveaccess.features.exceptions.NoSuchUserException;
import ru.waveaccess.features.forms.BugForm;
import ru.waveaccess.features.forms.TaskForm;
import ru.waveaccess.features.models.Bug;
import ru.waveaccess.features.models.Feature;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.FeatureJpaRepository;
import ru.waveaccess.features.repositories.TaskJpaRepository;
import ru.waveaccess.features.repositories.UserJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskJpaRepository taskJpaRepository;
    private UserJpaRepository userJpaRepository;
    private FeatureJpaRepository featureJpaRepository;

    @Autowired
    public void setTaskServiceImpl(TaskJpaRepository taskJpaRepository, UserJpaRepository userJpaRepository,
                                   FeatureJpaRepository featureJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.featureJpaRepository = featureJpaRepository;
    }

    public TaskDto sendTaskToNext(TaskForm taskForm) {
        Task task;
        Optional<Task> taskOptional = taskJpaRepository.findById(Long.parseLong(taskForm.getTaskId()));
        Optional<User> userOptional = userJpaRepository.findById(Long.parseLong(taskForm.getToUserById()));
        if (userOptional.isPresent()) {
            if (taskOptional.isPresent()) {
                task = taskOptional.get();
                task.setTaskRole(TaskRoles.RESOLVED);
                task.setUser(userOptional.get());
                taskJpaRepository.save(task);
            } else {
                throw new NoSuchTaskException("No such task");
            }
        } else {
            throw new NoSuchUserException("No such user");
        }
        return TaskDto.fromModelToDto(task);
    }

    public TaskDto addTaskToFeature(TaskForm taskForm) {
        Task task;
        Optional<User> userOptional = userJpaRepository.findById(Long.parseLong(taskForm.getToUserById()));
        Optional<Feature> featureOptional = featureJpaRepository.findById(Long.parseLong(taskForm.getFeatureId()));
        if (userOptional.isPresent()) {
            if (featureOptional.isPresent()) {
                task = Task.builder().name(taskForm.getTaskName()).description(taskForm.getTaskDescription())
                        .feature(featureOptional.get()).user(userOptional.get()).taskRole(TaskRoles.OPEN).build();
                taskJpaRepository.save(task);
            } else {
                throw new NoSuchFeatureException("No such feature");
            }
        } else {
            throw new NoSuchUserException("No such user");
        }
        return TaskDto.fromModelToDto(task);
    }


    public List<TaskDto> getAllOpenTasks(TaskForm taskForm) {
        List<Task> tasks;
        Optional<List<Task>> taskOptional = taskJpaRepository.getAllByTaskRoleAndUser("Open",
                Long.parseLong(taskForm.getToUserById()));
        if (taskOptional.isPresent()) {
            tasks = taskOptional.get();
            tasks.stream().forEach(val -> val.setTaskRole(TaskRoles.IN_PROGRESS));
        } else {
            throw new NoSuchTaskException("No such task");
        }
        return TaskDto.fromModelToDto(tasks);
    }

    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskJpaRepository.findById(id);
        if (optionalTask.isPresent()) {
            return TaskDto.fromModelToDto(optionalTask.get());
        } else {
            throw new NoSuchTaskException("No such task");
        }
    }

    public TaskDto changeTaskState(Long id, BugForm bugForm) {
        Task task;
        Optional<Task> optionalTask = taskJpaRepository.findById(id);

        if (optionalTask.isPresent()) {

            task = optionalTask.get();
            Optional<Feature> optionalFeature = featureJpaRepository.findById(task.getFeature().getId());

            if (bugForm.getStatus().equals(BugRoles.ACCEPTED.toString())) {
                task.setTaskRole(TaskRoles.COMPLETED);
            } else {
                task.setTaskRole(TaskRoles.IN_PROGRESS);
                if (task.getBugs() == null) {
                    task.setBugs(new ArrayList<>());
                } else {

                    if (optionalFeature.isPresent()) {
                        User user = optionalFeature.get().getUser();
                        task.getBugs().add(Bug.builder().bugName(bugForm.getBugName())
                                .description(bugForm.getDescription()).task(task).build());
                        task.setUser(user);
                    } else {
                        throw new NoSuchFeatureException("No such feature");
                    }
                }
            }
            taskJpaRepository.save(task);
        } else {
            throw new NoSuchTaskException("No such task");
        }
        return TaskDto.fromModelToDto(task);
    }

    public TaskDto changeTaskStateForCheck(TaskForm taskForm) {
        Task task;
        Optional<Task> taskOptional = taskJpaRepository.findById(Long.parseLong(taskForm.getTaskId()));
        if (taskOptional.isPresent()) {
            task = taskOptional.get();
            task.setTaskRole(TaskRoles.RESOLVED);
            task.setUser(null);
            taskJpaRepository.save(task);
        } else {
            throw new NoSuchTaskException("No such task");
        }
        return TaskDto.fromModelToDto(task);
    }

    public TaskDto getTaskPyParams(TaskForm taskForm) {
        Task task;
        Optional<Task> taskOptional = taskJpaRepository.getTaskByParams(taskForm.getTaskName(),
                taskForm.getTaskState() == null? null : TaskRoles.valueOf(taskForm.getTaskState()),
                taskForm.getToUserById() == null? null : User.builder().id(Long.parseLong(taskForm.getToUserById())).build());
        if (taskOptional.isPresent()) {
            task = taskOptional.get();
        } else {
            throw new NoSuchTaskException("No such task");
        }
        return TaskDto.fromModelToDto(task);
    }
}
