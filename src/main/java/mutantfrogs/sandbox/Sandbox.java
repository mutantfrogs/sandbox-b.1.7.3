package mutantfrogs.sandbox;

import mutantfrogs.sandbox.blocks.CryingObsidianBlock;
import mutantfrogs.sandbox.blocks.WetSpongeBlock;
import mutantfrogs.sandbox.entities.BrickEntity;
import mutantfrogs.sandbox.entities.TorchArrowEntity;
import mutantfrogs.sandbox.entities.renderer.TorchArrowEntityRenderer;
import mutantfrogs.sandbox.items.BloodDiamondItem;
import mutantfrogs.sandbox.items.ExampleItem;
import mutantfrogs.sandbox.items.MusicDiscs;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

import static net.minecraft.block.Block.DIRT_SOUND_GROUP;


public class Sandbox {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    //blocks
    public static Block cryingObsidianBlock;
    public static Block wetSpongeBlock;

    //items
    public static Item exampleItem;
    public static Item bloodDiamondItem;
    public static Item torchArrow;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        cryingObsidianBlock = new CryingObsidianBlock(NAMESPACE.id("crying_obsidian")).setTranslationKey(NAMESPACE, "crying_obsidian").setHardness(10.0F).setResistance(2000.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setLuminance(.6F);
        wetSpongeBlock = new WetSpongeBlock(NAMESPACE.id("wet_sponge")).setTranslationKey(NAMESPACE, "wet_sponge").setHardness(0.6F).setSoundGroup(DIRT_SOUND_GROUP);
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event){
        exampleItem = new ExampleItem(NAMESPACE.id("example_item")).setTranslationKey(NAMESPACE, "example_item");
        bloodDiamondItem = new BloodDiamondItem(NAMESPACE.id("blood_diamond")).setTranslationKey(NAMESPACE, "blood_diamond");
        torchArrow = new TemplateItem(NAMESPACE.id("torch_arrow")).setTranslationKey(NAMESPACE, "torch_arrow");
        MusicDiscs.initDiscs(NAMESPACE);
    }

    @EventListener
    public void registerEntityRender(EntityRendererRegisterEvent event){
        event.renderers.put(TorchArrowEntity.class, new TorchArrowEntityRenderer());
        event.renderers.put(BrickEntity.class, new ProjectileEntityRenderer(Item.BRICK.getTextureId(2)));
    }
}
