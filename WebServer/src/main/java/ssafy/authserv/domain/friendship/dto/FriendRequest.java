package ssafy.authserv.domain.friendship.dto;

import ssafy.authserv.domain.friendship.entity.enums.FriendshipStatus;

import java.util.UUID;

public record FriendRequest(
        UUID requesterId,
        String requesterName,
        String receiverName
//        FriendshipStatus status
) {
}
