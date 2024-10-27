package net.kyrptonaught.diggusmaximus.networking;

import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ExcavatePacket(BlockPos pos, ResourceLocation id, Direction facing,
                             int shape) implements CustomPacketPayload {
    public static final ResourceLocation IDENTIFIER = ResourceLocation.fromNamespaceAndPath(DiggusMaximusMod.MOD_ID, "start_excavate_packet");

    public static final Type<ExcavatePacket> PACKET_ID = new Type<>(IDENTIFIER);

    public static final StreamCodec<FriendlyByteBuf, ExcavatePacket> CODEC = StreamCodec.of(ExcavatePacket::write, ExcavatePacket::from);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

    public static ExcavatePacket from(FriendlyByteBuf buf) {
        var pos = buf.readBlockPos();
        var id = buf.readResourceLocation();
        var facingId = buf.readInt();
        var facing = facingId == -1 ? null : Direction.from3DDataValue(facingId);
        var shape = buf.readInt();
        return new ExcavatePacket(pos, id, facing, shape);
    }

    public static void write(FriendlyByteBuf buf, ExcavatePacket payload) {
        buf.writeBlockPos(payload.pos);
        buf.writeResourceLocation(payload.id);
        buf.writeInt(payload.facing == null ? -1 : payload.facing.get3DDataValue());
        buf.writeInt(payload.shape);
    }
}
