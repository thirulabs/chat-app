package org.thirulabs.chat.server.rest.protobuf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/proto/message")
@RequiredArgsConstructor
@Slf4j
public class ProtoMessageController {
    private final MessageService messageService;

    @GetMapping()
    public MessageArray findAll(){
        var pList = MessageMapper.INSTANCE.map(messageService.findAll());
        return MessageArray.newBuilder().addAllArray(pList).build();
    }

    @GetMapping("/{id}")
    public Message findById(@PathVariable Long id){
        return MessageMapper.INSTANCE.map(messageService.findById(id));
    }

    @PutMapping
    public Message add(@RequestBody Message message){
        var msg = MessageMapper.INSTANCE.map(message);
        msg = messageService.add(msg);
        return MessageMapper.INSTANCE.map(msg);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Message message){
        boolean updated = messageService.update(id, MessageMapper.INSTANCE.map(message));
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
