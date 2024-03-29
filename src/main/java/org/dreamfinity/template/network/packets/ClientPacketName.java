package org.dreamfinity.template.network.packets;

import io.netty.buffer.ByteBuf;

public class ClientPacketName extends AbstractPacketClient{

    public String message = "";

    @Override
    public void read(ByteBuf data) throws IndexOutOfBoundsException {
        this.message = this.readString(data);
    }
}
