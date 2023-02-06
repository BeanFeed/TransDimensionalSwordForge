package com.beanfeed.tdsword;

import com.beanfeed.tdsword.entity.TemporaryPortal;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.IPMcHelper;
import qouteall.imm_ptl.core.platform_specific.IPRegistry;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.q_misc_util.Helper;

import java.util.Iterator;
import java.util.List;

public class PortalUitls {

    public static TemporaryPortal makeTempPortal(double width, double height, Entity entity) {
        Vec3 playerLook = entity.getLookAngle();
        Tuple<BlockHitResult, List<Portal>> rayTrace = IPMcHelper.rayTrace(entity.level, new ClipContext(entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(playerLook.scale(100.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity), true);
        BlockHitResult hitResult = (BlockHitResult)rayTrace.getA();
        List<Portal> hitPortals = (List)rayTrace.getB();
        if (IPMcHelper.hitResultIsMissedOrNull(hitResult)) {
            return null;
        } else {
            Portal hitPortal;
            for(Iterator var9 = hitPortals.iterator(); var9.hasNext(); playerLook = hitPortal.transformLocalVecNonScale(playerLook)) {
                hitPortal = (Portal)var9.next();
            }

            Direction lookingDirection = Helper.getFacingExcludingAxis(playerLook, hitResult.getDirection().getAxis());
            if (lookingDirection == null) {
                return null;
            } else {
                Vec3 axisH = Vec3.atLowerCornerOf(hitResult.getDirection().getNormal());
                Vec3 axisW = axisH.cross(Vec3.atLowerCornerOf(lookingDirection.getOpposite().getNormal()));
                Vec3 pos = Vec3.atCenterOf(hitResult.getBlockPos()).add(axisH.scale(0.5 + height / 2.0));
                Level world = hitPortals.isEmpty() ? entity.level : ((Portal)hitPortals.get(hitPortals.size() - 1)).getDestinationWorld();
                TemporaryPortal portal = new TemporaryPortal((EntityType) IPRegistry.PORTAL.get(), world);
                portal.setPosRaw(pos.x, pos.y, pos.z);
                portal.axisW = axisW;
                portal.axisH = axisH;
                portal.width = width;
                portal.height = height;
                return portal;
            }
        }
    }
}
