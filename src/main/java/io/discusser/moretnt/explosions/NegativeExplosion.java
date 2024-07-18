package io.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NegativeExplosion extends BaseExplosion {
    public List<BlockPos> blocksToPlace = new ArrayList<>();

    public NegativeExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
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
            this.blocksToPlace.add(blockpos);
        }
    }

    @Override
    void onExplosionIteration(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        if (this.level.getBlockState(blockpos).isAir()) {
            set.add(blockpos);
        }
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        this.blocksToPlace = this.blocksToPlace.stream().distinct().toList();

        int i = 0;

        for(BlockPos blockpos : this.toBlow) {
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.isAir() && this.level instanceof ServerLevel serverLevel) {
                if (blocksToPlace.size() == i) {
                    break;
                }
                BlockPos randomPos = blocksToPlace.get(i);
                serverLevel.setBlockAndUpdate(blockpos, this.level.getBlockState(randomPos));
                i++;
            }
        }

        for (BlockPos blockpos : this.blocksToPlace) {
            if (this.level instanceof ServerLevel) {
                this.level.getBlockState(blockpos).onBlockExploded(this.level, blockpos, this);
            }
        }
    }
}
