package net.kyrptonaught.diggusmaximus.networking;

import lombok.Getter;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record ExcavateFailedPacket(Reason reason) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, "excavate_failed");
    public static final Type<ExcavateFailedPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ExcavateFailedPacket> CODEC = StreamCodec.of(ExcavateFailedPacket::write, ExcavateFailedPacket::from);

    private static ExcavateFailedPacket from(FriendlyByteBuf buf) {
        var reason = buf.readEnum(Reason.class);
        return new ExcavateFailedPacket(reason);
    }

    private static void write(FriendlyByteBuf buf, ExcavateFailedPacket packet) {
        buf.writeEnum(packet.reason());
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Getter
    public enum Reason {
        NOT_ENABLED("not_enabled"),
        SHAPE_NOT_ENABLED("shape_not_enabled"),
        ;

        private final String id;

        Reason(String id) {
            this.id = id;
        }
    }

    public static void handleClient(LocalPlayer player, ExcavateFailedPacket packet) {

    }
}
