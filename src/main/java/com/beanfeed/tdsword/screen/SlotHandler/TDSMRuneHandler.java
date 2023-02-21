package com.beanfeed.tdsword.screen.SlotHandler;

import com.beanfeed.tdsword.items.TDItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TDSMRuneHandler extends SlotItemHandler {
    public TDSMRuneHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return (pStack.is(TDItems.Rune.get()));
    }
}
