package org.example.businessserver.repository;

import org.example.businessserver.object.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable
    @Query("{sender:?0,receiver:?1}")
    Flux<Chat> mFindBySender(String sender, String receiver);
    //Flux- 데이터의 흐름,끊기지 않고 데이터를 지속적으로 받겠다는 의미

}