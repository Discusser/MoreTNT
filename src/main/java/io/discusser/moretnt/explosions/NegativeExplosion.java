package io.discusser.moretnt.explosions;

import com.google.common.collect.Sets;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NegativeExplosion extends BaseExplosion {
    public List<BlockPos> blocksToPlace = new ArrayList<>();

    public NegativeExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource, double pToBlowX,
                             double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                             BlockInteraction pBlockInteraction, List<BlockPos> pPositions) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, pPositions);
    }

    public NegativeExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource, double pToBlowX,
                             double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                             BlockInteraction pBlockInteraction) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
    }

    public NegativeExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                             @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                             @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                             double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                             BlockInteraction pBlockInteraction) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
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
                                    this.level, blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                                if (!blockstate.isAir() &&
                                        !MoreTNTBlocks.isTNT(blockstate)) {
                                    blocksToPlace.add(blockpos);
                                }
                            }

                            if (blockstate.isAir()) {
                                set.add(blockpos);
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

        this.blocksToPlace = this.blocksToPlace.stream().distinct().toList();

        this.toBlow.addAll(set);

        float f2 = this.radius * 2.0F;
        int k1 = Mth.floor(this.x - (double)f2 - 1.0D);
        int l1 = Mth.floor(this.x + (double)f2 + 1.0D);
        int i2 = Mth.floor(this.y - (double)f2 - 1.0D);
        int i1 = Mth.floor(this.y + (double)f2 + 1.0D);
        int j2 = Mth.floor(this.z - (double)f2 - 1.0D);
        int j1 = Mth.floor(this.z + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
        Vec3 vec3 = new Vec3(this.x, this.y, this.z);

        for (Entity entity : list) {
            if (!entity.ignoreExplosion()) {
                double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
                if (d12 <= 1.0D) {
                    double d5 = entity.getX() - this.x;
                    double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                    double d9 = entity.getZ() - this.z;
                    double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0D) {
                        d5 /= d13;
                        d7 /= d13;
                        d9 /= d13;
                        double d14 = getSeenPercent(vec3, entity);
                        double d10 = (1.0D - d12) * d14;
                        entity.hurt(this.getDamageSource(),
                                (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
                        }

                        entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                        if (entity instanceof Player player) {
                            if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                this.hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        preFinalizeExplosion(pSpawnParticles);

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
            if (this.level instanceof ServerLevel level) {
                level.getBlockState(blockpos).onBlockExploded(this.level, blockpos, this);
            }
        }

        postFinalizeExplosion();
    }
}
