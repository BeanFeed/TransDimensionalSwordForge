package com.beanfeed.tdsword.event;

import com.beanfeed.tdsword.PortalUitls;
import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.entity.TemporaryPortal;
import com.beanfeed.tdsword.items.TDSword;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
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
                Vec3 toGo = sword.getLastWaypoint(event.getItemStack());
                //toGo != null checks if the sword has a saved waypoint. If not then don't run
                //event.getEntity() != null double checks that the entity isn't null
                //!event.getLevel().isClientSide() makes sure the code is only ran on the server
                if(toGo != null && event.getEntity() != null && !event.getLevel().isClientSide()) {
                    //gets block to spawn portal on top of
                    BlockPos orgtoSpawn = event.getPos();
                    Vec3 toSpawn = new Vec3(orgtoSpawn.getX(), orgtoSpawn.getY(), orgtoSpawn.getZ());
                    //makes new portal object with dimensions
                    TemporaryPortal portal = PortalUitls.makeTempPortal(1,2, event.getEntity());

                    if (portal == null) {
                        //Failed to make portal
                        TransDimensionalSword.LOGGER.info("Portal Null");
                        return;
                    };

                    //gets the players head rotation when they saved the waypoint
                    var pRot = sword.getLastWaypointRotation(event.getItemStack());
                    //if(pRot == null) return;
                    //gets the players current head rotation
                    var cRot = event.getEntity().getYHeadRot();

                    double deltaY = pRot - cRot;

                    double degrees = Math.round(deltaY / 90) * 90;
                    TransDimensionalSword.LOGGER.info("Degree Rounded: " + degrees);
                    Quaternion rot = degrees != 0 ? new Quaternion(
                            new Vector3f(0,1,0),
                            (float)degrees - 180,
                            true
                    ) : null;

                    portal.rotation = rot;
                    portal.setDestination(toGo);
                    portal.setDestinationDimension(sword.getLastDimension());
                    //Spawns the portal on the server side
                    McHelper.spawnServerEntity(portal);
                    //Creates another portal at the new portals destination
                    PortalUitls.completeBiWayPortal(portal);

                }
                //Cancels the event, so it doesn't break the block in creative mode
                event.setCanceled(true);
            }
        }
    }


}
