package io.discusser.moretnt.objects.entities;

import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public interface PrimedTNTFactory<T extends BasePrimedTNT> {
    T create(Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing);
}
