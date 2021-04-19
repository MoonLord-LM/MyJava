package cn.moonlord.task;

import java.io.Serializable;
import java.util.UUID;

public class TaskVO implements Serializable {

    private String taskId;
    private Long creationTime;
    private String taskName;
    private String taskDescription;
    private String taskType;
    private String taskTime;

    public String getTaskId() {
        return taskId != null ? taskId : UUID.randomUUID() + "" + UUID.randomUUID();
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public Long getCreationTime() {
        return creationTime != null ? creationTime : System.currentTimeMillis();
    }
    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public String getTaskType() {
        return taskType;
    }
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    public String getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

}
