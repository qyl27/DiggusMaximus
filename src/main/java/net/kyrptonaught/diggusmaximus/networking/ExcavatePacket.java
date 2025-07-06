package net.kyrptonaught.diggusmaximus.networking;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ExcavatePacket(BlockPos pos, ResourceLocation id, Shape shape, Direction hitFace) implements CustomPacketPayload {
    public static final ResourceLocation IDENTIFIER = ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, "excavate");

    public static final Type<ExcavatePacket> PACKET_ID = new Type<>(IDENTIFIER);

    public static final StreamCodec<FriendlyByteBuf, ExcavatePacket> CODEC = StreamCodec.of(ExcavatePacket::write, ExcavatePacket::from);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

    public static ExcavatePacket from(FriendlyByteBuf buf) {
        var pos = buf.readBlockPos();
        var id = buf.readResourceLocation();
        var shape = buf.readEnum(Shape.class);
        var hitFace = buf.readEnum(Direction.class);
        return new ExcavatePacket(pos, id, shape, hitFace);
    }

    public static void write(FriendlyByteBuf buf, ExcavatePacket payload) {
        buf.writeBlockPos(payload.pos);
        buf.writeResourceLocation(payload.id);
        buf.writeEnum(payload.shape);
        buf.writeEnum(payload.hitFace);
    }
}
