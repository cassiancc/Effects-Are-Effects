package cc.cassian.effects.registry;

import cc.cassian.effects.references.ModPotionIds;
import cc.cassian.effects.util.ModUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotions {
	public static final Holder<Potion> BURNING = register(ModPotionIds.BURNING, new Potion("burning", new MobEffectInstance(ModEffects.BURNING, 900)));
	public static final Holder<Potion> LONG_BURNING = register(ModPotionIds.LONG_BURNING, new Potion("burning", new MobEffectInstance(ModEffects.BURNING, 1800)));
	//? if <26.4 {
	public static final Holder<Potion> FREEZING = register(ModPotionIds.FREEZING, new Potion("freezing", new MobEffectInstance(ModEffects.FREEZING, 900)));
	public static final Holder<Potion> LONG_FREEZING = register(
			ModPotionIds.LONG_FREEZING, new Potion("freezing", new MobEffectInstance(ModEffects.FREEZING, 1800))
	);
	//?}

	private static Holder<Potion> register(final ResourceKey<Potion> key, final Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
	}

	public static void init() {
		ModUtil.addAlias(BuiltInRegistries.POTION, "ice_ball");
	}
}
