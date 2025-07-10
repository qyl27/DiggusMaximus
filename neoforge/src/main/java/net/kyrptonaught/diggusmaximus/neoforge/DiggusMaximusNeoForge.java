package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.ModNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(ModConstants.MOD_ID)
public class DiggusMaximusNeoForge {
    public DiggusMaximusNeoForge(ModContainer container, IEventBus bus) {
        bus.addListener(this::setupClient);

        ConfigHelper.registerConfig();
        ModNetworking.registerPackets();
    }

    private void setupClient(FMLClientSetupEvent event) {
    }
}
