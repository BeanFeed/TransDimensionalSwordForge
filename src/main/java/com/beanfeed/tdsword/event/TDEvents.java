package com.beanfeed.tdsword.event;

import com.beanfeed.tdsword.PortalUtils;
import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.entity.TemporaryPortal;
import com.beanfeed.tdsword.items.TDSword;
import com.beanfeed.tdsword.particle.PortalBorderParticle;
import com.beanfeed.tdsword.particle.TDParticles;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import qouteall.imm_ptl.core.McHelper;

public class TDEvents {



    @Mod.EventBusSubscriber(modid = TransDimensionalSword.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeModEventBusEvents {
        @SubscribeEvent
        public static void LeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            if(event.getItemStack().getItem() instanceof TDSword sword) {
                //Do Code
                //TransDimensionalSword.LOGGER.info("Swung Sword");
                Vec3 toGo = sword.getLastWaypoint(event.getItemStack());
                TransDimensionalSword.LOGGER.info(String.valueOf(toGo));
                //toGo != null checks if the sword has a saved waypoint. If not then don't run
                //event.getEntity() != null double checks that the entity isn't null
                //!event.getLevel().isClientSide() makes sure the code is only ran on the server
                if(sword.getGoldAmount(event.getItemStack()) == 0) return;
                if(toGo != null && event.getEntity() != null && !event.getLevel().isClientSide() && event.getFace() == Direction.UP) {
                    //gets block to spawn portal on top of

                    BlockPos orgtoSpawn = event.getPos();
                    Vec3 toSpawn = new Vec3(orgtoSpawn.getX(), orgtoSpawn.getY(), orgtoSpawn.getZ());
                    //makes new portal object with dimensions
                    TemporaryPortal portal = PortalUtils.makeTempPortal(1,2, event.getEntity());

                    if (portal == null) {
                        //Failed to make portal
                        //TransDimensionalSword.LOGGER.info("Portal Null");
                        return;
                    };

                    //gets the players head rotation when they saved the waypoint

                    var pRot = sword.getLastWaypointRotation(event.getItemStack());
                    //TransDimensionalSword.LOGGER.info(pRot + " Saved Rot");
                    //if(pRot == null) return;
                    //gets the players current head rotation
                    var cRot = event.getEntity().getYHeadRot();
                    toGo = new Vec3(toGo.x + 0.5, toGo.y, toGo.z + 0.5);
                    double deltaY = pRot - cRot;
                    //TransDimensionalSword.LOGGER.info(deltaY + " Current Rotation Offset");
                    double degrees = Math.round(deltaY / 90) * 90;
                    //TransDimensionalSword.LOGGER.info("Degree Rounded: " + degrees);
                    Quaternion rot = degrees != 0 ? new Quaternion(
                            new Vector3f(0,1,0),
                            (float)degrees * -1,
                            true
                    ) : null;

                    portal.rotation = rot;

                    portal.setDestination(toGo);
                    portal.setDestinationDimension(sword.getLastDimension(event.getItemStack()));
                    //Spawns the portal on the server side
                    McHelper.spawnServerEntity(portal);
                    //Creates another portal at the new portals destination
                    PortalUtils.completeBiWayPortal(portal);
                    var goldAmount = sword.getGoldAmount(event.getItemStack());
                    sword.setGoldAmount(event.getItemStack(), goldAmount - 1);
                    event.setCanceled(true);
                }
                //Cancels the event, so it doesn't break the block in creative mode

            }
        }
    }

    @Mod.EventBusSubscriber(modid = TransDimensionalSword.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {

        @SubscribeEvent
        public static void RegisterParticleProvidersEvent(final RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(TDParticles.PORTAL_BORDER.get(), PortalBorderParticle.Factory::new);
        }
    }


}
