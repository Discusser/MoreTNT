package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.explosions.*;
import io.discusser.moretnt.objects.PrimedTNTObject;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;

public class PrimedTNTObjects {
    public static final PrimedTNTObject NEGATIVE_TNT = new PrimedTNTObject(MoreTNTEntities.NEGATIVE_TNT, NegativeExplosion::new, MoreTNTSounds.GENERIC_EXPLODE_REVERSED);
    public static final PrimedTNTObject NEGATIVE_TNT_4X = new PrimedTNTObject(MoreTNTEntities.NEGATIVE_TNT_4X, NegativeExplosion::new, MoreTNTSounds.GENERIC_EXPLODE_REVERSED);
    public static final PrimedTNTObject SHUFFLE_TNT = new PrimedTNTObject(MoreTNTEntities.SHUFFLE_TNT, ShuffleExplosion::new, SoundEvents.CHORUS_FRUIT_TELEPORT);
    public static final PrimedTNTObject SHUFFLE_TNT_4X = new PrimedTNTObject(MoreTNTEntities.SHUFFLE_TNT_4X, ShuffleExplosion::new, SoundEvents.CHORUS_FRUIT_TELEPORT);
    public static final PrimedTNTObject SNOW_TNT = new PrimedTNTObject(MoreTNTEntities.SNOW_TNT, SnowExplosion::new, SoundEvents.SNOW_BREAK);
    public static final PrimedTNTObject SNOW_TNT_4X = new PrimedTNTObject(MoreTNTEntities.SNOW_TNT_4X, SnowExplosion::new, SoundEvents.SNOW_BREAK);
    public static final PrimedTNTObject CAT_TNT = new PrimedTNTObject(MoreTNTEntities.CAT_TNT, CatExplosion::new, SoundEvents.CAT_PURREOW);
    public static final PrimedTNTObject DOME_TNT = new PrimedTNTObject(MoreTNTEntities.DOME_TNT, (pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent) -> new SphereExplosion(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent, Blocks.GLASS, false), SoundEvents.GLASS_PLACE);
    public static final PrimedTNTObject FIRE_TNT = new PrimedTNTObject(MoreTNTEntities.FIRE_TNT, FireExplosion::new, SoundEvents.FIRECHARGE_USE);
    public static final PrimedTNTObject KNOCKBACK_TNT = new PrimedTNTObject(MoreTNTEntities.KNOCKBACK_TNT, KnockbackExplosion::new, SoundEvents.PLAYER_ATTACK_KNOCKBACK);
    public static final PrimedTNTObject WATER_TNT = new PrimedTNTObject(MoreTNTEntities.WATER_TNT, WaterExplosion::new, SoundEvents.BUCKET_EMPTY);
    public static final PrimedTNTObject LAVA_TNT = new PrimedTNTObject(MoreTNTEntities.LAVA_TNT, LavaExplosion::new, SoundEvents.BUCKET_EMPTY_LAVA);
    public static final PrimedTNTObject COBBLESTONE_TNT = new PrimedTNTObject(MoreTNTEntities.COBBLESTONE_TNT, (pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent) -> new SphereExplosion(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent, Blocks.COBBLESTONE), SoundEvents.FIRE_EXTINGUISH);
    public static final PrimedTNTObject STONE_TNT = new PrimedTNTObject(MoreTNTEntities.STONE_TNT, (pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent) -> new SphereExplosion(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent, Blocks.STONE), SoundEvents.FIRE_EXTINGUISH);
    public static final PrimedTNTObject OBSIDIAN_TNT = new PrimedTNTObject(MoreTNTEntities.OBSIDIAN_TNT, (pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent) -> new SphereExplosion(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent, Blocks.OBSIDIAN), SoundEvents.FIRE_EXTINGUISH);
}
