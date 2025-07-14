package net.kyrptonaught.diggusmaximus;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.server.level.ServerPlayer;

public class ModNetworking {
    @ExpectPlatform
    public static void sendExcavatePacket(ExcavatePacket packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendFailedPacket(ServerPlayer player, ExcavateFailedPacket packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPackets() {
        throw new AssertionError();
    }
}
