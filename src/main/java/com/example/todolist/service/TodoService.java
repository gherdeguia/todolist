package com.example.todolist.service;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private final TodoRepository todoRepository;


    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAllItemsService() {
        return todoRepository.findAll();
    }

    public Todo findTodoListItemByIdService(int listId){
        return todoRepository.findById(listId)
                .orElseThrow(null);
    }

    public Todo addNewListItemService(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateListItemService(Integer id, Todo todo) {
        return todoRepository.findById(id)
                .map(todoItem -> {
                    todo.setId(id);
                    return todoRepository.save(todo);
                })
                .orElseThrow(null);
    }
}
