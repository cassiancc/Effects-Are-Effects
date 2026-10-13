package cc.cassian.effects.registry;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
	public static final Holder<MobEffect> BURNING =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, EffectsAreEffects.id("burning"), new BurningEffect());

	public static void init() {

	}

	private static class BurningEffect extends MobEffect {
		protected BurningEffect() {
			super(MobEffectCategory.HARMFUL, 0);
		}
	}
}
