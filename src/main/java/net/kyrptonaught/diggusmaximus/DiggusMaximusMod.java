package net.kyrptonaught.diggusmaximus;

import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.networking.StartExcavatePacket;

public class DiggusMaximusMod implements ModInitializer {
    public static final String MOD_ID = "diggusmaximus";

    @Override
    public void onInitialize() {
        ConfigHelper.registerConfig();
        StartExcavatePacket.registerReceivePacket();
    }
}