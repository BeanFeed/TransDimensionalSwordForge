package com.beanfeed.tdsword;

import com.beanfeed.tdsword.entity.TDEntity_Types;
import com.beanfeed.tdsword.items.TDItems;
import com.beanfeed.tdsword.screen.TDMenuTypes;
import com.beanfeed.tdsword.screen.TDSwordGUI.TDSwordScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import qouteall.imm_ptl.core.render.PortalEntityRenderer;

import java.util.Arrays;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TransDimensionalSword.MODID)
public class TransDimensionalSword {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "tdsword";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "tdsword" namespace
    public TransDimensionalSword() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TDItems.register(modEventBus);
        TDEntity_Types.register(modEventBus);
        TDMenuTypes.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //Code from GoodPortals mod
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::initPortalRenderers);
        });


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(TDMenuTypes.TD_SWORD_MENU.get(), TDSwordScreen::new);
        }
    }

    //This code is from GoodPortals mod
    public void initPortalRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Arrays.stream(new EntityType<?>[]{
                TDEntity_Types.TEMP_PORTAL.get()
        }).peek(
                Validate::notNull
        ).forEach(
                entityType -> event.registerEntityRenderer(entityType, (EntityRendererProvider) PortalEntityRenderer::new
                ));
    }
    
}
