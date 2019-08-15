package org.meetup.dockerfordev.todo;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetup.dockerfordev.todo.entity.TodoItem;
import org.meetup.dockerfordev.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoryTests {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TodoItemRepository todoItemRepository;

    private static TodoItem todoItem;

    @BeforeAll
    public static void setUp() {
        todoItem = createTodoItem();
    }

    public void tearDown() {
        entityManager.remove(todoItem);
    }

    @Test
    public void saveShouldStoreInTheDatabaseAndReturnATodoItemWithAGeneratedId() {
        entityManager.persist(todoItem);
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);

        assertThat(savedTodoItem, notNullValue());
        assertThat(savedTodoItem.getId(), notNullValue());
        TodoItem fromDatabase = todoItemRepository.findById(savedTodoItem.getId()).orElse(null);
        assertThat(fromDatabase, notNullValue());
        assertThat(savedTodoItem, equalTo(fromDatabase));
    }

    @Test
    public void deleteShouldRemoveInsertedTodoItem() {
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);

        todoItemRepository.delete(savedTodoItem);

        Optional<TodoItem> deletedElement = todoItemRepository.findById(savedTodoItem.getId());

        assertThat(deletedElement.isPresent(), equalTo(false));
    }

    private static TodoItem createTodoItem() {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Some title");
        todoItem.setDone(false);
        return todoItem;
    }
}
