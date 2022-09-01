package com.company.tm.app;

import com.company.tm.model.Project;
import com.company.tm.model.Task;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("tm_TaskImportService")
public class TaskImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskImportService.class);

    @Autowired
    private DataManager dataManager;

    public int importTasks() {
        List<String> externalTaskNames = obtainExternalTaskNames();
        Project project = loadDefaultProject();

        List<Task> tasks = externalTaskNames.stream()
                .map(name -> {
                    Task task = dataManager.create(Task.class);
                    task.setName(name);
                    task.setProject(project);
                    return task;
                }).collect(Collectors.toList());

        EntitySet entitySet = dataManager.save(new SaveContext().saving(tasks));
        log.info("{} tasks imported", entitySet.size());
        return entitySet.size();
    }

    private List<String> obtainExternalTaskNames() {
        return Stream.iterate(0, i -> i).limit(5)
                .map(i -> "Task " + RandomStringUtils.randomAlphabetic(5))
                .collect(Collectors.toList());
    }

    @Nullable
    private Project loadDefaultProject() {
        return dataManager.load(Project.class)
                .query("select p from tm_Project p where p.defaultProject = :defaultProject1")
                .parameter("defaultProject1", true)
                .optional().orElse(null);
    }
}