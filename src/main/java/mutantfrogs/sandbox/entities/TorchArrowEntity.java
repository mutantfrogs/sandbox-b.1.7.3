package mutantfrogs.sandbox.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TorchArrowEntity extends Entity {
    private int blockX = -1;
    private int blockY = -1;
    private int blockZ = -1;
    private int blockId = 0;
    private int blockMeta = 0;
    private boolean inGround = false;
    /**
     * This is true if the arrow was shot by an PlayerEntity
     */
    public boolean pickupAllowed = false;
    public int shake = 0;
    public LivingEntity owner;
    private int life;
    private int inAirTime = 0;
    private int sideHit = -1;

    public TorchArrowEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public TorchArrowEntity(World world, double x, double y, double z) {
        super(world);
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.standingEyeHeight = 0.0F;
    }

    public TorchArrowEntity(World world, LivingEntity owner) {
        super(world);
        this.owner = owner;
        this.pickupAllowed = owner instanceof PlayerEntity;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPositionAndAnglesKeepPrevAngles(owner.x, owner.y + (double)owner.getEyeHeight(), owner.z, owner.yaw, owner.pitch);
        this.x = this.x - (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.y -= 0.1F;
        this.z = this.z - (double)(MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0F;
        this.velocityX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI));
        this.velocityZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI));
        this.velocityY = (double)(-MathHelper.sin(this.pitch / 180.0F * (float) Math.PI));
        this.setVelocity(this.velocityX, this.velocityY, this.velocityZ, 1.5F, 1.0F);
    }

    @Override
    protected void initDataTracker() {
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
        this.life = 0;
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
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;
            this.setPositionAndAnglesKeepPrevAngles(this.x, this.y, this.z, this.yaw, this.pitch);
            this.life = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        world.addParticle("flame", this.x ,this.y, this.z, 0f, 0f, 0f);
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float var1 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.prevYaw = this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / (float) Math.PI);
            this.prevPitch = this.pitch = (float)(Math.atan2(this.velocityY, (double)var1) * 180.0 / (float) Math.PI);
        }

        int var15 = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
        if (var15 > 0) {
            Block.BLOCKS[var15].updateBoundingBox(this.world, this.blockX, this.blockY, this.blockZ);
            Box var2 = Block.BLOCKS[var15].getCollisionShape(this.world, this.blockX, this.blockY, this.blockZ);
            if (var2 != null && var2.contains(Vec3d.createCached(this.x, this.y, this.z))) {
                this.inGround = true;
            }
        }

        if (this.shake > 0) {
            this.shake--;
        }

        //if arrow hits block
        if (this.inGround) {
            var15 = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
            int var21 = this.world.getBlockMeta(this.blockX, this.blockY, this.blockZ);
            if (var15 == this.blockId && var21 == this.blockMeta) {
               //place torch upon hit
                int targetBlockX = this.blockX;
                int targetBlockY = this.blockY;
                int targetBlockZ = this.blockZ;

                switch(sideHit){
                    case 0: targetBlockY--; break;
                    case 1: targetBlockY++; break;
                    case 2: targetBlockZ--; break;
                    case 3: targetBlockZ++; break;
                    case 4: targetBlockX--; break;
                    case 5: targetBlockX++; break;
                }

                if(world.getBlockId(targetBlockX, targetBlockY, targetBlockZ) == 0){
                    world.setBlock(targetBlockX, targetBlockY, targetBlockZ, Block.TORCH.id);
                }
                this.markDead();
            }
                else {
                this.inGround = false;
                this.velocityX = this.velocityX * (double)(this.random.nextFloat() * 0.2F);
                this.velocityY = this.velocityY * (double)(this.random.nextFloat() * 0.2F);
                this.velocityZ = this.velocityZ * (double)(this.random.nextFloat() * 0.2F);
                this.life = 0;
                this.inAirTime = 0;
            }
        } else {
            this.inAirTime++;
            Vec3d var16 = Vec3d.createCached(this.x, this.y, this.z);
            Vec3d var19 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
            HitResult var3 = this.world.raycast(var16, var19, false, true);
            Vec3d var17 = Vec3d.createCached(this.x, this.y, this.z);
            var19 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
            if (var3 != null) {
                var19 = Vec3d.createCached(var3.pos.x, var3.pos.y, var3.pos.z);
            }

            Entity var4 = null;
            List var5 = this.world.getEntities(this, this.boundingBox.stretch(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;

            for (int var8 = 0; var8 < var5.size(); var8++) {
                Entity var9 = (Entity)var5.get(var8);
                if (var9.isCollidable() && (var9 != this.owner || this.inAirTime >= 5)) {
                    float var10 = 0.3F;
                    Box var11 = var9.boundingBox.expand((double)var10, (double)var10, (double)var10);
                    HitResult var12 = var11.raycast(var17, var19);
                    if (var12 != null) {
                        double var13 = var17.distanceTo(var12.pos);
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

            //if arrow hits entity
            if (var3 != null) {
                if (var3.entity != null) {
                    if (var3.entity.damage(this.owner, 1)) {
                        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                        var3.entity.fireTicks = 80;
                        this.markDead();
                    } else {
                        this.velocityX *= -0.1F;
                        this.velocityY *= -0.1F;
                        this.velocityZ *= -0.1F;
                        this.yaw += 180.0F;
                        this.prevYaw += 180.0F;
                        this.inAirTime = 0;
                    }
                } else {
                    this.blockX = var3.blockX;
                    this.blockY = var3.blockY;
                    this.blockZ = var3.blockZ;
                    this.sideHit = var3.side;
                    this.blockId = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
                    this.blockMeta = this.world.getBlockMeta(this.blockX, this.blockY, this.blockZ);
                    this.velocityX = (double)((float)(var3.pos.x - this.x));
                    this.velocityY = (double)((float)(var3.pos.y - this.y));
                    this.velocityZ = (double)((float)(var3.pos.z - this.z));
                    float var22 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
                    this.x = this.x - this.velocityX / (double)var22 * 0.05F;
                    this.y = this.y - this.velocityY / (double)var22 * 0.05F;
                    this.z = this.z - this.velocityZ / (double)var22 * 0.05F;
                    this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.shake = 7;
                }
            }

            this.x = this.x + this.velocityX;
            this.y = this.y + this.velocityY;
            this.z = this.z + this.velocityZ;
            float var23 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / (float) Math.PI);
            this.pitch = (float)(Math.atan2(this.velocityY, (double)var23) * 180.0 / (float) Math.PI);

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
            float var24 = 0.99F;
            float var25 = 0.03F;
            if (this.isSubmergedInWater()) {
                for (int var26 = 0; var26 < 4; var26++) {
                    float var27 = 0.25F;
                    this.world
                            .addParticle(
                                    "bubble",
                                    this.x - this.velocityX * (double)var27,
                                    this.y - this.velocityY * (double)var27,
                                    this.z - this.velocityZ * (double)var27,
                                    this.velocityX,
                                    this.velocityY,
                                    this.velocityZ
                            );
                    this.markDead(); //kill entity upon entering water
                }

                var24 = 0.8F;
            }

            this.velocityX *= (double)var24;
            this.velocityY *= (double)var24;
            this.velocityZ *= (double)var24;
            this.velocityY -= (double)var25;
            this.setPosition(this.x, this.y, this.z);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putShort("xTile", (short)this.blockX);
        nbt.putShort("yTile", (short)this.blockY);
        nbt.putShort("zTile", (short)this.blockZ);
        nbt.putByte("inTile", (byte)this.blockId);
        nbt.putByte("inData", (byte)this.blockMeta);
        nbt.putByte("shake", (byte)this.shake);
        nbt.putByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbt.putBoolean("player", this.pickupAllowed);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.blockX = nbt.getShort("xTile");
        this.blockY = nbt.getShort("yTile");
        this.blockZ = nbt.getShort("zTile");
        this.blockId = nbt.getByte("inTile") & 255;
        this.blockMeta = nbt.getByte("inData") & 255;
        this.shake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;
        this.pickupAllowed = nbt.getBoolean("player");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getShadowRadius() {
        return 0.0F;
    }
}
