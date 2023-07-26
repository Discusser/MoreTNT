package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
            MoreTNT.MODID);

    public static final RegistryObject<SoundEvent> GENERIC_EXPLODE_REVERSED = registerSoundEvent("generic_explode_reversed");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(MoreTNT.MODID, name)));
    }
}
