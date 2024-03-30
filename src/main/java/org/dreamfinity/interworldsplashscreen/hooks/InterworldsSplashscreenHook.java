package org.dreamfinity.interworldsplashscreen.hooks;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.dreamfinity.hooklibultimate.asm.Hook;
import org.dreamfinity.hooklibultimate.asm.ReturnCondition;
import org.dreamfinity.interworldsplashscreen.Config;
import org.lwjgl.opengl.GL11;

public class InterworldsSplashscreenHook {
    static final Minecraft mc = Minecraft.getMinecraft();
    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "drawScreen")
    @SideOnly(Side.CLIENT)
    public static void drawScreen(GuiDownloadTerrain gui, int mouseX, int mouseY, float partialTicks) {
        NetHandlerPlayClient netHandlerPlayClient = ReflectionHelper.getPrivateValue(GuiDownloadTerrain.class, gui, "field_146594_a", "netHandlerPlayClient");
        WorldClient worldClient = ReflectionHelper.getPrivateValue(NetHandlerPlayClient.class, netHandlerPlayClient, "field_147300_g", "clientWorldController");
        String worldName = worldClient.provider.getDimensionName();
        FontRenderer fontRenderer = ReflectionHelper.getPrivateValue(GuiScreen.class, gui, "field_146289_q", "fontRendererObj");
        gui.drawBackground(0);
        String text = Config.loadingTexts.getOrDefault(worldName, worldName);
        drawTextBackground(gui.width / 2, gui.height * 3 / 4, fontRenderer.getStringWidth(text), fontRenderer.FONT_HEIGHT);
        gui.drawCenteredString(fontRenderer, text, gui.width / 2, gui.height * 3 / 4, 16777215);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "drawBackground", createMethod = true)
    @SideOnly(Side.CLIENT)
    public static void drawBackground(GuiDownloadTerrain gui, int mode) {
        NetHandlerPlayClient netHandlerPlayClient = ReflectionHelper.getPrivateValue(GuiDownloadTerrain.class, gui, "field_146594_a", "netHandlerPlayClient");
        WorldClient worldClient = ReflectionHelper.getPrivateValue(NetHandlerPlayClient.class, netHandlerPlayClient, "field_147300_g", "clientWorldController");
        String worldName = worldClient.provider.getDimensionName();
        drawFullscreenImage(Config.splashes.getOrDefault(worldName, Config.defaultSplash), gui.height, gui.width);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "drawBackground", createMethod = true)
    @SideOnly(Side.CLIENT)
    public static void drawConnectingBackground(GuiConnecting gui, int mode) {
        drawFullscreenImage(Config.splashes.getOrDefault("splashConnecting", Config.defaultSplash), gui.height, gui.width);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "drawBackground", createMethod = true)
    @SideOnly(Side.CLIENT)
    public static void drawDisconnectedBackground(GuiDisconnected gui, int mode) {
        drawFullscreenImage(Config.splashes.getOrDefault("splashDisconnecting", Config.defaultSplash), gui.height, gui.width);
    }

    public static void drawTextBackground(int x, int y, int width, int height) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.45f);

        tessellator.startDrawingQuads();
        tessellator.addVertex(x - (double) width / 2 - 5, y + height + 5, 0.0);
        tessellator.addVertex(x + (double) width / 2 + 5, y + height + 5, 0.0);
        tessellator.addVertex(x + (double) width / 2 + 5, y - 5, 0.0);
        tessellator.addVertex(x - (double) width / 2 - 5, y - 5, 0.0);

        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawFullscreenImage(ResourceLocation image, double height, double width) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator tessellator = Tessellator.instance;
        mc.getTextureManager().bindTexture(image);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0, height, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(width, height, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(width, 0.0, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}
