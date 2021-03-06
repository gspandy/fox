package com.fox.rpc.common.codec.invoker;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.codec.Serializer;
import com.fox.rpc.common.codec.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class InvokerEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public InvokerEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {

        if (genericClass.isInstance(in)) {
            InvokeRequest invokeRequest=(InvokeRequest)in;
            String s=invokeRequest.getSerialize();
            Serializer serializer=SerializerFactory.getSerializer(s);
            byte[] data = serializer.serialize(in);
            out.writeByte(serializer.getSerializerType());
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
