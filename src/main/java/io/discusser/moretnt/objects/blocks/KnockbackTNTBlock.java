package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedKnockbackTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class KnockbackTNTBlock extends BaseTNTBlock {
    public KnockbackTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        return new PrimedKnockbackTNT(level, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, size, fire,
                this.getFacing(level, blockPos));
    }
}

