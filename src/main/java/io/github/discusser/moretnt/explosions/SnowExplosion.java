package io.github.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SnowExplosion extends BaseExplosion {

    public SnowExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pX, pY, pZ, pRadius, pFire, pBlockInteraction, pSmallExplosionParticles, pLargeExplosionParticles, pExplosionSound);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        // lots of random checks
        BlockState blockstate = this.level.getBlockState(blockpos);
        BlockState below = this.level.getBlockState(blockpos.below());
        int layers = blockstate.getOptionalValue(SnowLayerBlock.LAYERS).orElse(0);
        boolean flag1 = blockstate.is(Blocks.SNOW) && layers != 8;
        boolean flag2 = !below.is(Blocks.SNOW)
                || blockstate.getOptionalValue(SnowLayerBlock.LAYERS).orElse(0) == 8;
        boolean flag3 = below.getBlock().properties().hasCollision && blockstate.canBeReplaced()
                || layers == 1;
        if (flag1 || (flag2 && flag3)) {
            set.add(blockpos);
        }
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                BlockState blockState = level.getBlockState(blockPos);
                int layers = this.level.random.nextInt(1, 3);
                if (blockState.is(Blocks.SNOW)) {
                    layers += blockState.getValue(SnowLayerBlock.LAYERS);
                    // layers <= 16 because this would only work if there is one additional snow block to place
                    // Either way layers should never be bigger than 10
                    if (8 < layers && layers <= 16) {
                        this.level.setBlockAndUpdate(blockPos.above(),
                                Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, layers - 8));
                        layers = 8;
                    }
                    blockState = blockState.setValue(SnowLayerBlock.LAYERS, layers);
                } else {
                    blockState = Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, layers);
                }
                this.level.setBlockAndUpdate(blockPos, blockState);
            }
        }
    }
}
