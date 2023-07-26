package io.discusser.moretnt.objects.entities;

import net.minecraft.world.level.Explosion;

public interface IPrimedTNT {
    Explosion createExplosion(double x, double y, double z);
}
