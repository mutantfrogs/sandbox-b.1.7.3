package mutantfrogs.sandbox.items;

import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.template.item.TemplateMusicDiscItem;
import net.modificationstation.stationapi.api.util.Namespace;

public class MusicDiscs {
    public static Item musicDisc11;
    public static Item musicDiscBlocks;
    public static Item musicDiscChrip;
    public static Item musicDiscDroopy;
    public static Item musicDiscFar;
    public static Item musicDiscMall;
    public static Item musicDiscMellohi;
    public static Item musicDiscStal;
    public static Item musicDiscStrad;
    public static Item musicDiscWait;
    public static Item musicDiscWard;

    public static void initDiscs(Namespace namespace){
        musicDisc11 = new TemplateMusicDiscItem(namespace.id("music_disc_11"), "11").setTranslationKey("record");
        musicDiscBlocks = new TemplateMusicDiscItem(namespace.id("music_disc_blocks"), "blocks").setTranslationKey("record");
        musicDiscChrip = new TemplateMusicDiscItem(namespace.id("music_disc_chirp"), "chirp").setTranslationKey("record");
        musicDiscDroopy = new TemplateMusicDiscItem(namespace.id("music_disc_droopy"), "sandbox:droopy").setTranslationKey("record");
        musicDiscFar = new TemplateMusicDiscItem(namespace.id("music_disc_far"), "far").setTranslationKey("record");
        musicDiscMall = new TemplateMusicDiscItem(namespace.id("music_disc_mall"), "mall").setTranslationKey("record");
        musicDiscMellohi = new TemplateMusicDiscItem(namespace.id("music_disc_mellohi"), "mellohi").setTranslationKey("record");
        musicDiscStal = new TemplateMusicDiscItem(namespace.id("music_disc_stal"), "stal").setTranslationKey("record");
        musicDiscStrad = new TemplateMusicDiscItem(namespace.id("music_disc_strad"), "strad").setTranslationKey("record");
        musicDiscWait = new TemplateMusicDiscItem(namespace.id("music_disc_wait"), "sandbox:wait").setTranslationKey("record");
        musicDiscWard = new TemplateMusicDiscItem(namespace.id("music_disc_ward"), "ward").setTranslationKey("record");
    }

}
