package org.dreamfinity.template.network.packets;

import io.netty.buffer.ByteBuf;

public class ServerPacketName extends AbstractPacketServer {

    @Override
    public void write(ByteBuf data) throws IndexOutOfBoundsException {
        data.writeBoolean(true);
    }
}
