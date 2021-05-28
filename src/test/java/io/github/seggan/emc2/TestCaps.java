package io.github.seggan.emc2;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import io.github.seggan.emc2.items.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;

public class TestCaps {
    private static ServerMock server;
    private static SlimefunPlugin slimefun;
    private static EMC2 emc2;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        slimefun = MockBukkit.load(SlimefunPlugin.class);
        emc2 = MockBukkit.load(EMC2.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testAdding() {
        Map<Location, Map<String, String>> storage = new HashMap<>();

        WorldMock world = server.addSimpleWorld("test");

        try (MockedStatic<BlockStorage> bsMock = Mockito.mockStatic(BlockStorage.class)) {
            bsMock.when(() -> BlockStorage.addBlockInfo(any(Block.class), anyString(), anyString()))
                .then((Answer<Void>) a -> {
                        Block b = a.getArgument(0, Block.class);
                        storage.computeIfAbsent(b.getLocation(), k -> new HashMap<>())
                            .put(a.getArgument(1), a.getArgument(2));
                        return null;
                    }
                );
            bsMock.when(() -> BlockStorage.getLocationInfo(any(Location.class), anyString()))
                .then((Answer<String>) a -> {
                        Map<String, String> info = storage.get(a.getArgument(0, Location.class));
                        if (info == null) return null;

                        return info.get(a.getArgument(1, String.class));
                    }
                );
            bsMock.when(() -> BlockStorage.check(any(Block.class))).then((Answer<SlimefunItem>) a -> {
                    Location l = a.getArgument(0, Block.class).getLocation();
                    String id = BlockStorage.getLocationInfo(l, "id");
                    if (id == null) return null;

                    return SlimefunItem.getByID(id);
                }
            );

            BlockMock cap1 = world.getBlockAt(0, 0, 0);
            BlockMock cap2 = world.getBlockAt(0, 2, 0);
            BlockMock center = world.getBlockAt(0, 1, 0);

            BlockStorage.addBlockInfo(cap1, "id", "SMALL_QGP_CAPACITOR");
            BlockStorage.addBlockInfo(cap2, "id", "SMALL_QGP_CAPACITOR");

            Capacitor.distributeAmong(center, 4);
            Assertions.assertEquals(Capacitor.get(cap1), Capacitor.get(cap2));

            Assertions.assertEquals(3, Capacitor.removeAmong(center, 3));
            Assertions.assertEquals(1, Capacitor.removeAmong(center, 3));
        }
    }
}
