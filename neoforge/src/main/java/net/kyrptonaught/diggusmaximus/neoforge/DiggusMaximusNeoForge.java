package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.ModNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.neoforged.fml.common.Mod;

@Mod(ModConstants.MOD_ID)
public class DiggusMaximusNeoForge {
    public DiggusMaximusNeoForge() {
        ConfigHelper.registerConfig();
        ModNetworking.registerPackets();
    }
}
