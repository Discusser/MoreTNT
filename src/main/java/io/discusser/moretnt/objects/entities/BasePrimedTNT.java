package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.network.CustomClientboundExplodePacket;
import io.discusser.moretnt.network.MoreTNTPacketHandler;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;

import java.lang.reflect.Constructor;

public abstract class BasePrimedTNT extends PrimedTnt implements IPrimedTNT {
    public static final float DEFAULT_SIZE = 4.0F;
    public static final boolean DEFAULT_FIRE = true;
    public static final Direction DEFAULT_DIRECTION = Direction.NORTH;
    public Direction facing = DEFAULT_DIRECTION;
    public float size = DEFAULT_SIZE;
    public boolean fire = DEFAULT_FIRE;

    public BasePrimedTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // Override with your own EntityType
    public BasePrimedTNT(EntityType<? extends PrimedTnt> entityType, Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        this(entityType, pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.size = size;
        this.fire = fire;
        this.facing = facing;
    }

    // Override with your own Explosion class
//    public Explosion createExplosion(double x, double y, double z) {
//        return new Explosion(this.level, null, null, null, x, y, z, this.size, this.fire,
//                Explosion.BlockInteraction.BREAK);
//    }

    @Override
    protected void explode() {
        double pX = this.getX();
        double pY = this.getY(0.0625);
        double pZ = this.getZ();

        BaseExplosion explosion = this.createExplosion(pX, pY, pZ);

        if (ForgeEventFactory.onExplosionStart(this.level, explosion)) {
            return;
        }

        explosion.explode();
        explosion.finalizeExplosion(true);

        if (this.level instanceof ServerLevel serverlevel) {
            for (ServerPlayer serverplayer : serverlevel.players()) {
                if (serverplayer.distanceToSqr(pX, pY, pZ) < 4096.0D) {
                    CustomClientboundExplodePacket packet = new CustomClientboundExplodePacket(pX, pY, pZ, this.size,
                            explosion.getToBlow(), explosion.getHitPlayers().get(serverplayer), explosion.soundEvent.getLocation());
                    MoreTNTPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverplayer), packet);
                }
            }
        }
    }

    public BaseExplosion createExplosion(double x, double y, double z) {
        Constructor<? extends BaseExplosion> constructor = getExplosionConstructor();

        try {
            return constructor.newInstance(this.level, null, null, null, x, y, z, this.size,
                    this.fire, Explosion.BlockInteraction.DESTROY, this.getSound());
        } catch (Exception e) {
            MoreTNT.LOGGER.error("Tried calling constructor for class " + getExplosionClass() + " but failed: " + e.getMessage());
        }

        return null;
    }

    public Constructor<? extends BaseExplosion> getExplosionConstructor() {
        try {
            return getExplosionClass().getDeclaredConstructor(Level.class, Entity.class, DamageSource.class,
                    ExplosionDamageCalculator.class, double.class, double.class, double.class,
                    float.class, boolean.class, Explosion.BlockInteraction.class, SoundEvent.class);
        } catch (Exception e) {
            MoreTNT.LOGGER.error("Tried getting constructor from class " + getExplosionClass() + " but failed: " + e.getMessage());
        }

        return null;
    }

    public Class<? extends BaseExplosion> getExplosionClass() {
        return BaseExplosion.class;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.TNT_PRIMED;
    }
}
