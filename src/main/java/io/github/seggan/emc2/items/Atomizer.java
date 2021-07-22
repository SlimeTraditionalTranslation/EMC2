package io.github.seggan.emc2.items;

import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Atomizer extends SlimefunItem {

    public Atomizer() {
        super(Items.CATEGORY, Items.ATOMIZER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
            SlimefunItems.AIR_RUNE, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.WATER_RUNE,
            Items.QGP_CONTAINMENT_FIELD, Items.QGP_CONTAINMENT_CELL, Items.QGP_CONTAINMENT_FIELD,
            SlimefunItems.EARTH_RUNE, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.FIRE_RUNE
        });

        addItemHandler(onRightClick());
    }

    private BlockUseHandler onRightClick() {
        return e -> {
            e.cancel();

            ItemStack item = e.getItem();
            if (item.getType().isAir()) return;

            long value = ItemValues.getInstance().getValue(item, false);

            if (value > 0) {
                e.getPlayer().sendMessage(String.format(
                    "這個 %s" + ChatColor.RESET + " 值 %d 夸克膠子等離子體在去物質化機中",
                    ItemUtils.getItemName(item),
                    value
                ));

                e.getPlayer().sendMessage(String.format(
                    "這個 %s" + ChatColor.RESET + " 在再物質化機需要消耗 %d 夸克膠子等離子體",
                    ItemUtils.getItemName(item),
                    ItemValues.getInstance().getValue(item, true)
                ));
            } else {
                e.getPlayer().sendMessage(String.format(
                    "這個 %s" + ChatColor.RESET + " 是不可被複製的",
                    ItemUtils.getItemName(item)
                ));
            }
        };
    }
}
