package ssafy.authserv.domain.friendship.dto;

import java.util.UUID;

public record FriendInfo(
        UUID friendId,
        String friendNickname
) {
}
