package com.metatechcraft.models;

import org.lwjgl.opengl.GL11;

import com.metatechcraft.lib.ModInfo;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelFrameBox {

	private IModelCustom modelFrameBox;

	public static final String MODEL_LOCATION = "/assets/metatechcraft/models/" + "blocks/boxFrame.obj";
	public static final ResourceLocation boxFrameTexture = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "models/blocks/" + "boxFrame.png");

	public ModelFrameBox() {
		this.modelFrameBox = AdvancedModelLoader.loadModel(ModelFrameBox.MODEL_LOCATION);
	}

	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.0, 0.5);// center
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ModelFrameBox.boxFrameTexture);
		this.modelFrameBox.renderAll();
		GL11.glPopMatrix();
	}
}