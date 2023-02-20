package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.screen.TDSwordGUI.TDSwordMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class TDSword extends Item {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);
    private Vec3 lastWaypoint = null;
    private int lapisSlot = 0;
    private int lapisAmount = 0;
    private int goldSlot = 1;
    private int goldAmount = 0;
    protected final ContainerData data;
    private List<Vec3> Waypoints;
    private float lastWaypointYRotation = 0.0f;
    private ResourceKey<Level> lastDim = null;
    public TDSword(Properties pProperties) {
        super(pProperties);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TDSword.this.lapisAmount;
                    case 1 -> TDSword.this.goldAmount;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TDSword.this.lapisAmount = pValue;
                    case 1 -> TDSword.this.goldAmount = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    //Returns saved waypoint
    public Vec3 getLastWaypoint() { return lastWaypoint; }
    public float getLastWaypointRotation() { return lastWaypointYRotation; }
    public ResourceKey<Level> getLastDimension() { return lastDim; }

    public SimpleContainer inv = new SimpleContainer(2);

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //TransDimensionalSword.LOGGER.info(pUsedHand.toString());
        if(!pPlayer.isCrouching() && (pPlayer.getOffhandItem().is(Items.LAPIS_LAZULI) || pPlayer.getOffhandItem().is(Items.GOLD_INGOT)))
        {
            /*
            if(pPlayer.getOffhandItem().is(Items.LAPIS_LAZULI)) {
                //If item is lapis
                pPlayer.getOffhandItem().shrink(1);
                if(inv.getItem(lapisSlot) == ItemStack.EMPTY) {
                    inv.setItem(lapisSlot, new ItemStack(Items.LAPIS_LAZULI, 1));
                }
            } else {
                //If item is gold ingot
                pPlayer.getOffhandItem().shrink(1);
                if(inv.getItem(goldSlot) == ItemStack.EMPTY) {
                    inv.setItem(goldSlot, new ItemStack(Items.GOLD_INGOT, 1));
                }
            }
            */
            if(!pPlayer.level.isClientSide()) {
                pPlayer.openMenu(new SimpleMenuProvider(
                        (containerid, playerInventory, player) -> new TDSwordMenu(containerid, playerInventory, this, this.data),
                        Component.translatable("menu.title.tdsword.tdswordmenu")
                ));
                TransDimensionalSword.LOGGER.info("Open");
            }
        } else {
            lastDim = pLevel.dimension();
            var tempLastWaypoint = pPlayer.position();
            lastWaypointYRotation = pPlayer.getYHeadRot();
            //TransDimensionalSword.LOGGER.info("Sword: " + tempLastWaypointRotation);
            lastWaypoint = new Vec3(((int)tempLastWaypoint.x) + 0.5, tempLastWaypoint.y + 1, ((int)tempLastWaypoint.z) + 0.5);
            //lastWaypointRotation = new Vec3(Math.round(tempLastWaypointRotation), 0, Math.round(tempLastWaypointRotation);

        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }
    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new TDSwordMenu(id ,inv, this, this.data);
    }


}
