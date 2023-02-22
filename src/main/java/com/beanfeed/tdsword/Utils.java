package com.beanfeed.tdsword;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static Vec3 BlockPosToVec3(BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    public static BlockPos Vec3ToBlockPos(Vec3 vec) {
        return new BlockPos(vec.x(), vec.y(), vec.z());
    }
}
