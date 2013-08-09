package com.metatechcraft.block;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;
import com.metatechcraft.tileentity.InventoryLinkTile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class InventoryLinkBlock extends BlockContainer {

	public InventoryLinkBlock(int id, Material material) {
		super(id, material);
	}

	protected InventoryLinkBlock(int par1) {
		this(par1, Material.iron);
		setUnlocalizedName("InventoryLink");
		// this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F,
		// 0.9375F);
		setCreativeTab(MetaTechCraft.tabs);
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
	 * side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		int j1 = Facing.oppositeSide[par5];
		return j1;
	}
	
	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
	}

	@SideOnly(Side.CLIENT)
	protected Icon side2Icon;
	@SideOnly(Side.CLIENT)
	protected Icon side3Icon;
	@SideOnly(Side.CLIENT)
	protected Icon side4Icon;
	@SideOnly(Side.CLIENT)
	protected Icon frontIcon;
	@SideOnly(Side.CLIENT)
	protected Icon backIcon;

	@SideOnly(Side.CLIENT)
	protected Icon icons[][];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		/*this.blockIcon = iconRegister.registerIcon("furnace_side");
		this.topIcon = iconRegister.registerIcon("furnace_top");
		this.frontIcon = iconRegister.registerIcon("dropper_front");
		this.frontVerticalIcon = iconRegister.registerIcon("dropper_front_vertical");*/
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_side1");
		this.side2Icon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_side2");
		this.side3Icon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_side3");
		this.side4Icon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_side4");
		this.frontIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_front");
		this.backIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName() + "_sideBack");
		this.icons = new Icon[][] { { this.frontIcon, this.backIcon, this.side4Icon, this.side4Icon, this.side4Icon, this.side4Icon },
				{ this.backIcon, this.frontIcon, this.side3Icon, this.side3Icon, this.side3Icon, this.side3Icon },
				{ this.side3Icon, this.side3Icon, this.frontIcon, this.backIcon, this.blockIcon, this.side2Icon },
				{ this.side4Icon, this.side4Icon, this.backIcon, this.frontIcon, this.side2Icon, this.blockIcon },
				{ this.blockIcon, this.blockIcon, this.side2Icon, this.blockIcon, this.frontIcon, this.backIcon },
				{ this.side2Icon, this.side2Icon, this.blockIcon, this.side2Icon, this.backIcon, this.frontIcon } };
	}

	// {"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"};
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return this.icons[par2][par1];
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new InventoryLinkTile();
	}
	
	
    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
	@Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
	@Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory(this.getLinkTile(par1World, par2, par3, par4));
    }

	private InventoryLinkTile getLinkTile(World par1World, int par2, int par3, int par4) {
		return (InventoryLinkTile)par1World.getBlockTileEntity(par2, par3, par4);
	}
    
}
