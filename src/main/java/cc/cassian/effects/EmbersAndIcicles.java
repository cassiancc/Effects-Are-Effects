package cc.cassian.effects;

//? if <26.4
import cc.cassian.effects.backport.FrozenCaves;
import cc.cassian.effects.config.ModConfig;
import cc.cassian.effects.registry.*;
import cc.cassian.effects.util.ModUtil;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbersAndIcicles {
	public static final String MOD_ID = "embers_and_icicles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfig CONFIG = ModConfig.createToml(ModUtil.getConfigDir(), "", MOD_ID, ModConfig.class);

	public static void onInitialize() {
		ModSoundEvents.init();
		ModParticleTypes.init();
		ModEntityTypes.init();
		ModItems.init();
		ModEffects.init();
		ModPotions.init();
		//? if <26.4
		FrozenCaves.setup();
	}

	public static Identifier of(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
