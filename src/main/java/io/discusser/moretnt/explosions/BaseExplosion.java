package io.discusser.moretnt.explosions;

import com.google.common.collect.Maps;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Sets;

import javax.annotation.Nullable;
import java.util.*;

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
    public final List<BlockPos> neighborTnt = new ArrayList<>();
    public final List<Entity> entitiesInRange = new ArrayList<>();
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

    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        set.add(blockpos);
    }

    void onExplosionIteration(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {}

    boolean canBlockExplode(BlockPos blockpos) {
        return true;
    }

    @Override
    public void explode() {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
        Set<BlockPos> set = Sets.newHashSet();

        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float) j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float) k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float) l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for (; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = BlockPos.containing(d4, d6, d8);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            FluidState fluidstate = this.level.getFluidState(blockpos);
                            if (!this.level.isInWorldBounds(blockpos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this,
                                    this.level, blockpos, blockstate, fluidstate);
                            if (optional.isPresent() && canBlockExplode(blockpos)) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos,
                                    blockstate, f)) {
                                if (!MoreTNTBlocks.isTNT(blockstate)) {
                                    onBlockExploded(set, blockpos, j, k, l);
                                }
                            }

                            onExplosionIteration(set, blockpos, j, k, l);

                            if (MoreTNTBlocks.isTNT(blockstate)) {
                                neighborTnt.add(blockpos);
                            }

                            d4 += d0 * (double) 0.3F;
                            d6 += d1 * (double) 0.3F;
                            d8 += d2 * (double) 0.3F;
                        }
                    }
                }
            }
        }

        this.toBlow.addAll(set);

        float f2 = this.radius * 2.0F;
        int k1 = Mth.floor(this.x - (double) f2 - 1.0D);
        int l1 = Mth.floor(this.x + (double) f2 + 1.0D);
        int i2 = Mth.floor(this.y - (double) f2 - 1.0D);
        int i1 = Mth.floor(this.y + (double) f2 + 1.0D);
        int j2 = Mth.floor(this.z - (double) f2 - 1.0D);
        int j1 = Mth.floor(this.z + (double) f2 + 1.0D);
        this.entitiesInRange.addAll(this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1)));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, entitiesInRange, f2);
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
            if (this.level instanceof ServerLevel) {
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
