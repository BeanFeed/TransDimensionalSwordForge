package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TransDimensionalSword.MODID);

    public static final RegistryObject<TDSword> TDSword = ITEMS.register("tdsword",
            () -> new TDSword(new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Rune> Rune = ITEMS.register("rune",
            () -> new Rune(new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeModeTab.TAB_MISC)));


    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
