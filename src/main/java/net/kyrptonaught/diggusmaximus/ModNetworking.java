package net.kyrptonaught.diggusmaximus;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ModNetworking {
    @ExpectPlatform
    public static void sendExcavatePacket(BlockPos pos, ResourceLocation id, Shape shape, Direction hitFace) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPackets() {
        throw new AssertionError();
    }
}
