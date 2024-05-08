package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import net.minecraft.sounds.SoundEvent;

import java.lang.reflect.Constructor;

public interface IPrimedTNT {
    Constructor<? extends BaseExplosion> getExplosionConstructor();
    SoundEvent getSound();
}
