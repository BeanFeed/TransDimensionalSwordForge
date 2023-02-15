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
                Vec3 toGo = sword.getLastWaypoint();
                if(toGo != null && event.getEntity() != null && !event.getLevel().isClientSide()) {
                    BlockPos orgtoSpawn = event.getPos();
                    Vec3 toSpawn = new Vec3(orgtoSpawn.getX(), orgtoSpawn.getY(), orgtoSpawn.getZ());
                    TemporaryPortal portal = PortalUitls.makeTempPortal(1,2, event.getEntity());
                    //TemporaryPortal portal = TemporaryPortal.entityType.create(event.getLevel());
                    if (portal == null) {
                        TransDimensionalSword.LOGGER.info("Portal Null");
                        return;
                    };

                    //portal.markOneWay();
                    //portal.unbreakable = true;
                    //Vec3 toSpawn = new Vec3(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                    //portal.setOriginPos(toSpawn);
                    var pRot = sword.getLastWaypointRotation();
                    var cRot = event.getEntity().getYHeadRot();
                    double deltaY = pRot - cRot;
                    TransDimensionalSword.LOGGER.info("pRot: " + pRot + " cRot: " + cRot + " Dif: " + deltaY);
                    //double angleRads = Math.atan2(deltaY, 1.0);
                    //TransDimensionalSword.LOGGER.info("Degree: " + Math.toDegrees(angleRads));
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
                    /*portal.setOrientationAndSize(
                            new Vec3(1, 0, 0), // axisW
                            new Vec3(0, 1, 0), // axisH
                            1, // width
                            2 // height
                    );*/
                    McHelper.spawnServerEntity(portal);

                    PortalUitls.completeBiWayPortal(portal);
                    event.setCanceled(true);

                }
            }
        }
    }

    private static Vec3 getRightVec(Entity entity) {
        float yaw = entity.getYRot() + 90;
        float radians = -yaw * 0.017453292F;

        return new Vec3(
                Math.sin(radians), 0, Math.cos(radians)
        );
    }

}
