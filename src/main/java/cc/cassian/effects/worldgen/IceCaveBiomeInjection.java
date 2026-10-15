//? if <26.4 {
package cc.cassian.effects.worldgen;

import cc.cassian.effects.EmbersAndIcicles;

public final class IceCaveBiomeInjection {
	private static final ThreadLocal<Boolean> BUILDING_BUILTIN_REGISTRIES = ThreadLocal.withInitial(() -> false);

	public static void setBuildingBuiltinRegistries(final boolean building) {
		BUILDING_BUILTIN_REGISTRIES.set(building);
	}

	public static boolean shouldInject() {
		return EmbersAndIcicles.CONFIG.generateIceCaves && !BUILDING_BUILTIN_REGISTRIES.get();
	}
}
//?}
