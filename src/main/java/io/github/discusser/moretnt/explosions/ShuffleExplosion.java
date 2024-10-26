package io.github.discusser.moretnt.explosions;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ShuffleExplosion extends BaseExplosion {

    public ShuffleExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pX, pY, pZ, pRadius, pFire, pBlockInteraction, pSmallExplosionParticles, pLargeExplosionParticles, pExplosionSound);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        if (!this.level.getBlockState(blockpos).isAir()) {
            set.add(blockpos);
        }
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
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
    }
}
