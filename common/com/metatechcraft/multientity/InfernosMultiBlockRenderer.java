package com.metatechcraft.multientity;

import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class InfernosMultiBlockRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		/*Tessellator tessellator = Tessellator.instance;
		FluidStack fluidstack = new FluidStack(MetaLiquids.metaFluids[1].getFluid(), 200);
		Icon icon = InfuserRenderer.getFluidTexture(fluidstack, false);

		double size = fluidstack.amount * 0.001;

		FluidTessallator.InfuserTank.addToTessallator(tessellator, x, y, z, icon, size, size);*/
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return MetaTechCraft.infernosRendererId;
	}

}
