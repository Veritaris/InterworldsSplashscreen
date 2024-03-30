package org.dreamfinity.interworldsplashscreen.proxy;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.dreamfinity.interworldsplashscreen.Config;

public class ClientProxy extends CommonProxy {
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        Config.load();
    }
}
