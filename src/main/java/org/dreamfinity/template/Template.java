package org.dreamfinity.template;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dreamfinity.template.proxy.CommonProxy;


@Mod(
    modid = Template.MODID,
    name = Template.NAME,
    version = Template.VERSION
)
public class Template {
    public static final  String MODID = "template";
    public static final String NAME = "Mod Template";
    public static final String VERSION = "@version@";
    public static Logger logger = LogManager.getLogger(MODID);
    @Mod.Instance(MODID)
    public static Template instance;

    @SidedProxy(
        clientSide = "org.dreamfinity.template.proxy.ClientProxy",
        serverSide = "org.dreamfinity.template.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
