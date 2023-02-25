package com.beanfeed.tdsword.entity;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDEntity_Types {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, TransDimensionalSword.MODID);

    public static final RegistryObject<EntityType<TemporaryPortal>> TEMP_PORTAL = ENTITY_TYPES.register("tportal", () -> EntityType.Builder.of(TemporaryPortal::new, MobCategory.MISC).sized(1.0f, 1.0f).build("tdswordtportal"));
    public static void register(IEventBus bus) {

        ENTITY_TYPES.register(bus);
    }
}
