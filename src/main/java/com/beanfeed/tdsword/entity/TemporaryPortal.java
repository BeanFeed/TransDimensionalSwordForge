package com.beanfeed.tdsword.entity;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import qouteall.imm_ptl.core.McHelper;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;

public class TemporaryPortal extends Portal {
    public TemporaryPortal(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void onEntityTeleportedOnServer(Entity entity) {
        super.onEntityTeleportedOnServer(entity);
        String name = "Entity";
        if(entity instanceof ServerPlayer sp) name = sp.getDisplayName().getString();
        TransDimensionalSword.LOGGER.info(name + " has used a portal");
        PortalManipulation.removeConnectedPortals(this, p -> blank());
        this.remove(RemovalReason.KILLED);
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
}
