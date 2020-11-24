package com1032.cw1.mk00756.mk00756_todolist;

public class Task {
    // create fields
    private String task = "";
    private Boolean completed = false;
    private String notes = "";

    // constructor
    public Task(String task, Boolean completed, String notes) {
        this.task = task;
        this.completed = completed;
        this.notes = notes;
    }
    // getters
    public String getTask() {
        return this.task;
    }

    public String getCompleted() {
        String result = "";

        if (this.completed == true) {
            result = "Finished";
        } else {
            result = "Unfinished";
        }

        return result;
    }

    public String getNotes() {
        return this.notes;
    }
    // setters
    public void setTask(String task) {
        this.task = task;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
