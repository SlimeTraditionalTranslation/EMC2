package io.github.seggan.emc2;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.LockedItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Items {

    public static final ItemGroup CATEGORY;

    static {
        assert Slimefun.instance() != null;

        CATEGORY = new LockedItemGroup(
            AbstractAddon.createKey("emc2_category"),
            new CustomItemStack(Material.QUARTZ_BRICKS, "夸克交換2"),
            new NamespacedKey(Slimefun.instance(), "electricity")
        );
    }

    public static final SlimefunItemStack SMALL_CAPACITOR = new SlimefunItemStack(
        "SMALL_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&f小型夸克膠子容器",
        "",
        "&6儲存1,000夸克膠子等離子體"
    );

    public static final SlimefunItemStack MEDIUM_CAPACITOR = new SlimefunItemStack(
        "MEDIUM_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&f中型夸克膠子容器",
        "",
        "&6儲存50,000夸克膠子等離子體"
    );

    public static final SlimefunItemStack LARGE_CAPACITOR = new SlimefunItemStack(
        "LARGE_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&f大型夸克膠子容器",
        "",
        "&6儲存2,500,000夸克膠子等離子體"
    );

    public static final SlimefunItemStack DEMATERIALIZER = new SlimefunItemStack(
        "DEMATERIALIZER",
        Material.QUARTZ_BRICKS,
        "&f去物質化機",
        "",
        "&7將物品分解成夸克膠子等離子體"
    );

    public static final SlimefunItemStack REMATERIALIZER = new SlimefunItemStack(
        "REMATERIALIZER",
        Material.QUARTZ_PILLAR,
        "&f再物質化機",
        "",
        "&7使用夸克膠子等離子體複製物品"
    );

    public static final SlimefunItemStack ROUTER = new SlimefunItemStack(
        "QGP_ROUTER",
        Material.CHISELED_QUARTZ_BLOCK,
        "&f夸克膠子路由器",
        "",
        "&7嘗試在相鄰容器之間",
        "&7均勻分布夸克膠子等離子體.",
        "&7可用於製作蓄電池或電線"
    );

    public static final SlimefunItemStack QGP_CONTAINMENT_FIELD = new SlimefunItemStack(
        "QGP_CONTAINMENT_FIELD",
        Material.PAPER,
        "&b夸克膠子等離子體遏制領域"
    );

    public static final SlimefunItemStack QGP_CONTAINMENT_CELL = new SlimefunItemStack(
        "QGP_CONTAINMENT_CELL",
        Material.HEART_OF_THE_SEA,
        "&b夸克膠子等離子體遏制元件"
    );

    public static final SlimefunItemStack SUPERCONDUCTING_WIRE = new SlimefunItemStack(
        "SUPERCONDUCTING_WIRE",
        Material.STRING,
        "&b超導線材"
    );

    public static final SlimefunItemStack ATOMIZER = new SlimefunItemStack(
        "ATOMIZER",
        Material.BEACON,
        "&b夸克膠子等離子體霧化器",
        "",
        "&e右鍵&7 與一個物品來檢查它值",
        "&7多少夸克膠子等離子體"
    );
}
