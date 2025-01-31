package com.todo.todoapp.domain.model;

public class Task extends AppEntity {

	private Long id;

	private String title;

	private String notes;

	private String category;

	private TasksStatus status;

	private String created;

	private String lastUpdated;

	private String start;

	private String finish;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public TasksStatus getStatus() {
		return status;
	}

	public void setStatus(TasksStatus status) {
		this.status = status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", notes=" + notes + ", category=" + category + ", status="
				+ status + ", created=" + created + ", lastUpdated=" + lastUpdated + ", start=" + start + ", finish="
				+ finish + "]";
	}
}
