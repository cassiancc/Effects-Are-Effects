package cc.cassian.effects;

import cc.cassian.effects.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectsAreEffects {
	public static final String MOD_ID = "effects_are_effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
