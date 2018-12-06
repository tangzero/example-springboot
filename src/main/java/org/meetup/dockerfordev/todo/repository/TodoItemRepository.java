package org.meetup.dockerfordev.todo.repository;

import org.meetup.dockerfordev.todo.entity.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
}
