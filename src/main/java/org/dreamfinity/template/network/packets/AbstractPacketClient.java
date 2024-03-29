package org.dreamfinity.template.network.packets;

import io.netty.buffer.ByteBuf;


@SuppressWarnings("unused")
public abstract class AbstractPacketClient extends BasePacket {

    @Override
    public final void write(ByteBuf data) {}
}