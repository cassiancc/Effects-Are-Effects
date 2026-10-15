//? if <26 {
/*package cc.cassian.effects.client;

import cc.cassian.effects.util.ModUtil;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.LivingEntity;

public final class TinyTakeoverCompat {
	private static final boolean LOADED = ModUtil.isModLoaded("tiny_takeover_backport");

	public static boolean usesBabyModel(final LivingEntity entity) {
		return LOADED && entity.isBaby() && ModConfig.get().isModelEnabled(entity);
	}
}
*///?}
