package org.example.businessserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.businessserver.object.Chat;
import org.example.businessserver.repository.ChatRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController //데이터 리턴 서버
public class ChatController {
    private final ChatRepository chatRepository;

    @CrossOrigin
    @GetMapping(value="/sender/{sender}/receiver/{receiver}",produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable String sender,
                             @PathVariable String receiver){
        return chatRepository.mFindBySender(sender,receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/chat")
    public Mono setMsg(@RequestBody Chat chat){
        chat.setCreateAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }
}
