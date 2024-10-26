package io.github.discusser.moretnt.network;

import io.github.discusser.moretnt.MoreTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomClientboundExplodePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CustomClientboundExplodePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MoreTNT.MODID, "custom_clientbound_explode_packet")
    );

    public static final StreamCodec<FriendlyByteBuf, CustomClientboundExplodePacket> STREAM_CODEC = StreamCodec.ofMember(
            CustomClientboundExplodePacket::write, CustomClientboundExplodePacket::new
    );

    public double x;
    public double y;
    public double z;
    public float power;
    public List<BlockPos> toBlow;
    public Vec3 knockback;
    public ResourceLocation resourceLocation;

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public CustomClientboundExplodePacket(double x, double y, double z, float power, List<BlockPos> toBlow, Vec3 knockback, ResourceLocation resourceLocation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = power;
        this.toBlow = toBlow;
        this.knockback = knockback;
        this.resourceLocation = resourceLocation;
    }

    public CustomClientboundExplodePacket(FriendlyByteBuf pBuffer) {
        this.x = pBuffer.readDouble();
        this.y = pBuffer.readDouble();
        this.z = pBuffer.readDouble();
        this.power = pBuffer.readFloat();
        this.toBlow = pBuffer.readList((buf) -> {
            int x = buf.readByte() + Mth.floor(this.x);
            int y = buf.readByte() + Mth.floor(this.y);
            int z = buf.readByte() + Mth.floor(this.z);
            return new BlockPos(x, y, z);
        });
        this.knockback = pBuffer.readVec3();
        this.resourceLocation = pBuffer.readResourceLocation();
    }

    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat((float) this.x);
        pBuffer.writeFloat((float) this.y);
        pBuffer.writeFloat((float) this.z);
        pBuffer.writeFloat(this.power);
        pBuffer.writeCollection(this.toBlow, (buf, pos) -> {
            int x = pos.getX() - Mth.floor(this.x);
            int y = pos.getY() - Mth.floor(this.y);
            int z = pos.getZ() - Mth.floor(this.z);
            buf.writeByte(x);
            buf.writeByte(y);
            buf.writeByte(z);
        });
        pBuffer.writeVec3(this.knockback);
        pBuffer.writeResourceLocation(this.resourceLocation);
    }
}
