package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class Rematerializer extends SlimefunItem {

    private static final int[] BACKGROUND = new int[]{0, 8, 9, 13, 17, 18, 22, 26};
    private static final int[] INPUT_BORDER = new int[]{1, 2, 3, 10, 12, 19, 20, 21};
    private static final int[] OUTPUT_BORDER = new int[]{5, 6, 7, 14, 16, 23, 24, 25};

    private static final int ACTION_SLOT = 4;
    private static final int ITEM_SLOT = 11;
    private static final int OUTPUT_SLOT = 15;

    private static final ItemStack INPUT_ITEM = new CustomItem(
        Material.BLUE_STAINED_GLASS_PANE,
        "&9輸入",
        "",
        "&7將要複製的物品",
        "&7放在相鄰的欄位中"
    );

    private static final ItemStack ACTION_ITEM = new CustomItem(
        Material.NETHER_STAR,
        "&6複製",
        "",
        "&e左鍵&7 複製一件物品到你的物品欄中",
        "&eShift 左鍵&7 複製一組物品到你的物品欄中",
        "&e右鍵&7 複製一件物品",
        "&eShift 右鍵&7 複製一組物品"
    );

    public Rematerializer() {
        super(Items.CATEGORY, Items.REMATERIALIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL,
            Items.SUPERCONDUCTING_WIRE, Items.ATOMIZER, Items.SUPERCONDUCTING_WIRE,
            Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL
        });

        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                setupMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addMenuClickHandler(ACTION_SLOT, (p, slot, item1, action) -> {
                    Rematerializer.this.onClick(p, menu, action);
                    return false;
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                    SlimefunPlugin.getProtectionManager().hasPermission(
                        p,
                        b.getLocation(),
                        ProtectableAction.INTERACT_BLOCK
                    );
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[]{OUTPUT_SLOT};
            }
        };
    }

    private void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int slot : INPUT_BORDER) {
            preset.addItem(slot, INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            preset.addItem(slot, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(ACTION_SLOT, ACTION_ITEM);
    }

    @ParametersAreNonnullByDefault
    private void onClick(Player p, BlockMenu menu, ClickAction action) {
        ItemStack copyItem = menu.getItemInSlot(ITEM_SLOT);
        if (copyItem == null || copyItem.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "請放入要複製的物品");
            return;
        }

        ItemStack item = copyItem.clone();
        item.setAmount(action.isShiftClicked() ? 64 : 1);

        long cost = ItemValues.getInstance().getValue(item); // get the cost of the item
        if (cost == 0) {
            p.sendMessage(ChatColor.RED + "這個物品無法被複製");
            return;
        }

        Block b = menu.getBlock();
        String s = BlockStorage.getLocationInfo(b.getLocation(), "buffer");
        if (s != null) {
            cost -= Long.parseLong(s); // reduce the cost by the buffer
            cost = Math.max(1, cost);
        }

        long taken = QGPCapacitor.removeAmong(b, cost); // take any more qgp needed
        if (taken < cost) { // too little qgp taken
            BlockStorage.addBlockInfo(b, "buffer", Long.toString(taken)); // store to buffer
            p.sendMessage(ChatColor.RED + "沒有足夠的夸克膠子等離子體. 請確保至少有一個 " +
                "夸克膠子容器與再物質化機相鄰, 並且它們有足夠的 " +
                "夸克膠子等離子體在它們之間"
            );
            return;
        }

        BlockStorage.addBlockInfo(b, "buffer", Long.toString(0)); // clear buffer on successful copy

        if (action.isRightClicked()) {
            menu.pushItem(item, OUTPUT_SLOT);
        } else {
            Map<Integer, ItemStack> notFit = p.getInventory().addItem(item);
            if (!notFit.isEmpty()) {
                menu.pushItem(item, OUTPUT_SLOT);
            }
        }
    }
}
