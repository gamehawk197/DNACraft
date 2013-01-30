package dnacraft.client.model;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dnacraft.api.IMobDefinition;
import dnacraft.client.rendering.Body;
import dnacraft.client.rendering.BodyPart;

@SideOnly(Side.CLIENT)
public class ModelMutant extends ModelBase {

	public HashMap<String, BodyPart[]> legs = new HashMap<String, BodyPart[]>();
	public HashMap<String, Body> bodies = new HashMap<String, Body>();
	public HashMap<String, BodyPart> heads = new HashMap<String, BodyPart>();
	public HashMap<String, Float> additionalLegHeight = new HashMap<String, Float>();
	public void register(IMobDefinition mobdef) {

		legs.put(mobdef.getName(), mobdef.getLegs(this));
		bodies.put(mobdef.getName(), mobdef.getBody(this));
		heads.put(mobdef.getName(), mobdef.getHead(this));
		additionalLegHeight.put(mobdef.getName(), mobdef.getAdditionalLegHeight());

	}

	public ModelMutant() {

	}

	public void render(Entity entity, float legSwing, float maxLegSwing,
			float wingSwing, float yaw, float pitch, float scale) {

		RenderEngine renderEngine = Minecraft.getMinecraft().renderEngine;

		Body bodyDef = this.bodies.get("pig");
		BodyPart[] legsDef = this.legs.get("spider");
		float additionalLegHeight = this.additionalLegHeight.get("spider");
		BodyPart headDef = this.heads.get("pig");

		// first get the leg height from one of the legs
		// so we know where to render the body
		float legheight = legsDef[0].getHeight() + additionalLegHeight;

		// bind the body texture
		renderEngine.bindTexture(renderEngine.getTexture(bodyDef.getTexture()));

		// get the body model
		ModelRenderer bodyRenderer = bodyDef.getRenderer();

		bodyRenderer.setRotationPoint(0, (float) 24 - legheight,
				-(bodyDef.getHeight() / 2));

		bodyDef.setRotation(bodyRenderer, entity, legSwing, maxLegSwing,
				wingSwing, yaw, pitch, scale);

		// render the body
		bodyRenderer.render(scale);

		// render the head
		ModelRenderer headRenderer = headDef.getRenderer();

		Vec3 headAttachmentPoint = bodyDef.getHeadAttachmentPoint();
		headRenderer.setRotationPoint((float) headAttachmentPoint.xCoord,
				(float) (24 - legheight - headAttachmentPoint.yCoord),
				(float) headAttachmentPoint.zCoord);
		headDef.setRotation(headRenderer, entity, legSwing, maxLegSwing,
				wingSwing, yaw, pitch, scale);
		headRenderer.render(scale);

		// so, a body has relative attachment points for either 2, 4 or 8 legs.
		// we know we have 2 legs (because we're rendering pig legs), so we get
		// the attachment point on the body for when there's 2 legs
		Vec3[] legAttachments = bodyDef.getLegAttachmentPoints(legsDef.length);

		// loop through the leg defintions
		for (int i = 0; i < legsDef.length; i++) {

			BodyPart leg = legsDef[i];

			// get the attachment point
			Vec3 attachmentPoint = legAttachments[i];

			// now render the leg..etc.
			ModelRenderer legRenderer = leg.getRenderer();

			renderEngine.bindTexture(renderEngine.getTexture(leg.getTexture()));

			legRenderer.setRotationPoint((float) attachmentPoint.xCoord,
					(float) (24 - additionalLegHeight - attachmentPoint.yCoord),
					(float) attachmentPoint.zCoord);

			leg.setRotation(legRenderer, entity, legSwing, maxLegSwing,
					wingSwing, yaw, pitch, scale);

			legRenderer.render(scale);
		}

	}

}
