package org.meetup.dockerfordev.todo.facade;

import org.meetup.dockerfordev.todo.entity.TodoItem;
import org.meetup.dockerfordev.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoResource {

    @Autowired
    TodoItemRepository repository;

    @RequestMapping("")
    public @ResponseBody Iterable<TodoItem> listTodoItems() {
        return repository.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody TodoItem createTodoItem(@RequestBody TodoItem todoItem) {
        return repository.save(todoItem);
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<TodoItem> updateTodoItem(@PathVariable("itemId") Long itemId, @RequestBody TodoItem itemToUpdate) {
        if(itemToUpdate!= null && repository.existsById(itemId)) {
            Optional<TodoItem> oldTodoItemOptional = repository.findById(itemId);
            if(oldTodoItemOptional.isPresent()) {
                TodoItem oldTodoItem = oldTodoItemOptional.get();
                oldTodoItem.setDone(itemToUpdate.getDone());
                oldTodoItem.setTitle(itemToUpdate.getTitle());
                return ResponseEntity.ok(repository.save(oldTodoItem));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{itemId}")
    public @ResponseBody TodoItem readTodoItem(@PathVariable("itemId") Long itemId){
        return repository.findById(itemId).orElse(null);
    }
}
