package com.beanfeed.tdsword.screen;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.screen.TDSwordGUI.TDSwordMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, TransDimensionalSword.MODID);
    public static final RegistryObject<MenuType<TDSwordMenu>> TD_SWORD_MENU = registerMenuType(TDSwordMenu::new, "td_sword_menu");

    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }
}
