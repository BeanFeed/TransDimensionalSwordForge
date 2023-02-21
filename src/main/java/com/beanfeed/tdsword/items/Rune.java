package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class Rune extends Item {
    public Rune(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide() || !(pPlayer.getMainHandItem().getItem() instanceof Rune)) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        CompoundTag nbt = pPlayer.getMainHandItem().getOrCreateTag();
        if(nbt.contains("waypoint")) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        BlockPos pPos = new BlockPos(Math.floor(pPlayer.getX()), Math.floor(pPlayer.getY()), Math.floor(pPlayer.getZ()));
        CompoundTag waypoint = NbtUtils.writeBlockPos(pPos);
        //CompoundTag rotation = new CompoundTag();
        nbt.put("waypoint", waypoint);
        pLevel.
        nbt.putFloat("rotation", pPlayer.getYHeadRot());
        pPlayer.getMainHandItem().save(nbt);
        TransDimensionalSword.LOGGER.info("added");

        pPlayer.getMainHandItem().setHoverName(Component.translatable("item.tdsword.filled_rune"));
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }


}
