package com.example.todolist.integration;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Test
    void should_return_all_list_items_when_call_list_api() throws Exception {
        List<Todo> todos =todoListDataFactory();
        todoRepository.saveAll(todos);


        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].text").value("todo1"))
                .andExpect(jsonPath("$[0].done").value(false))
                ;
    }

    @Test
    void should_add_new_list_item_when_call_add_item_api() throws Exception {
        List<Todo> todos =todoListDataFactory();
        todoRepository.saveAll(todos);

        String newTodoItem = "{ \"text\" : \"coming from the post\", \"done\" : false  }";

        mockMvc.perform(post("/todos")
                .contentType(APPLICATION_JSON)
                .content(newTodoItem))
                .andExpect(status().isCreated())
                ;
    }

    @Test
    void should_update_selected_item_when_call_update_api() throws Exception {
        List<Todo> todos =todoListDataFactory();
        todoRepository.saveAll(todos);

        Todo todoItem = todoListDataFactory().get(1);
        Integer returnedItemId = todoRepository.save(todoItem).getId();
        String updatedTodoItem = "{ \"id\" : 2,\"text\" : \"coming from the post\", \"done\" : false  }";
        //then
        mockMvc.perform(put(format("/todos/%d", returnedItemId))
                .contentType(APPLICATION_JSON)
                .content(updatedTodoItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(returnedItemId))
            ;
    }

    private List<Todo> todoListDataFactory() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1,"todo1",false));
        todos.add(new Todo(2,"todo2",false));
        return todos;
    }

}
