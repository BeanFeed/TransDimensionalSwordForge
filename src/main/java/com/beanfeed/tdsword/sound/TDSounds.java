package com.beanfeed.tdsword.sound;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TransDimensionalSword.MODID);
    public static final RegistryObject<SoundEvent> TD_IGNITE = createSoundEvent("tdignite");

    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> new SoundEvent(new ResourceLocation(TransDimensionalSword.MODID, soundName)));
    }
    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
