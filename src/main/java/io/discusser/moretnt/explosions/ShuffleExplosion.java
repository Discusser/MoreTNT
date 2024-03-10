package io.discusser.moretnt.explosions;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class ShuffleExplosion extends BaseExplosion {
    public ShuffleExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                            @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                            @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                            double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                            BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        if (!this.level.getBlockState(blockpos).isAir()) {
            set.add(blockpos);
        }
    }

    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        preFinalizeExplosion(pSpawnParticles, this.soundEvent);

        ObjectArrayList<BlockPos> toSwap = new ObjectArrayList<>(this.toBlow);

        if (this.level instanceof ServerLevel) {
            for (BlockPos pos : this.toBlow) {
                // Get random position
                int i = level.random.nextInt(toSwap.size());
                BlockPos randomPos = toSwap.remove(i);

                BlockState blockState = level.getBlockState(pos);
                BlockState randomBlockState = level.getBlockState(randomPos);

                blockState.onBlockExploded(level, pos, this);
                randomBlockState.onBlockExploded(level, randomPos, this);

                level.setBlockAndUpdate(randomPos, blockState);
                level.setBlockAndUpdate(pos, randomBlockState);
            }
        }


        postFinalizeExplosion();
    }
}
