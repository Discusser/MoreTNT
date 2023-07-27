package io.discusser.moretnt.explosions;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseExplosion extends Explosion {
    public static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    public final Explosion.BlockInteraction blockInteraction;
    public final boolean fire;
    public final Level level;
    public final double x;
    public final double y;
    public final double z;
    @Nullable
    public final Entity source;
    public final float radius;
    public final ExplosionDamageCalculator damageCalculator;
    public final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList<>();
    public final Map<Player, Vec3> hitPlayers = Maps.newHashMap();
    public final List<BlockPos> neighborTnt = new ArrayList<>();
    public final SoundEvent soundEvent;

    public BaseExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource,
             @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ,
                         float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
        this.level = pLevel;
        this.source = pSource;
        this.radius = pRadius;
        this.x = pToBlowX;
        this.y = pToBlowY;
        this.z = pToBlowZ;
        this.fire = pFire;
        this.blockInteraction = pBlockInteraction;
        this.damageCalculator = pDamageCalculator == null ? this.makeDamageCalculator(pSource) : pDamageCalculator;
        this.soundEvent = soundEvent;
    }

    public ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity pEntity) {
        return pEntity == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(pEntity);
    }

    public void preFinalizeExplosion(boolean pSpawnParticles, SoundEvent soundEvent) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(this.x, this.y, this.z, soundEvent, SoundSource.BLOCKS, 4.0F,
                    (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        if (pSpawnParticles) {
            if (!(this.radius < 2.0F)) {
                this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            }
        }


        Util.shuffle(this.toBlow, this.level.random);
    }

    public void postFinalizeExplosion() {
        for (BlockPos blockpos : this.neighborTnt) {
            if (this.level instanceof ServerLevel level) {
                level.getBlockState(blockpos).onBlockExploded(this.level, blockpos, this);
            }
        }
    }

    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        preFinalizeExplosion(pSpawnParticles, this.soundEvent);
        postFinalizeExplosion();
    }
}
