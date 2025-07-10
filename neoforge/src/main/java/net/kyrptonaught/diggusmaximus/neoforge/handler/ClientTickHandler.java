package net.kyrptonaught.diggusmaximus.neoforge.handler;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.client.DiggusMaximusClient;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID)
public class ClientTickHandler {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        var mc = Minecraft.getInstance();
        DiggusMaximusClient.onClientTick(mc);
    }

    @SubscribeEvent
    public static void onRegisterKey(RegisterKeyMappingsEvent event) {
        event.register(DiggusMaximusClient.EXCAVATE);
        event.register(DiggusMaximusClient.SHAPED);
        event.register(DiggusMaximusClient.CYCLE);
    }
}
