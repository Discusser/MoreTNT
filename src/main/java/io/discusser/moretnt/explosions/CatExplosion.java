package io.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Set;

public class CatExplosion extends BaseExplosion {
    public CatExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                        @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                        @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                        double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                        BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.NO_RESTRICTIONS, this.level,
                blockpos, EntityType.CAT)) {
            set.add(blockpos);
        }
    }

    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        preFinalizeExplosion(pSpawnParticles, this.soundEvent);

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

                if (!ForgeEventFactory.checkSpawnPosition(cat, (ServerLevel)level, MobSpawnType.SPAWN_EGG)) {
                    continue;
                }

                cat.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.SPAWN_EGG, null, null);
                ((ServerLevel)level).addFreshEntityWithPassengers(cat);
                i++;
            }
        }

        postFinalizeExplosion();
    }
}
