package org.dreamfinity.template.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }
    @Override
    public void init(FMLInitializationEvent event) {
        this.registerKeyBindings();
        this.registerEvents();
        this.registerEvents();
    }
    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void registerKeyBindings() {

    }

    public void registerRenderers() {

    }

    private void registerEvents() {

    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext context) {
        return (context.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(context));
    }
}
