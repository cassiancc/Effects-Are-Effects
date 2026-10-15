//? if <26.4 {
package cc.cassian.effects.client;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.monster.zombie.DrownedModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModModelLayers {
	public static final ModelLayerLocation FROSTBITE = new ModelLayerLocation(EmbersAndIcicles.of("frostbite"), "main");
	public static final ModelLayerLocation FROSTBITE_OUTER_LAYER = new ModelLayerLocation(EmbersAndIcicles.of("frostbite"), "outer");

	public static Map<ModelLayerLocation, Supplier<LayerDefinition>> createLayerDefinitions() {
		Map<ModelLayerLocation, Supplier<LayerDefinition>> layers = new LinkedHashMap<>();
		layers.put(FROSTBITE, () -> DrownedModel.createBodyLayer(CubeDeformation.NONE));
		layers.put(FROSTBITE_OUTER_LAYER, () -> DrownedModel.createBodyLayer(new CubeDeformation(0.25F)));
		return layers;
	}
}
//?}
