package org.dreamfinity.template.network.packets;


import io.netty.buffer.ByteBuf;

public abstract class BasePacket {
    public BasePacket(){}

    public abstract void write(ByteBuf data) throws IndexOutOfBoundsException;

    public abstract void read(ByteBuf data) throws IndexOutOfBoundsException;

    public void writeString(String string, ByteBuf data) throws IndexOutOfBoundsException
    {
        byte[] stringBytes = string.getBytes();
        data.writeInt(stringBytes.length);
        data.writeBytes(stringBytes);
    }

    public String readString(ByteBuf data) throws IndexOutOfBoundsException
    {
        int length = data.readInt();
        byte[] stringBytes = new byte[length];
        data.readBytes(stringBytes);

        return new String(stringBytes);
    }
}