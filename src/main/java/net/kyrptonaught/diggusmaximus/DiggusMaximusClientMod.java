package net.kyrptonaught.diggusmaximus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class DiggusMaximusClientMod implements ClientModInitializer {
    public static final KeyMapping EXCAVATE = new KeyMapping(ModConstants.KEY_EXCAVATE, GLFW.GLFW_KEY_GRAVE_ACCENT, ModConstants.KEY_CATEGORY);
    public static final KeyMapping SHAPED = new KeyMapping(ModConstants.KEY_SHAPED, GLFW.GLFW_KEY_UNKNOWN, ModConstants.KEY_CATEGORY);
    public static final KeyMapping CYCLE = new KeyMapping(ModConstants.KEY_CYCLE_SHAPE, GLFW.GLFW_KEY_UNKNOWN, ModConstants.KEY_CATEGORY);

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(clientWorld -> {
            var pressed = false;
            while (CYCLE.consumeClick()) {
                pressed = true;
            }

            var config = ConfigHelper.getConfig();
            if (config.shapes.enableShapes && pressed) {
                var shape = config.shapes.selectedShape;
                if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isShiftKeyDown()) {
                    shape = shape.prev();
                } else {
                    shape = shape.next();
                }
                config.shapes.selectedShape = shape;
                ConfigHelper.save();
                Minecraft.getInstance().player.displayClientMessage(Component.translatable(shape.getName()), true);
            }
        });

        KeyBindingHelper.registerKeyBinding(EXCAVATE);
        KeyBindingHelper.registerKeyBinding(SHAPED);
        KeyBindingHelper.registerKeyBinding(CYCLE);
    }
}
