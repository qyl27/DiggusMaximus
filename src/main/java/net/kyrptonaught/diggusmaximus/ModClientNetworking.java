package net.kyrptonaught.diggusmaximus;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;

public class ModClientNetworking {
    @ExpectPlatform
    public static void sendExcavatePacket(ExcavatePacket packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPackets() {
        throw new AssertionError();
    }
}
