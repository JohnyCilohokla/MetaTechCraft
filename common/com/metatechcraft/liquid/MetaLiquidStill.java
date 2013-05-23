package com.metatechcraft.liquid;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MetaLiquidStill extends BlockFluid {
	protected MetaLiquidStill(int par1, Material par2Material) {
		super(par1, par2Material);
		//setTickRandomly(false);

		//if (par2Material == Material.lava) {
		//	setTickRandomly(true);
		//}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return theIcon[0];
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return this.blockMaterial != Material.lava;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, tickRate(par1World));
		//if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
		//	setNotStationary(par1World, par2, par3, par4);
		//}
	}
	
	/**
	 * Returns true if the block at the coordinates can be displaced by the
	 * liquid.
	 */
	private boolean liquidCanDisplaceBlock(World par1World, int par2, int par3, int par4) {
		Material material = par1World.getBlockMaterial(par2, par3, par4);
		return material == Material.air;
	}
	
	/*private boolean blockBlocksFlow(World par1World, int par2, int par3, int par4) {
		int l = par1World.getBlockId(par2, par3, par4);

		if ((l != Block.doorWood.blockID) && (l != Block.doorIron.blockID) && (l != Block.signPost.blockID) && (l != Block.ladder.blockID)
				&& (l != Block.reed.blockID)) {
			if (l == 0) {
				return false;
			} else {
				Material material = Block.blocksList[l].blockMaterial;
				return material == Material.portal ? true : material.blocksMovement();
			}
		} else {
			return true;
		}
	}*/


	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (liquidCanDisplaceBlock(par1World, par2, par3-1, par4)) {
			par1World.setBlock(par2, par3-1, par4, this.blockID, 0, 3);
			par1World.setBlockToAir(par2, par3, par4);
		}else if (liquidCanDisplaceBlock(par1World, par2+1, par3-1, par4)&&liquidCanDisplaceBlock(par1World, par2+1, par3, par4)) {
			par1World.setBlock(par2+1, par3, par4, this.blockID, 0, 3);
			par1World.setBlockToAir(par2, par3, par4);
		}else if (liquidCanDisplaceBlock(par1World, par2-1, par3-1, par4)&&liquidCanDisplaceBlock(par1World, par2-1, par3, par4)) {
			par1World.setBlock(par2-1, par3, par4, this.blockID, 0, 3);
			par1World.setBlockToAir(par2, par3, par4);
		}else if (liquidCanDisplaceBlock(par1World, par2, par3-1, par4+1)&&liquidCanDisplaceBlock(par1World, par2, par3, par4+1)) {
			par1World.setBlock(par2, par3, par4+1, this.blockID, 0, 3);
			par1World.setBlockToAir(par2, par3, par4);
		}else if (liquidCanDisplaceBlock(par1World, par2, par3-1, par4-1)&&liquidCanDisplaceBlock(par1World, par2, par3, par4-1)) {
			par1World.setBlock(par2, par3, par4-1, this.blockID, 0, 3);
			par1World.setBlockToAir(par2, par3, par4);
		}
		
		if (this.blockMaterial == Material.lava) {
			int l = par5Random.nextInt(3);
			int i1;
			int j1;

			for (i1 = 0; i1 < l; ++i1) {
				par2 += par5Random.nextInt(3) - 1;
				++par3;
				par4 += par5Random.nextInt(3) - 1;
				j1 = par1World.getBlockId(par2, par3, par4);

				if (j1 == 0) {
					if (this.isFlammable(par1World, par2 - 1, par3, par4) || this.isFlammable(par1World, par2 + 1, par3, par4)
							|| this.isFlammable(par1World, par2, par3, par4 - 1) || this.isFlammable(par1World, par2, par3, par4 + 1)
							|| this.isFlammable(par1World, par2, par3 - 1, par4) || this.isFlammable(par1World, par2, par3 + 1, par4)) {
						par1World.setBlock(par2, par3, par4, Block.fire.blockID);
						return;
					}
				} else if (Block.blocksList[j1].blockMaterial.blocksMovement()) {
					return;
				}
			}

			if (l == 0) {
				i1 = par2;
				j1 = par4;

				for (int k1 = 0; k1 < 3; ++k1) {
					par2 = (i1 + par5Random.nextInt(3)) - 1;
					par4 = (j1 + par5Random.nextInt(3)) - 1;

					if (par1World.isAirBlock(par2, par3 + 1, par4) && this.isFlammable(par1World, par2, par3, par4)) {
						par1World.setBlock(par2, par3 + 1, par4, Block.fire.blockID);
					}
				}
			}
		}
	}

	/**
	 * Checks to see if the block is flammable.
	 */
	private boolean isFlammable(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockMaterial(par2, par3, par4).getCanBurn();
	}
	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);

		if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, tickRate(par1World));
		}
	}
}
