package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedSnowTNT;
import io.discusser.moretnt.objects.entities.PrimedWaterTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnowTNTBlock extends BaseTNTBlock {
    public SnowTNTBlock() {
        super();
    }

    public SnowTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        return new PrimedSnowTNT(level, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, size, fire,
                level.getBlockState(blockPos).getOptionalValue(BaseTNTBlock.FACING).orElse(BasePrimedTNT.DEFAULT_DIRECTION));
    }
}
