package mutantfrogs.sandbox;

import mutantfrogs.sandbox.blocks.CryingObsidianBlock;
import mutantfrogs.sandbox.items.BloodDiamondItem;
import mutantfrogs.sandbox.items.ExampleItem;
import mutantfrogs.sandbox.items.MusicDiscs;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;


public class Sandbox {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;
    public static Block cryingObsidianBlock;
    public static Item exampleItem;
    public static Item bloodDiamondItem;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        cryingObsidianBlock = new CryingObsidianBlock(NAMESPACE.id("crying_obsidian")).setTranslationKey(NAMESPACE, "crying_obsidian").setHardness(10.0F).setResistance(2000.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setLuminance(.6F);
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event){
        exampleItem = new ExampleItem(NAMESPACE.id("example_item")).setTranslationKey(NAMESPACE, "example_item");
        bloodDiamondItem = new BloodDiamondItem(NAMESPACE.id("blood_diamond")).setTranslationKey(NAMESPACE, "blood_diamond");
        MusicDiscs.initDiscs(NAMESPACE);
    }
}
