package ru.waveaccess.features.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.features.configuration.SwaggerConfig;
import ru.waveaccess.features.dto.TaskDto;
import ru.waveaccess.features.forms.BugForm;
import ru.waveaccess.features.forms.TaskForm;
import ru.waveaccess.features.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Api(tags = {SwaggerConfig.TASK_TAG})
public class TaskRestController {

    private final TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/opened")
    @ApiOperation(value = "Retrieving all opened user tasks", response = TaskDto.class)
    public ResponseEntity<List<TaskDto>> getAllOpenUserTasks(@RequestBody TaskForm taskForm) {
        return ResponseEntity.ok(taskService.getAllOpenTasks(taskForm));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Getting tasks by ID", response = TaskDto.class)
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @ApiOperation(value = "Adding Task to Feature", response = TaskDto.class)
    public ResponseEntity<TaskDto> addTaskToFeature(@RequestBody TaskForm taskForm) {
        return ResponseEntity.ok(taskService.addTaskToFeature(taskForm));
    }

    @PostMapping("/solved")
    @ApiOperation(value = "Changing the state of a Task instance from InProgress to Resolved without passing it to the Tester",
            response = TaskDto.class)
    public ResponseEntity<TaskDto> sendTaskToCheck(@RequestBody TaskForm taskForm) {
        return ResponseEntity.ok(taskService.changeTaskStateForCheck(taskForm));
    }

    @PostMapping("/solved/tester")
    @ApiOperation(value = "Changing the state of a Task instance from InProgress to Resolved from being passed to the Tester",
            response = TaskDto.class)
    public ResponseEntity<TaskDto> sendTaskToNext(@RequestBody TaskForm taskForm) {
        return ResponseEntity.ok(taskService.sendTaskToNext(taskForm));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Reviewed result", response = TaskDto.class)
    public ResponseEntity<TaskDto> reviewTask(@PathVariable Long id,
                                              @RequestBody BugForm bugForm) {
        return ResponseEntity.ok(taskService.changeTaskState(id, bugForm));
    }

    @GetMapping("/param")
    @ApiOperation(value = "Get task by params", response = TaskDto.class)
    public ResponseEntity<TaskDto> getTaskByParams(@RequestBody TaskForm taskForm) {
        return ResponseEntity.ok(taskService.getTaskPyParams(taskForm));
    }
}
