package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModClientNetworking;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ModConstants.MOD_ID, dist = Dist.CLIENT)
public class DiggusMaximusClientNeoForge {
    public DiggusMaximusClientNeoForge() {
        ModClientNetworking.registerPackets();
    }
}
