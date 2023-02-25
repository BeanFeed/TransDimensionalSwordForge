package com.beanfeed.tdsword.entity;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import qouteall.imm_ptl.core.McHelper;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.imm_ptl.core.portal.nether_portal.GeneralBreakablePortal;

public class TemporaryPortal extends Portal {
    public static final EntityType<TemporaryPortal> entityType = TDEntity_Types.TEMP_PORTAL.get();
    public double targetWidth = 1;
    private int wait = 1;
    public TemporaryPortal(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void onEntityTeleportedOnServer(Entity entity) {
        super.onEntityTeleportedOnServer(entity);
        String name = "Entity";
        if(entity instanceof ServerPlayer sp) name = sp.getDisplayName().getString();
        //TransDimensionalSword.LOGGER.info(name + " has used a portal");
        PortalManipulation.removeConnectedPortals(this, p -> blank());
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void tick() {
        super.tick();
        if(wait == 0 && this.width != targetWidth  && !this.level.isClientSide()) {
            this.width = targetWidth;
            reloadPortal();
        } else wait--;
    }
    private void blank(){}
    /*
    @Override
    public void tick() {
        if(!this.level.isClientSide()) {
            if(timeTillDelete > 0) {
                timeTillDelete--;
                //TransDimensionalSword.LOGGER.info("Tick");
            }
            else {
                this.remove(RemovalReason.KILLED);
            }
        }
        super.tick();

    }

     */
    private void reloadPortal() {
        this.rectifyClusterPortals();
        this.reloadAndSyncToClient();
    }
}
