package com.metatechcraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MetaPortalBlock extends BlockBreakable {
	public MetaPortalBlock(int par1) {
		super(ModInfo.MOD_ID.toLowerCase() + ":" + "portal/meta", Material.portal, false);
		MetaTechCraft.registry.registerBlock(this, "MetaPortalBlock", "MetaPortal Block");
		setTickRandomly(true);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		float f;
		float f1;

		if ((par1IBlockAccess.getBlock(par2 - 1, par3, par4) != this) && (par1IBlockAccess.getBlock(par2 + 1, par3, par4) != this)) {
			f = 0.125F;
			f1 = 0.5F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
		} else {
			f = 0.5F;
			f1 = 0.125F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
		}
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Checks to see if this location is valid to create a portal and will
	 * return True if it does. Args: world, x, y, z
	 */
	public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4) {
		byte b0 = 0;
		byte b1 = 0;

		if ((par1World.getBlock(par2 - 1, par3 + 1, par4) == MetaBlocks.strangeObsidianBlock)
				|| (par1World.getBlock(par2 + 1, par3 + 1, par4) == MetaBlocks.strangeObsidianBlock)) {
			b0 = 1;
		}

		if ((par1World.getBlock(par2, par3 + 1, par4 - 1) == MetaBlocks.strangeObsidianBlock)
				|| (par1World.getBlock(par2, par3 + 1, par4 + 1) == MetaBlocks.strangeObsidianBlock)) {
			b1 = 1;
		}

		if (b0 == b1) {
			return false;
		} else {
			if (par1World.isAirBlock(par2 - b0, par3 + 1, par4 - b1)) {
				par2 -= b0;
				par4 -= b1;
			}

			int l;
			int i1;

			for (l = -1; l <= 2; ++l) {
				for (i1 = -1; i1 <= 3; ++i1) {
					boolean flag = (l == -1) || (l == 2) || (i1 == -1) || (i1 == 3);

					if (((l != -1) && (l != 2)) || ((i1 != -1) && (i1 != 3))) {
						Block j1 = par1World.getBlock(par2 + (b0 * l), par3 + 1 + i1, par4 + (b1 * l));

						if (flag) {
							if (j1 != MetaBlocks.strangeObsidianBlock) {
								return false;
							}
						}/*
							* else if ((j1 != 0) && (j1 != Block.fire.blockID)) {
							* return false; }
							*/
					}
				}
			}

			for (l = 0; l < 2; ++l) {
				for (i1 = 0; i1 < 3; ++i1) {
					par1World.setBlock(par2 + (b0 * l), par3 + 1 + i1, par4 + (b1 * l), MetaBlocks.metaPortalBlock, 0, 2);
				}
			}

			return true;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block block) {
		byte b0 = 0;
		byte b1 = 1;

		if ((par1World.getBlock(par2 - 1, par3, par4) == this) || (par1World.getBlock(par2 + 1, par3, par4) == this)) {
			b0 = 1;
			b1 = 0;
		}

		int i1;

		for (i1 = par3; par1World.getBlock(par2, i1 - 1, par4) == this; --i1) {
			;
		}

		if (par1World.getBlock(par2, i1 - 1, par4) != MetaBlocks.strangeObsidianBlock) {
			par1World.setBlockToAir(par2, par3, par4);
		} else {
			int j1;

			for (j1 = 1; (j1 < 4) && (par1World.getBlock(par2, i1 + j1, par4) == this); ++j1) {
				;
			}

			if ((j1 == 3) && (par1World.getBlock(par2, i1 + j1, par4) == MetaBlocks.strangeObsidianBlock)) {
				boolean flag = (par1World.getBlock(par2 - 1, par3, par4) == this) || (par1World.getBlock(par2 + 1, par3, par4) == this);
				boolean flag1 = (par1World.getBlock(par2, par3, par4 - 1) == this) || (par1World.getBlock(par2, par3, par4 + 1) == this);

				if (flag && flag1) {
					par1World.setBlockToAir(par2, par3, par4);
				} else {
					if (((par1World.getBlock(par2 + b0, par3, par4 + b1) != MetaBlocks.strangeObsidianBlock) || (par1World.getBlock(par2 - b0, par3, par4 - b1) != this))
							&& ((par1World.getBlock(par2 - b0, par3, par4 - b1) != MetaBlocks.strangeObsidianBlock) || (par1World.getBlock(par2 + b0, par3,
									par4 + b1) != this))) {
						par1World.setBlockToAir(par2, par3, par4);
					}
				}
			} else {
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par1IBlockAccess.getBlock(par2, par3, par4) == this) {
			return false;
		} else {
			boolean flag = (par1IBlockAccess.getBlock(par2 - 1, par3, par4) == this) && (par1IBlockAccess.getBlock(par2 - 2, par3, par4) != this);
			boolean flag1 = (par1IBlockAccess.getBlock(par2 + 1, par3, par4) == this) && (par1IBlockAccess.getBlock(par2 + 2, par3, par4) != this);
			boolean flag2 = (par1IBlockAccess.getBlock(par2, par3, par4 - 1) == this) && (par1IBlockAccess.getBlock(par2, par3, par4 - 2) != this);
			boolean flag3 = (par1IBlockAccess.getBlock(par2, par3, par4 + 1) == this) && (par1IBlockAccess.getBlock(par2, par3, par4 + 2) != this);
			boolean flag4 = flag || flag1;
			boolean flag5 = flag2 || flag3;
			return flag4 && (par5 == 4) ? true : (flag4 && (par5 == 5) ? true : (flag5 && (par5 == 2) ? true : flag5 && (par5 == 3)));
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if ((par5Entity.ridingEntity == null) && (par5Entity.riddenByEntity == null) && ((par5Entity instanceof EntityPlayerMP))) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) par5Entity;
			if (thePlayer.timeUntilPortal > 0) {
				thePlayer.timeUntilPortal = 10;
			} else if (thePlayer.dimension != MetaTechCraft.metaDimID) {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, MetaTechCraft.metaDimID,
						new MetaTeleporter(thePlayer.mcServer.worldServerForDimension(MetaTechCraft.metaDimID)));
			} else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0,
						new MetaTeleporter(thePlayer.mcServer.worldServerForDimension(0)));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par5Random.nextInt(100) == 0) {
			par1World.playSound(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "portal.portal", 0.5F, (par5Random.nextFloat() * 0.4F) + 0.8F, false);
		}

		for (int l = 0; l < 4; ++l) {
			double d0 = par2 + par5Random.nextFloat();
			double d1 = par3 + par5Random.nextFloat();
			double d2 = par4 + par5Random.nextFloat();
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = (par5Random.nextInt(2) * 2) - 1;
			d3 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d4 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d5 = (par5Random.nextFloat() - 0.5D) * 0.5D;

			if ((par1World.getBlock(par2 - 1, par3, par4) != this) && (par1World.getBlock(par2 + 1, par3, par4) != this)) {
				d0 = par2 + 0.5D + (0.25D * i1);
				d3 = par5Random.nextFloat() * 2.0F * i1;
			} else {
				d2 = par4 + 0.5D + (0.25D * i1);
				d5 = par5Random.nextFloat() * 2.0F * i1;
			}

			par1World.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 10;
	}
}
