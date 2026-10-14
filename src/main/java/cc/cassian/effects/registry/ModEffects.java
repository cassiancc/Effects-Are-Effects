package cc.cassian.effects.registry;

import cc.cassian.effects.EffectsAreEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
	public static final Holder<MobEffect> BURNING =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, EffectsAreEffects.of("burning"), new BurningEffect(MobEffectCategory.HARMFUL, 0xeb6607));
	public static final Holder<MobEffect> FREEZING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
			EffectsAreEffects.of("freezing"), new FreezingMobEffect(MobEffectCategory.HARMFUL, 10415854).withSoundOnAdded(ModSoundEvents.APPLY_EFFECT_FREEZING)
	);

	public static void init() {

	}

	private static class BurningEffect extends MobEffect {
		protected BurningEffect(MobEffectCategory category, int color) {
			super(category, color);
		}
	}

	public static class FreezingMobEffect extends MobEffect {
		protected FreezingMobEffect(final MobEffectCategory category, final int color) {
			super(category, color, ModParticleTypes.FREEZING);
		}
	}

}
