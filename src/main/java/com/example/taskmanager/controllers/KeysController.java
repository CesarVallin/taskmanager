package com.example.taskmanager.controllers;

import com.example.taskmanager.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeysController {

    @Autowired
    private Keys keys;

    @GetMapping(value = "/keys.js", produces = "application/javascript")
    public String getKeys() {
        return String.format("const FILE_STACK_API = '%s'", keys.getFILE_STACK_API());
    }

}
