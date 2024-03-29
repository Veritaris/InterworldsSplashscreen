package org.dreamfinity.template.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }
    public void postInit(FMLPostInitializationEvent event) {

    }

    public EntityPlayer getPlayerEntity(MessageContext context) {
        return context.getServerHandler().playerEntity;
    }
}
