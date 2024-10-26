package io.github.discusser.moretnt.objects.registration;

import io.github.discusser.moretnt.MoreTNT;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoreTNTSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT,
            MoreTNT.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> GENERIC_EXPLODE_REVERSED = registerSoundEvent("generic_explode_reversed");

    public static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MoreTNT.MODID, name)));
    }
}
