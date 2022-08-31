package com.company.tm.model.task;

import io.jmix.ui.screen.*;
import com.company.tm.model.Task;

@UiController("tm_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
}