package org.example.businessserver.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.util.List;

public class Decoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        final int length = msg.readableBytes();
        array = new byte[length];
        msg.getBytes(msg.readerIndex(), array);

        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(array);
        // 예시로, 문자열로 된 데이터를 언팩합니다. 실제 사용 시에는 데이터의 구조에 맞게 처리해야 합니다.
        while (unpacker.hasNext()) {
            // MessagePack 데이터 구조에 따라 적절한 타입으로 변환
            String item = unpacker.unpackString();
            out.add(item);
        }
        unpacker.close();
    }
}
