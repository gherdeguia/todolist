package com.example.todolist.contoller;


import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllListItems(){
        return todoService.findAllItemsService();
    }

    @GetMapping(path = "/{id}")
    public Todo getTodoById(@PathVariable Integer id){
        return todoService.findTodoListItemByIdService(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Todo addNewItemList(@RequestBody Todo todo){
        return todoService.addNewListItemService(todo);
    }

    @PutMapping("/{id}")
    public Todo updateListToDone(@PathVariable Integer id,@RequestBody Todo todo){
        return todoService.updateDoneItemService(id, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteItemList(@PathVariable Integer id){
        todoService.deleteListItemService(id);
    }

}
