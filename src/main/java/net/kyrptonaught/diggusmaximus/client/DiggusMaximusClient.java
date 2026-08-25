package net.kyrptonaught.diggusmaximus.client;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class DiggusMaximusClient {
    public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, "keys"));

    public static final KeyMapping EXCAVATE = new KeyMapping(ModConstants.KEY_EXCAVATE, GLFW.GLFW_KEY_GRAVE_ACCENT, KEY_CATEGORY);
    public static final KeyMapping CYCLE = new KeyMapping(ModConstants.KEY_CYCLE_SHAPE, GLFW.GLFW_KEY_UNKNOWN, KEY_CATEGORY);

    public static void onClientTick(Minecraft mc) {
        var cycled = false;
        while (CYCLE.consumeClick()) {
            cycled = true;
        }

        var config = ConfigHelper.getConfig();
        if (cycled) {
            var shape = config.client.selectedShape;
            if (mc.player != null && mc.player.isShiftKeyDown()) {
                shape = shape.prev();
            } else {
                shape = shape.next();
            }
            config.client.selectedShape = shape;
            ConfigHelper.save();
        }

        if (mc.player != null && (cycled || EXCAVATE.isDown())) {
            var shape = config.client.selectedShape;
            mc.player.sendOverlayMessage(Component.translatable(shape.getName()));
        }
    }
}
