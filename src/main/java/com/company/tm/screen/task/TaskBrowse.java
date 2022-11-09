package com.company.tm.screen.task;

import com.company.tm.app.TaskImportService;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.tm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tm_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {

    @Autowired
    private TaskImportService taskImportService;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private Notifications notifications;

    @Subscribe("importBtn")
    public void onImportBtnClick(Button.ClickEvent event) {
        int tasks = taskImportService.importTasks();
        if (tasks > 0) {
            tasksDl.load();

            notifications.create()
                    .withCaption(tasks + " imported")
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        }
    }
}