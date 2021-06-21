package io.github.seggan.emc2;

import io.github.seggan.emc2.items.Atomizer;
import io.github.seggan.emc2.items.Dematerializer;
import io.github.seggan.emc2.items.QGPCapacitor;
import io.github.seggan.emc2.items.Rematerializer;
import io.github.seggan.emc2.items.Router;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class Setup {

    private static int id = 112_112;

    public static void setupRecipes(EMC2 addon) {
        new QGPCapacitor(Items.SMALL_CAPACITOR, new ItemStack[]{
            SlimefunItems.REDSTONE_ALLOY, Items.QGP_CONTAINMENT_CELL, SlimefunItems.REDSTONE_ALLOY,
            Items.QGP_CONTAINMENT_CELL, SlimefunItems.ENERGIZED_CAPACITOR, Items.QGP_CONTAINMENT_CELL,
            SlimefunItems.REDSTONE_ALLOY, Items.QGP_CONTAINMENT_CELL, SlimefunItems.REDSTONE_ALLOY
        }, 1_000).register(addon);
        new QGPCapacitor(Items.MEDIUM_CAPACITOR, new ItemStack[]{
            Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE,
            Items.ROUTER, Items.SMALL_CAPACITOR, Items.ROUTER,
            Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE
        }, 50_000).register(addon);
        new QGPCapacitor(Items.LARGE_CAPACITOR, new ItemStack[]{
            Items.QGP_CONTAINMENT_CELL, Items.ROUTER, Items.QGP_CONTAINMENT_CELL,
            Items.ATOMIZER, Items.MEDIUM_CAPACITOR, Items.ATOMIZER,
            Items.QGP_CONTAINMENT_CELL, Items.ROUTER, Items.QGP_CONTAINMENT_CELL
        }, 2_500_000).register(addon);

        new Dematerializer().register(addon);
        new Rematerializer().register(addon);
        new Router().register(addon);

        new Atomizer().register(addon);

        new SlimefunItem(Items.CATEGORY, Items.SUPERCONDUCTING_WIRE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2,
            SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE,
            SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2
        }, new SlimefunItemStack(Items.SUPERCONDUCTING_WIRE, 2)).register(addon);
        new SlimefunItem(Items.CATEGORY, Items.QGP_CONTAINMENT_FIELD, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
            SlimefunItems.AIR_RUNE, SlimefunItems.SCROLL_OF_DIMENSIONAL_TELEPOSITION, SlimefunItems.AIR_RUNE,
            Items.SUPERCONDUCTING_WIRE, Items.SUPERCONDUCTING_WIRE, Items.SUPERCONDUCTING_WIRE,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
        }).register(addon);
        new SlimefunItem(Items.CATEGORY, Items.QGP_CONTAINMENT_CELL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.WITHER_PROOF_GLASS, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.WITHER_PROOF_GLASS,
            Items.QGP_CONTAINMENT_FIELD, null, Items.QGP_CONTAINMENT_FIELD,
            SlimefunItems.WITHER_PROOF_GLASS, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.WITHER_PROOF_GLASS
        }).register(addon);
    }

    public static void setupResearches() {
        registerResearch("qgp_storage", "夸克膠子儲存", 50, Items.SMALL_CAPACITOR, Items.MEDIUM_CAPACITOR);
        registerResearch("more_qgp_storage", "更多夸克膠子儲存", 70, Items.LARGE_CAPACITOR);
        registerResearch("materialization", "物質化", 70, Items.DEMATERIALIZER, Items.REMATERIALIZER);
        registerResearch("routing", "路由", 40, Items.ROUTER);
        registerResearch("qgp_manipulation", "夸克膠子等離子體操控者", 50, Items.QGP_CONTAINMENT_CELL,
            Items.QGP_CONTAINMENT_FIELD, Items.SUPERCONDUCTING_WIRE, Items.ATOMIZER);

    }

    private static void registerResearch(String key, String name, int cost, SlimefunItemStack... items) {
        Research research = new Research(EMC2.inst().getKey(key), id++, name, cost);
        research.addItems(items);
        research.register();
    }
}
