//? if <26.4 {
package cc.cassian.effects.client;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.entity.Frostbite;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
//? if >=26 {
import net.minecraft.client.model.monster.zombie.BabyZombieModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
//?} else {
/*import net.minecraft.world.entity.monster.zombie.Zombie;
*///?}

//? if >=26 {
public class FrostbiteRenderer extends AbstractZombieRenderer<Frostbite, ZombieRenderState, ZombieModel<ZombieRenderState>> {
//?} else {
/*public class FrostbiteRenderer extends AbstractZombieRenderer<Frostbite, ZombieModel<Frostbite>> {
*///?}
	private static final Identifier FROSTBITE_LOCATION = EmbersAndIcicles.of("textures/entity/zombie/frostbite.png");
	private static final Identifier FROSTBITE_BABY_LOCATION = EmbersAndIcicles.of("textures/entity/zombie/frostbite_baby.png");

	public FrostbiteRenderer(final EntityRendererProvider.Context context) {
		//? if >=26 {
		super(
				context,
				new ZombieModel<>(context.bakeLayer(ModModelLayers.FROSTBITE)),
				new BabyZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_BABY)),
				ArmorModelSet.bake(ModelLayers.ZOMBIE_ARMOR, context.getModelSet(), ZombieModel::new),
				ArmorModelSet.bake(ModelLayers.ZOMBIE_BABY_ARMOR, context.getModelSet(), BabyZombieModel::new)
		);
		//?} else {
		/*super(
				context,
				new ZombieModel<>(context.bakeLayer(ModModelLayers.FROSTBITE)),
				new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
				new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))
		);
		*///?}
		this.addLayer(new FrostbiteOuterLayer(this, context.getModelSet()));
	}

	//? if >=26 {
	@Override
	public ZombieRenderState createRenderState() {
		return new ZombieRenderState();
	}

	@Override
	public Identifier getTextureLocation(final ZombieRenderState state) {
		return state.isBaby ? FROSTBITE_BABY_LOCATION : FROSTBITE_LOCATION;
	}
	//?} else {
	/*@Override
	public Identifier getTextureLocation(final Zombie entity) {
		// Tiny Takeover Backport only swaps the textures of vanilla mobs for its baby models.
		return TinyTakeoverCompat.usesBabyModel(entity) ? FROSTBITE_BABY_LOCATION : FROSTBITE_LOCATION;
	}
	*///?}
}
//?}
