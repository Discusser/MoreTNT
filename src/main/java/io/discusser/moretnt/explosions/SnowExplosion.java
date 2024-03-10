package io.discusser.moretnt.explosions;

import com.google.common.collect.Sets;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SnowExplosion extends BaseExplosion {
    public SnowExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                          @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                          @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                          double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                          BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    public void explode() {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
        Set<BlockPos> set = Sets.newHashSet();

        for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float)j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float)k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float)l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for(; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            FluidState fluidstate = this.level.getFluidState(blockpos);
                            if (!this.level.isInWorldBounds(blockpos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this,
                                    this.level(), blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level(), blockpos, blockstate, f)) {
                                if (!MoreTNTBlocks.isTNT(blockstate)) {
                                    // lots of random checks
                                    BlockState below = this.level.getBlockState(blockpos.below());
                                    int layers = blockstate.getOptionalValue(SnowLayerBlock.LAYERS).orElse(0);
                                    boolean flag1 = blockstate.is(Blocks.SNOW) && layers != 8;
                                    boolean flag2 = !below.is(Blocks.SNOW)
                                            || blockstate.getOptionalValue(SnowLayerBlock.LAYERS).orElse(0) == 8;
                                    boolean flag3 = below.getBlock().properties.hasCollision && blockstate.getMaterial().isReplaceable()
                                            || layers == 1;
                                    if (flag1 || (flag2 && flag3)) {
                                        set.add(blockpos);
                                    }
                                }
                            }

                            if (MoreTNTBlocks.isTNT(blockstate)) {
                                neighborTnt.add(blockpos);
                            }

                            d4 += d0 * (double)0.3F;
                            d6 += d1 * (double)0.3F;
                            d8 += d2 * (double)0.3F;
                        }
                    }
                }
            }
        }

        this.toBlow.addAll(set);

        float f2 = this.radius * 2.0F;
        List<Entity> list = new ArrayList<>();
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level(), this, list, f2);
    }

    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        preFinalizeExplosion(pSpawnParticles, this.soundEvent);

        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel level) {
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

        postFinalizeExplosion();
    }
}
