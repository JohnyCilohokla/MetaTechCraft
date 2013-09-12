package com.metatechcraft.liquid;

import com.forgetutorials.lib.registry.DescriptorFluid;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MetaLiquid extends BlockFluidFinite {

	String unlocalizedName;
	DescriptorFluid fluid;

	protected MetaLiquid(int id, DescriptorFluid fluid, String name) {
		super(id, fluid.getFluid(), MetaLiquids.metaLiquidMaterial);
		this.fluid = fluid;
		// props
		this.blockHardness = 100.0F;
		setLightOpacity(3);
		disableStats();
		setCreativeTab(MetaTechCraft.tabs);

		// names
		this.unlocalizedName = name.replaceAll("[^a-zA-Z]", "");
		setUnlocalizedName("metatech.fluid." + this.unlocalizedName);
		GameRegistry.registerBlock(this, this.unlocalizedName);
		LanguageRegistry.addName(this, "Liquid " + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "liquid/" + this.unlocalizedName);
		this.fluid.getFluid().setIcons(this.blockIcon);
	}

	@Override
	public Fluid getFluid() {
		return this.fluid.getFluid();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return this.blockIcon;
	}

	@Override
	public boolean canDrain(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public FluidStack drain(World world, int x, int y, int z, boolean doDrain) {
		if (doDrain) {
			int meta = world.getBlockMetadata(x, y, z);
			if (world.getBlockMetadata(x, y, z) <= 0) {
				world.setBlockToAir(x, y, z);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 3);
			}
		}
		return new FluidStack(getFluid(), 125);
	}
}
