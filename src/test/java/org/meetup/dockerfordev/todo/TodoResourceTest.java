package org.meetup.dockerfordev.todo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetup.dockerfordev.todo.entity.TodoItem;
import org.meetup.dockerfordev.todo.facade.TodoResource;
import org.meetup.dockerfordev.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoResourceTest {

    @Autowired
    TodoResource controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static TodoItem item;

    @Autowired
    public TodoItemRepository repository;

    @Test
    public void contextLoads() {
        assertThat(controller, notNullValue());
        assertThat(repository, notNullValue());
    }

    @BeforeAll
    public static void setUp() {
        item = new TodoItem();
        item.setTitle("Title");
        item.setDone(true);
    }

    public  void tearDown() {
        repository.findById(item.getId()).ifPresent(item -> repository.delete(item));
    }

    @Test
    public void shouldUpdateTodoItem() {
        repository.save(item);
        TodoItem newItem = new TodoItem();
        newItem.setTitle("some title");
        newItem.setDone(false);

        HttpEntity<TodoItem> entity = new HttpEntity<>(newItem);
        String url = String.format("http://localhost:%s/todos/%d", port, this.item.getId());
        ResponseEntity<TodoItem> response = restTemplate.exchange(url, HttpMethod.PUT, entity, TodoItem.class);

        assertThat(response.getStatusCodeValue(), equalTo(200));
        TodoItem updatedItem = response.getBody();
        assertThat(updatedItem.getTitle(), equalTo("some title"));
        assertThat(updatedItem.getDone(), equalTo(false));
    }
}
