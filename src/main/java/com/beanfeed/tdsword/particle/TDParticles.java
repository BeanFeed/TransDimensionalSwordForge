package com.beanfeed.tdsword.particle;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TransDimensionalSword.MODID);

    public static final RegistryObject<SimpleParticleType> PORTAL_BORDER =
            PARTICLE_TYPES.register("portal_border", () -> new SimpleParticleType(true));
    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }
}
