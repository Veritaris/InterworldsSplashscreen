package org.dreamfinity.interworldsplashscreen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dreamfinity.interworldsplashscreen.proxy.CommonProxy;

import java.io.File;


@Mod(
    modid = InterworldSplashscreen.MODID,
    name = InterworldSplashscreen.NAME,
    version = InterworldSplashscreen.VERSION
)
public class InterworldSplashscreen {
    public static final String MODID = "interworldsplashscreen";
    public static final String NAME = "InterworldSplashscreen";
    public static final String VERSION = "1.0.0";
    public static Logger logger = LogManager.getLogger(MODID);
    @Mod.Instance(MODID)
    public static InterworldSplashscreen instance;
    public static File configDir;

    @SidedProxy(
        clientSide = "org.dreamfinity.interworldsplashscreen.proxy.ClientProxy",
        serverSide = "org.dreamfinity.interworldsplashscreen.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        configDir = event.getModConfigurationDirectory();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
