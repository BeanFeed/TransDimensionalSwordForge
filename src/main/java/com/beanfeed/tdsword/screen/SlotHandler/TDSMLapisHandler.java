package com.beanfeed.tdsword.screen.SlotHandler;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TDSMLapisHandler extends SlotItemHandler {
    public TDSMLapisHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return pStack.is(Items.LAPIS_LAZULI);
    }
}
