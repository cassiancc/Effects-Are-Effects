package cc.cassian.effects.registry;

//? if <26.4 {
import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.Entity;
//? fabric {
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
//?} else {
/*import net.minecraft.core.Registry;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
*///?}
//?}

public class ModAttachments {
    //? if <26.4 {
    //? fabric {
    public static final AttachmentType<Boolean> FREEZING_CONVERSION = AttachmentRegistry.create(
            EmbersAndIcicles.of("freezing_conversion"),
            builder -> builder.initializer(() -> false).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );
    //?} else {
	/*public static final AttachmentType<Boolean> FREEZING_CONVERSION = Registry.register(
			NeoForgeRegistries.ATTACHMENT_TYPES,
			EmbersAndIcicles.of("freezing_conversion"),
			AttachmentType.builder(() -> false).sync(ByteBufCodecs.BOOL).build()
	);
	*///?}

    public static boolean isFreezingConverting(final Entity entity) {
        //? fabric {
        return entity.getAttachedOrElse(FREEZING_CONVERSION, false);
        //?} else {
        /*return entity.getData(FREEZING_CONVERSION);
         *///?}
    }

    public static void setFreezingConverting(final Entity entity, final boolean converting) {
        // setting an attachment always sends a sync packet, so only write when the value changes
        if (isFreezingConverting(entity) == converting) {
            return;
        }
        //? fabric {
        entity.setAttached(FREEZING_CONVERSION, converting);
        //?} else {
        /*entity.setData(FREEZING_CONVERSION, converting);
         *///?}
    }
    //?}

    public static void init() {
    }
}
