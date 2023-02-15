package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.mojang.math.Quaternion;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.openjdk.nashorn.internal.ir.Block;

public class TDSword extends Item {

    private Vec3 lastWaypoint = null;
    private float lastWaypointYRotation = 0.0f;
    private ResourceKey<Level> lastDim = null;
    public TDSword(Properties pProperties) {
        super(pProperties);
    }

    public Vec3 getLastWaypoint() { return lastWaypoint; }
    public float getLastWaypointRotation() { return lastWaypointYRotation; }
    public ResourceKey<Level> getLastDimension() { return lastDim; }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //TransDimensionalSword.LOGGER.info(pUsedHand.toString());
        lastDim = pLevel.dimension();
        var tempLastWaypoint = pPlayer.position();
        lastWaypointYRotation = pPlayer.getYHeadRot();
        //TransDimensionalSword.LOGGER.info("Sword: " + tempLastWaypointRotation);
        lastWaypoint = new Vec3(((int)tempLastWaypoint.x) + 0.5, tempLastWaypoint.y + 1, ((int)tempLastWaypoint.z) + 0.5);
        //lastWaypointRotation = new Vec3(Math.round(tempLastWaypointRotation), 0, Math.round(tempLastWaypointRotation);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

}
