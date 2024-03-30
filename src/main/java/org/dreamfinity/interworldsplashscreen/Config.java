package org.dreamfinity.interworldsplashscreen;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class Config {
    public static ResourceLocation defaultSplash = new ResourceLocation("interworldsplashscreen:splashscreens/Overworld.png");
    public static HashMap<String, ResourceLocation> splashes = new HashMap<String, ResourceLocation>() {{
        put("Nether", new ResourceLocation("interworldsplashscreen:splashscreens/Nether.png"));
        put("The End", new ResourceLocation("interworldsplashscreen:splashscreens/The End.png"));
        put("Overworld", new ResourceLocation("interworldsplashscreen:splashscreens/Overworld.png"));
    }};

    public static HashMap<String, String> loadingTexts = new HashMap<>();

    static final String[] defaultSplashes = {
        "Nether:Nether",
        "The End:The End",
        "Overworld:Overworld",
        "splashConnecting:Overworld",
        "splashDisconnecting:Overworld"
    };

    static final String[] defaultSplashesTexts = {
        "Nether:Nether",
        "The End:The End",
        "Overworld:Overworld",
        "splashConnecting:Overworld",
        "splashDisconnecting:Overworld"
    };
    static final String[] buildInSplashes = {
        "Nether",
        "The End",
        "Overworld",
    };

    static void copyDefaultSplashes(File splashesDir) {
        if (!splashesDir.mkdirs()) return;

        for (int i = 0; i < 3; i++) {
            String sName = buildInSplashes[i];
            try {
                InputStream is = InterworldSplashscreen.class.getClassLoader().getResourceAsStream(String.format("assets/interworldsplashscreen/splashscreens/%s.png", sName));
                if (is == null) continue;
                Files.copy(is, new File(splashesDir, String.format("%s.png", sName)).toPath());
            } catch (IOException ignored) {
            }
        }
    }

    static void ensureDefaultSplashes(File splashesDir) {
        for (int i = 0; i < 3; i++) {
            String sName = buildInSplashes[i];
            File splash = new File(splashesDir, String.format("%s.png", sName));
            if (!splash.exists()) {
                try {
                    InputStream is = InterworldSplashscreen.class.getClassLoader().getResourceAsStream(String.format("assets/interworldsplashscreen/splashscreens/%s.png", sName));
                    if (is == null) continue;
                    Files.copy(is, splash.toPath());
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void load() {
        Path configDirPath = new File(InterworldSplashscreen.configDir, "InterworldSplashscreen").toPath();
        File splashesDir = new File(configDirPath.toFile(), "splashses");
        Configuration config = new Configuration(new File(configDirPath.toFile(), "InterworldSplashscreen.cfg"));

        if (!splashesDir.exists()) {
            copyDefaultSplashes(splashesDir);
        }
        ensureDefaultSplashes(splashesDir);

        config.load();

        config.addCustomCategoryComment("Splashes files", "Define filenames for world vs splashes. Format is <worldname>:<splashName>");
        String[] splashesRaw = config.getStringList(
            "splashesPaths",
            "Splashes files",
            defaultSplashes,
            "Splashes images to be used in loading screen. Use just filename without extension. Files must be stored in folder splashes in mod's config directory"
        );
        String defaultSplashName = config.getString("defaultSplash", "Splashes files", "Overworld", "Default splash to be used if not set for world");

        config.addCustomCategoryComment("Splashes texts", "Define texts to display on splashes. Format is <worldname>:<text>");
        String[] splashesTextsRaw = config.getStringList(
            "splashesTexts",
            "Splashes texts",
            defaultSplashesTexts,
            "Splashes texts to be displayed on splash"
        );
        config.save();


        splashes = Arrays.stream(splashesRaw)
                       .map(entry -> entry.split(":", 2))
                       .collect(Collectors.toMap(
                           e -> e[0],
                           e -> {
                               BufferedImage image;
                               try {
                                   InterworldSplashscreen.logger.info(
                                       String.format(
                                           "Loading splashscreen %s for world %s",
                                           new File(splashesDir, String.format("%s.png", e[1])),
                                           e[0]
                                       )
                                   );
                                   image = ImageIO.read(new File(splashesDir, String.format("%s.png", e[1])));
                                   return FMLClientHandler.instance()
                                              .getClient()
                                              .getTextureManager()
                                              .getDynamicTextureLocation(e[0], new DynamicTexture(image));
                               } catch (IOException ex) {
                                   InterworldSplashscreen.logger.warn(String.format(
                                       "Splash for world %s not found, trying to use fallback splash of Overworld",
                                       e[0]
                                   ));
                                   if (Objects.equals(e[0], "Overworld")) {
                                       InterworldSplashscreen.logger.error(String.format(
                                           "Trying to load fallback texture but it was requested for Overworld. Please check %s directory",
                                           splashesDir
                                       ));
                                       throw new RuntimeException(ex);
                                   }
                                   ;

                                   try {
                                       image = ImageIO.read(new File(splashesDir, "Overworld.png"));
                                       return FMLClientHandler.instance()
                                                  .getClient()
                                                  .getTextureManager()
                                                  .getDynamicTextureLocation(e[0], new DynamicTexture(image));
                                   } catch (IOException exc) {
                                       throw new RuntimeException(exc);
                                   }
                               }

                           },
                           (prev, next) -> next,
                           HashMap::new
                       ));

        loadingTexts = Arrays.stream(splashesTextsRaw)
                           .map(entry -> entry.split(":", 2))
                           .collect(Collectors.toMap(
                               e -> e[0],
                               e -> e[1],
                               (prev, next) -> next,
                               HashMap::new
                           ));
        try {
            BufferedImage image = ImageIO.read(new File(splashesDir, String.format("%s.png", defaultSplashName)));
            defaultSplash = FMLClientHandler.instance()
                                .getClient()
                                .getTextureManager()
                                .getDynamicTextureLocation("defaultSplashScreen", new DynamicTexture(image));
        } catch (IOException ignored) {
        }
    }
}
