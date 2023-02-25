package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Rune extends Item {
    public Rune(Properties pProperties) {
        super(pProperties);
    }

    /*
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide() || !(pPlayer.getMainHandItem().getItem() instanceof Rune)) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        CompoundTag nbt = pPlayer.getMainHandItem().getOrCreateTag();
        if(nbt.contains("waypoint")) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        var tempPos = pPlayer.position();
        var pPos = new BlockPos(((int)tempPos.x), tempPos.y + 1, ((int)tempPos.z));
        TransDimensionalSword.LOGGER.info(String.valueOf(pPos));
        CompoundTag waypoint = NbtUtils.writeBlockPos(pPos);
        //CompoundTag rotation = new CompoundTag();
        nbt.put("waypoint", waypoint);

        nbt.putString("dimension", pLevel.dimension().location().toString());
        nbt.putFloat("rotation", pPlayer.getYHeadRot());
        pPlayer.getMainHandItem().save(nbt);
        TransDimensionalSword.LOGGER.info(pPlayer.getYHeadRot() + " Saved Rot");

        pPlayer.getMainHandItem().setHoverName(Component.translatable("item.tdsword.filled_rune"));
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
    */
    public static CompoundTag getWaypointNBT(ItemStack stack, Player pPlayer) {
        CompoundTag nbt = stack.getOrCreateTag();

        if(nbt.contains("waypoint")) { return nbt;}
        var position = pPlayer.position();
        var pPos = new BlockPos(((int)position.x), position.y + 1, ((int)position.z));
        //TransDimensionalSword.LOGGER.info(String.valueOf(pPos));
        CompoundTag waypoint = NbtUtils.writeBlockPos(pPos);
        //CompoundTag rotation = new CompoundTag();
        nbt.put("waypoint", waypoint);
        nbt.putString("dimension", pPlayer.level.dimension().location().toString());
        nbt.putFloat("rotation", pPlayer.getYHeadRot());
        return nbt;
    }

}
