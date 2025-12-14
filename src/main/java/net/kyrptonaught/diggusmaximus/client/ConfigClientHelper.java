package net.kyrptonaught.diggusmaximus.client;

import me.shedaniel.autoconfig.AutoConfigClient;
import net.kyrptonaught.diggusmaximus.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ConfigClientHelper {
    public static Screen getConfigScreen(Screen parent) {
        return AutoConfigClient.getConfigScreen(ModConfig.class, parent).get();
    }

    public static void showConfigScreen(Minecraft mc) {
        var parent = mc.screen;
        var screen = getConfigScreen(parent);
        mc.setScreen(screen);
    }
}
