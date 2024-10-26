package net.kyrptonaught.diggusmaximus;

import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;

public class DiggusMaximusMod implements ModInitializer {
    public static final String MOD_ID = "diggusmaximus";

    @Override
    public void onInitialize() {
        ConfigHelper.registerConfig();
        StartExcavatePacket.registerReceivePacket();

//        ServerLifecycleEvents.SERVER_STARTED.register(server -> { // tags need to exist before we can generate the lookup
//            getGrouping().generateLookup();
//            getBlackList().generateLookup();
//        });
    }
}