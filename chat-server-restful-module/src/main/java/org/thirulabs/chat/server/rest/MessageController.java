package org.thirulabs.chat.server.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;

    @GetMapping()
    public List<Message> findAll(){
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public Message findById(@PathVariable Long id){
        return messageService.findById(id);
    }

    @PutMapping
    public Message add(@RequestBody Message message){
        return messageService.add(message);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Message message){
        boolean updated = messageService.update(id, message);
        if(updated){
            return ResponseEntity.accepted().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        boolean removed = messageService.remove(id);
        if(removed){
            return ResponseEntity.accepted().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> remove(){
        messageService.removeAll();
        return ResponseEntity.accepted().build();
    }
}
