package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import net.minecraft.sounds.SoundEvent;

public interface IPrimedTNT {
    BaseExplosion createExplosion(double x, double y, double z);
    SoundEvent getSound();
}
