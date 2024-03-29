package org.dreamfinity.template.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.dreamfinity.template.Template;
import org.dreamfinity.template.network.server.OpenGuiMessage;

public class PacketDispatcher {

	private static byte packetId = 0;

	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Template.MODID);

	public static void registerPackets() {
		registerMessage(OpenGuiMessage.class);
	}

	private static <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> messageClass) {
		if (AbstractMessage.AbstractClientMessage.class.isAssignableFrom(messageClass)) {
			PacketDispatcher.dispatcher.registerMessage(messageClass, messageClass, packetId++, Side.CLIENT);
		} else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(messageClass)) {
			PacketDispatcher.dispatcher.registerMessage(messageClass, messageClass, packetId++, Side.SERVER);
		} else {
			PacketDispatcher.dispatcher.registerMessage(messageClass, messageClass, packetId, Side.CLIENT);
			PacketDispatcher.dispatcher.registerMessage(messageClass, messageClass, packetId++, Side.SERVER);
		}
	}

	public final void sendTo(IMessage message, EntityPlayerMP player) {
		PacketDispatcher.dispatcher.sendTo(message, player);
	}

	public static void sendToAll(IMessage message) {
		PacketDispatcher.dispatcher.sendToAll(message);
	}

	public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		PacketDispatcher.dispatcher.sendToAllAround(message, point);
	}

	public static void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	public static void sendToAllAround(IMessage message, EntityPlayer player, double range) {
		PacketDispatcher.sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
	}

	public static void sendToDimension(IMessage message, int dimensionId) {
		PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	}

	public static void sendToServer(IMessage message) {
		PacketDispatcher.dispatcher.sendToServer(message);
	}

}