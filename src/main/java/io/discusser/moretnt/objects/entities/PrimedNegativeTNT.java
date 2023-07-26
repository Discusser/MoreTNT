package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.explosions.NegativeExplosion;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import io.discusser.moretnt.objects.registration.MoreTNTItems;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PrimedNegativeTNT extends BasePrimedTNT {
    public PrimedNegativeTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // Override with your own EntityType
    public PrimedNegativeTNT(Level pLevel, double pX, double pY, double pZ, float size, boolean fire) {
        this(MoreTNTEntities.NEGATIVE_TNT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.size = size;
        this.fire = fire;
    }

    // Override PrimedTnt constructor for compatibility
    public PrimedNegativeTNT(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(pLevel, pX, pY, pZ, DEFAULT_SIZE, DEFAULT_FIRE);
    }

    @Override
    public Explosion createExplosion(double x, double y, double z) {
        return new NegativeExplosion(this.level, null, null, null, x, y, z, this.size,
                this.fire, Explosion.BlockInteraction.BREAK);
    }
}
