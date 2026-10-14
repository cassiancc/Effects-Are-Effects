package cc.cassian.effects;

import cc.cassian.effects.config.ModConfig;
import cc.cassian.effects.registry.*;
import cc.cassian.effects.util.ModUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectsAreEffects {
	public static final String MOD_ID = "effects_are_effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfig CONFIG = ModConfig.createToml(ModUtil.getConfigDir(), "", MOD_ID, ModConfig.class);

	public static void onInitialize() {
		ModEntityTypes.init();
		ModItems.init();
		ModEffects.init();
		ModParticleTypes.init();
		ModPotions.init();
		ModSoundEvents.init();
	}

	public static Identifier of(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
