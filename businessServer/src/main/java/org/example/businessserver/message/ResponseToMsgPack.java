package org.example.businessserver.message;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Room;
import org.example.businessserver.object.Rooms;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;
import java.util.Arrays;

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
        packer.packArrayHeader(3);
        packer.packString("userList");
        packer.packString(String.valueOf(channel.getSessionsName()));
        packer.packString("\n");
        bytes = packer.toByteArray();

        packer.flush();
        packer.close();

        return bytes;
    }

    // 로비에 있는 게임 방 목록
    public static byte[] lobbyRoomToMsgPack() throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            packer.packArrayHeader(3);
            packer.packString("roomList");
            packer.packString(String.valueOf(Rooms.getRoomsInfo()));
            packer.packString("\n");

            return packer.toByteArray();
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }

    // 게임 방에 대한 정보
    public static byte[] gameRoomInfoToMsgPack(int roomIdx) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            Room room = Rooms.getRoom(roomIdx);

            packer.packArrayHeader(8);
            packer.packString("roomInfo");
            packer.packString(room.getRoomUserList().toString());   // 방 유저 목록
            packer.packInt(roomIdx);                    // 방 번호
            packer.packString(room.getRoomName());                  // 방이름
            packer.packString(room.getRoomManager());               // 방장 닉네임
            packer.packString(room.getMapName());                   // 맵 이름
            packer.packString(room.getIsReady().toString());        // 준비상태 정보
            packer.packString("\n");

            return packer.toByteArray();
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }
}
