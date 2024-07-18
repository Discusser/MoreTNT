package io.discusser.moretnt.network;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class CustomClientboundExplodePacket {
    public final double x;
    public final double y;
    public final double z;
    public final float power;
    public final List<BlockPos> toBlow;
    public final float knockbackX;
    public final float knockbackY;
    public final float knockbackZ;
    public final ResourceLocation resourceLocation;

    public CustomClientboundExplodePacket(double pX, double pY, double pZ, float pPower, List<BlockPos> pToBlow, @Nullable Vec3 pKnockback, ResourceLocation resourceLocation) {
        this.x = pX;
        this.y = pY;
        this.z = pZ;
        this.power = pPower;
        this.toBlow = Lists.newArrayList(pToBlow);
        if (pKnockback != null) {
            this.knockbackX = (float) pKnockback.x;
            this.knockbackY = (float) pKnockback.y;
            this.knockbackZ = (float) pKnockback.z;
        } else {
            this.knockbackX = 0.0F;
            this.knockbackY = 0.0F;
            this.knockbackZ = 0.0F;
        }
        this.resourceLocation = resourceLocation;
    }

    public CustomClientboundExplodePacket(FriendlyByteBuf pBuffer) {
        this.x = pBuffer.readFloat();
        this.y = pBuffer.readFloat();
        this.z = pBuffer.readFloat();
        this.power = pBuffer.readFloat();
        this.toBlow = pBuffer.readList((buf) -> {
            int x = buf.readByte() + Mth.floor(this.x);
            int y = buf.readByte() + Mth.floor(this.y);
            int z = buf.readByte() + Mth.floor(this.z);
            return new BlockPos(x, y, z);
        });
        this.knockbackX = pBuffer.readFloat();
        this.knockbackY = pBuffer.readFloat();
        this.knockbackZ = pBuffer.readFloat();
        this.resourceLocation = pBuffer.readResourceLocation();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
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
        pBuffer.writeFloat(this.knockbackX);
        pBuffer.writeFloat(this.knockbackY);
        pBuffer.writeFloat(this.knockbackZ);
        pBuffer.writeResourceLocation(this.resourceLocation);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CustomClientboundExplodePacketHandler.handle(this, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
