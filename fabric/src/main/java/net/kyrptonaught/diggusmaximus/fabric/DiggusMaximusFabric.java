package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.diggusmaximus.ModNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;

public class DiggusMaximusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigHelper.registerConfig();
        ModNetworking.registerPackets();
    }
}