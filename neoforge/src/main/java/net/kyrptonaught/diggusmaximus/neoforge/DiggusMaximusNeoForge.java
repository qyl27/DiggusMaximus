package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.ModNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(ModConstants.MOD_ID)
public class DiggusMaximusNeoForge {
    private final ModContainer container;

    public DiggusMaximusNeoForge(ModContainer container, IEventBus bus) {
        this.container = container;

        bus.addListener(this::setupClient);

        ConfigHelper.registerConfig();
        ModNetworking.registerPackets();
    }

    private void setupClient(FMLClientSetupEvent event) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (c, screen) -> ConfigHelper.getConfigScreen(screen));
    }
}
