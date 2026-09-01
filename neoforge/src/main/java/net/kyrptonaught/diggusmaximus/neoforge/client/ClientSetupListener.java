package net.kyrptonaught.diggusmaximus.neoforge.client;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.client.ConfigClientHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT)
public class ClientSetupListener {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.getContainer().registerExtensionPoint(IConfigScreenFactory.class, (c, screen) -> ConfigClientHelper.getConfigScreen(screen));
    }
}
