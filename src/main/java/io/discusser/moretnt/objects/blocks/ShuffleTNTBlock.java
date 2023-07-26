package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedNegativeTNT;
import io.discusser.moretnt.objects.entities.PrimedShuffleTNT;
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

public class ShuffleTNTBlock extends BaseTNTBlock {
    public ShuffleTNTBlock() {
        super();
    }

    public ShuffleTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        return new PrimedShuffleTNT(level, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, size, fire);
    }
}
