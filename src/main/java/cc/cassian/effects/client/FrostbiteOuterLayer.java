//? if <26.4 {
package cc.cassian.effects.client;

import cc.cassian.effects.EmbersAndIcicles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
//? if >=26 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
//?} else {
/*import cc.cassian.effects.entity.Frostbite;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=26 {
public class FrostbiteOuterLayer extends RenderLayer<ZombieRenderState, ZombieModel<ZombieRenderState>> {
//?} else {
/*public class FrostbiteOuterLayer extends RenderLayer<Frostbite, ZombieModel<Frostbite>> {
*///?}
	private static final Identifier OUTER_LAYER_LOCATION = EmbersAndIcicles.of("textures/entity/zombie/frostbite_outer_layer.png");
	//? if >=26 {
	private final ZombieModel<ZombieRenderState> model;

	public FrostbiteOuterLayer(final RenderLayerParent<ZombieRenderState, ZombieModel<ZombieRenderState>> renderer, final EntityModelSet modelSet) {
		super(renderer);
		this.model = new ZombieModel<>(modelSet.bakeLayer(ModModelLayers.FROSTBITE_OUTER_LAYER));
	}

	@Override
	public void submit(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final ZombieRenderState state,
					   final float yRot, final float xRot) {
		if (!state.isBaby) {
			coloredCutoutModelCopyLayerRender(this.model, OUTER_LAYER_LOCATION, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
		}
	}
	//?} else {
	/*private final ZombieModel<Frostbite> model;

	public FrostbiteOuterLayer(final RenderLayerParent<Frostbite, ZombieModel<Frostbite>> renderer, final EntityModelSet modelSet) {
		super(renderer);
		this.model = new ZombieModel<>(modelSet.bakeLayer(ModModelLayers.FROSTBITE_OUTER_LAYER));
	}

	@Override
	public void render(final PoseStack poseStack, final MultiBufferSource buffer, final int packedLight, final Frostbite entity, final float limbSwing,
					   final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch) {
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, OUTER_LAYER_LOCATION, poseStack, buffer, packedLight, entity,
				limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, -1);
	}
	*///?}
}
//?}
