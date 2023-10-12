package com.example.taskmanager.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {
    @Value("${file.stack.api}")
    private String FILE_STACK_API;

    public String getFILE_STACK_API() {
        return FILE_STACK_API;
    }

    public void setFILE_STACK_API(String FILE_STACK_API) {
        this.FILE_STACK_API = FILE_STACK_API;
    }
}
