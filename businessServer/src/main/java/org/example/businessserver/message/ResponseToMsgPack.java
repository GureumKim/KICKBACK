package org.example.businessserver.message;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Channels;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;

public class ResponseToMsgPack {
    public static byte[] errorToMsgPack(String message) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            packer.packArrayHeader(2);
            packer.packString("error");
            packer.packString(message);
            return packer.toByteArray();
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }

    // 로비에 있는 유저 목록
    public static byte[] lobbyUserToMsgPack(Channel channel) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        byte[] bytes;
        packer.packArrayHeader(2);
        packer.packString("userList");
        packer.packString(String.valueOf(channel.getSessionsName()));
        bytes = packer.toByteArray();

        packer.flush();
        packer.close();

        return bytes;
    }

    // 로비에 있는 게임 방 목록
    public static byte[] lobbyChannelToMsgPack() throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            packer.packArrayHeader(2);
            packer.packString("channelList");
            packer.packString(String.valueOf(Channels.getChannelsInfo()));
            System.out.println(Channels.getChannelsInfo());

            return packer.toByteArray();
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }

    // 게임 방에 대한 정보
    public static byte[] gameChannelInfoToMsgPack(int channelIdx) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            Channel channel = Channels.getChannel("GameChannel" + channelIdx);

            packer.packArrayHeader(10);
            packer.packString("channelInfo");
            packer.packString(channel.getChannelUserList().toString());   // 방 유저 목록
            packer.packInt(channelIdx);                                   // 방 번호
            packer.packString(channel.getChannelName());                  // 방이름
            packer.packString(channel.getChannelManager());               // 방장 닉네임
            packer.packString(channel.getMapName());                      // 맵 이름
            packer.packString(channel.getIsReady().toString());           // 준비상태 정보
            packer.packString(channel.getTeamColor().toString());         // 팀 컬러 정보
            packer.packString(channel.getUserCharacter().toString());     // 유저 캐릭터 정보
            packer.packString(channel.getGameMode());                     // 게임모드
            
            return packer.toByteArray();
        } finally {
            System.out.println("send channel Info complete");
            packer.close();
        }
    }
}
