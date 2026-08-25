package net.kyrptonaught.diggusmaximus.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.kyrptonaught.diggusmaximus.client.DiggusMaximusClient;

public class DiggusMaximusClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(DiggusMaximusClient::onClientTick);

        KeyMappingHelper.registerKeyMapping(DiggusMaximusClient.EXCAVATE);
        KeyMappingHelper.registerKeyMapping(DiggusMaximusClient.CYCLE);
    }
}
