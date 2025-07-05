package mutantfrogs.sandbox.entities;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BrickEntity extends Entity {
    private int blockX = -1;
    private int blockY = -1;
    private int blockZ = -1;
    private int blockId = 0;
    private boolean inGround = false;
    public int shake = 0;
    private LivingEntity owner;
    private int removalTimer;
    private int inAirTime = 0;

    public BrickEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
    }

    @Override
    protected void initDataTracker() {
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean shouldRender(double distance) {
        double var3 = this.boundingBox.getAverageSideLength() * 4.0;
        var3 *= 64.0;
        return distance < var3 * var3;
    }

    public BrickEntity(World world, LivingEntity owner) {
        super(world);
        this.owner = owner;
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPositionAndAnglesKeepPrevAngles(owner.x, owner.y + (double)owner.getEyeHeight(), owner.z, owner.yaw, owner.pitch);
        this.x = this.x - (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.y -= 0.1F;
        this.z = this.z - (double)(MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0F;
        float var3 = 0.4F;
        this.velocityX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.velocityZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.velocityY = (double)(-MathHelper.sin(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.setVelocity(this.velocityX, this.velocityY, this.velocityZ, 0.7f, 1.0F);
    }

    public BrickEntity(World world, double x, double y, double z) {
        super(world);
        this.removalTimer = 0;
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPosition(x, y, z);
        this.standingEyeHeight = 0.0F;
    }

    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        float var9 = MathHelper.sqrt(x * x + y * y + z * z);
        x /= (double)var9;
        y /= (double)var9;
        z /= (double)var9;
        x += this.random.nextGaussian() * 0.0075F * (double)divergence;
        y += this.random.nextGaussian() * 0.0075F * (double)divergence;
        z += this.random.nextGaussian() * 0.0075F * (double)divergence;
        x *= (double)speed;
        y *= (double)speed;
        z *= (double)speed;
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
        float var10 = MathHelper.sqrt(x * x + z * z);
        this.prevYaw = this.yaw = (float)(Math.atan2(x, z) * 180.0 / (float) Math.PI);
        this.prevPitch = this.pitch = (float)(Math.atan2(y, (double)var10) * 180.0 / (float) Math.PI);
        this.removalTimer = 0;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float var7 = MathHelper.sqrt(x * x + z * z);
            this.prevYaw = this.yaw = (float)(Math.atan2(x, z) * 180.0 / (float) Math.PI);
            this.prevPitch = this.pitch = (float)(Math.atan2(y, (double)var7) * 180.0 / (float) Math.PI);
        }
    }

    @Override
    public void tick() {
        this.lastTickX = this.x;
        this.lastTickY = this.y;
        this.lastTickZ = this.z;
        super.tick();
        if (this.shake > 0) {
            this.shake--;
        }

        if (this.inGround) {
            int var1 = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
            if (var1 == this.blockId) {
                this.removalTimer++;
                if (this.removalTimer == 1200) {
                    this.markDead();
                }

                return;
            }

            this.inGround = false;
            this.velocityX = this.velocityX * (double)(this.random.nextFloat() * 0.2F);
            this.velocityY = this.velocityY * (double)(this.random.nextFloat() * 0.2F);
            this.velocityZ = this.velocityZ * (double)(this.random.nextFloat() * 0.2F);
            this.removalTimer = 0;
            this.inAirTime = 0;
        } else {
            this.inAirTime++;
        }

        Vec3d var15 = Vec3d.createCached(this.x, this.y, this.z);
        Vec3d var2 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult var3 = this.world.raycast(var15, var2);
        var15 = Vec3d.createCached(this.x, this.y, this.z);
        var2 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (var3 != null) {
            var2 = Vec3d.createCached(var3.pos.x, var3.pos.y, var3.pos.z);
        }

        if (!this.world.isRemote) {
            Entity var4 = null;
            List var5 = this.world.getEntities(this, this.boundingBox.stretch(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;

            for (int var8 = 0; var8 < var5.size(); var8++) {
                Entity var9 = (Entity)var5.get(var8);
                if (var9.isCollidable() && (var9 != this.owner || this.inAirTime >= 5)) {
                    float var10 = 0.3F;
                    Box var11 = var9.boundingBox.expand((double)var10, (double)var10, (double)var10);
                    HitResult var12 = var11.raycast(var15, var2);
                    if (var12 != null) {
                        double var13 = var15.distanceTo(var12.pos);
                        if (var13 < var6 || var6 == 0.0) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

            if (var4 != null) {
                var3 = new HitResult(var4);
            }
        }

        if (var3 != null) {
            if (var3.entity != null) {
                var3.entity.damage(this.owner, 2);
            }
            else{
                this.blockX = var3.blockX;
                this.blockY = var3.blockY;
                this.blockZ = var3.blockZ;
                this.blockId = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
                if(blockId == Block.GLASS.id){
                    world.worldEvent(2001,var3.blockX,var3.blockY,var3.blockZ,this.blockId);
                    this.world.setBlock(var3.blockX,var3.blockY,var3.blockZ, 0);
                }
            }

            if(blockId != Block.GLASS.id){
                this.world.playSound(this, "sandbox:brick", 1F, (1F) / (this.random.nextFloat() * 0.2F + 0.9F));
                for (int var18 = 0; var18 < 8; var18++) {
                    this.world.addParticle("brickpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                }
                this.markDead();
            }
        }

        this.x = this.x + this.velocityX;
        this.y = this.y + this.velocityY;
        this.z = this.z + this.velocityZ;
        float var19 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / (float) Math.PI);
        this.pitch = (float)(Math.atan2(this.velocityY, (double)var19) * 180.0 / (float) Math.PI);

        while (this.pitch - this.prevPitch < -180.0F) {
            this.prevPitch -= 360.0F;
        }

        while (this.pitch - this.prevPitch >= 180.0F) {
            this.prevPitch += 360.0F;
        }

        while (this.yaw - this.prevYaw < -180.0F) {
            this.prevYaw -= 360.0F;
        }

        while (this.yaw - this.prevYaw >= 180.0F) {
            this.prevYaw += 360.0F;
        }

        this.pitch = this.prevPitch + (this.pitch - this.prevPitch) * 0.2F;
        this.yaw = this.prevYaw + (this.yaw - this.prevYaw) * 0.2F;
        float var20 = 0.99F;
        float var21 = 0.03F;
        if (this.isSubmergedInWater()) {
            for (int var7 = 0; var7 < 4; var7++) {
                float var22 = 0.25F;
                this.world
                        .addParticle(
                                "bubble",
                                this.x - this.velocityX * (double)var22,
                                this.y - this.velocityY * (double)var22,
                                this.z - this.velocityZ * (double)var22,
                                this.velocityX,
                                this.velocityY,
                                this.velocityZ
                        );
            }

            var20 = 0.8F;
        }

        this.velocityX *= (double)var20;
        this.velocityY *= (double)var20;
        this.velocityZ *= (double)var20;
        this.velocityY -= (double)var21;
        this.setPosition(this.x, this.y, this.z);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putShort("xTile", (short)this.blockX);
        nbt.putShort("yTile", (short)this.blockY);
        nbt.putShort("zTile", (short)this.blockZ);
        nbt.putByte("inTile", (byte)this.blockId);
        nbt.putByte("shake", (byte)this.shake);
        nbt.putByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.blockX = nbt.getShort("xTile");
        this.blockY = nbt.getShort("yTile");
        this.blockZ = nbt.getShort("zTile");
        this.blockId = nbt.getByte("inTile") & 255;
        this.shake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;
    }

    @Override
    public void onPlayerInteraction(PlayerEntity player) {
        if (this.inGround && this.owner == player && this.shake <= 0 && player.inventory.addStack(new ItemStack(Item.ARROW, 1))) {
            this.world.playSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.sendPickup(this, 1);
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getShadowRadius() {
        return 0.0F;
    }
}
