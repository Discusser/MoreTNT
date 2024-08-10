package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.network.CustomClientboundExplodePacket;
import io.discusser.moretnt.network.MoreTNTPacketHandler;
import io.discusser.moretnt.objects.PrimedTNTObject;
import io.discusser.moretnt.objects.blocks.BaseTNTBlock;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

public class BasePrimedTNT extends PrimedTnt {
    public static final float DEFAULT_SIZE = 4.0F;
    public static final boolean DEFAULT_FIRE = true;
    public static final Direction DEFAULT_DIRECTION = Direction.NORTH;
    public Direction facing = DEFAULT_DIRECTION;
    public float size = DEFAULT_SIZE;
    public boolean fire = DEFAULT_FIRE;
    public BaseTNTBlock block;

    public BasePrimedTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BasePrimedTNT(EntityType<? extends PrimedTnt> entityType, BaseTNTBlock block, Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        this(entityType, pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.block = block;
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.size = size;
        this.fire = fire;
        this.facing = facing;
    }

    @Override
    protected void explode() {
        double pX = this.getX();
        double pY = this.getY(0.0625);
        double pZ = this.getZ();
        BaseExplosion explosion = this.createExplosion(pX, pY, pZ);

        if (ForgeEventFactory.onExplosionStart(this.level(), explosion)) {
            return;
        }

        explosion.explode();
        explosion.finalizeExplosion(true);

        if (this.level() instanceof ServerLevel level) {
            for (ServerPlayer serverplayer : level.players()) {
                if (serverplayer.distanceToSqr(pX, pY, pZ) < 4096.0D) {
                    CustomClientboundExplodePacket packet = new CustomClientboundExplodePacket(pX, pY, pZ, this.size,
                            explosion.getToBlow(), explosion.getHitPlayers().get(serverplayer), explosion.soundEvent.getLocation());
                    MoreTNTPacketHandler.sendToPlayer(packet, serverplayer);
                }
            }
        }
    }

    public BaseExplosion createExplosion(double x, double y, double z) {
        PrimedTNTObject primedTNTObject = MoreTNT.blockToPrimedTNTMap.get(this.block);
        return primedTNTObject.explosionFactory.create(this.level(), null, null, null,
                x, y, z, this.size, this.fire, Explosion.BlockInteraction.DESTROY, primedTNTObject.sound.get());
    }
}
