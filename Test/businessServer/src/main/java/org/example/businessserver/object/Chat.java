package org.example.businessserver.object;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter
@Document(collection = "chat-app")
public class Chat {

    @Id
    private String id;
    private String msg;
    private String sender;
    private String receiver;
    private LocalDateTime createAt;

    public Chat(String id, String msg, String sender, String receiver, LocalDateTime createAt) {
        this.id = id;
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
        this.createAt = createAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }


    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
