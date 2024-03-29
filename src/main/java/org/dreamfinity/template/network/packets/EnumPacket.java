package org.dreamfinity.template.network.packets;

public enum EnumPacket {
    GUILD_NAME(ClientPacketName.class),

    GUILD_GETNAME(ServerPacketName.class);

    private Class<? extends BasePacket> packetClass;

    EnumPacket(Class<? extends BasePacket> packetClass) {
        this.packetClass = packetClass;
    }

    public Class<? extends BasePacket> getPacketClass() {
        return packetClass;
    }
}