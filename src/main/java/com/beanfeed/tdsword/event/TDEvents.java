package com.beanfeed.tdsword.event;

import com.beanfeed.tdsword.PortalUitls;
import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.entity.TemporaryPortal;
import com.beanfeed.tdsword.items.TDSword;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import qouteall.imm_ptl.core.McHelper;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.imm_ptl.core.portal.nether_portal.GeneralBreakablePortal;


public class TDEvents {



    @Mod.EventBusSubscriber(modid = TransDimensionalSword.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void LeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            if(event.getItemStack().getItem() instanceof TDSword sword) {
                //Do Code
                TransDimensionalSword.LOGGER.info("Swung Sword");
                Vec3 toGo = sword.getLastWaypoint();
                if(toGo != null && event.getEntity() != null && !event.getLevel().isClientSide()) {
                    BlockPos orgtoSpawn = event.getPos();
                    Vec3 toSpawn = new Vec3(orgtoSpawn.getX(), orgtoSpawn.getY(), orgtoSpawn.getZ());
                    TemporaryPortal portal = PortalUitls.makeTempPortal(1,2, event.getEntity());
                    //TemporaryPortal portal = (TemporaryPortal)port;
                    if (portal == null) return;
                    portal.markOneWay();
                    portal.unbreakable = true;
                    portal.setDestination(toGo);
                    portal.setDestinationDimension(sword.getLastDimension());
                    McHelper.spawnServerEntity(portal);
                    //PortalUitls.completeBiWayPortal(portal);
                    event.setCanceled(true);

                }
            }
        }
    }

}
