package com.company.tm.security;

import com.company.tm.model.Task;
import com.company.tm.model.TaskPriority;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.model.RowLevelPredicate;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "View assign tasks", code = "view-assigned-tasks")
public interface ViewAssignedTasks {

    @JpqlRowLevelPolicy(entityClass = Task.class, where = "{E}.assignee.id = :current_user_id")
    void task();

    @PredicateRowLevelPolicy(entityClass = Task.class, actions = {RowLevelPolicyAction.CREATE, RowLevelPolicyAction.UPDATE})
    default RowLevelPredicate<Task> createOnlyLowPriorityTasks() {
        return task -> task.getPriority() != TaskPriority.HIGH;
    }
}
