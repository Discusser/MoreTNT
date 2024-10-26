package io.github.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class CatExplosion extends BaseExplosion {

    public CatExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pX, pY, pZ, pRadius, pFire, pBlockInteraction, pSmallExplosionParticles, pLargeExplosionParticles, pExplosionSound);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        if (SpawnPlacementTypes.NO_RESTRICTIONS.isSpawnPositionOk(this.level,
                blockpos, EntityType.CAT)) {
            set.add(blockpos);
        }
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        int i = 0;

        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                if (i > 8) {
                    break;
                }

                Cat cat = EntityType.CAT.create(level);

                if (cat == null) {
                    continue;
                }

                cat.moveTo(blockPos, 0.0F, 0.0F);

                if (!EventHooks.checkSpawnPosition(cat, (ServerLevel) level, MobSpawnType.SPAWN_EGG)) {
                    continue;
                }

                cat.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.SPAWN_EGG,
                        null);
                ((ServerLevel) level).addFreshEntityWithPassengers(cat);
                i++;
            }
        }
    }
}
