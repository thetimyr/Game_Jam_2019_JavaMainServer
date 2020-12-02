package SkyCrafting.Main.Entyti;

import net.minecraft.server.v1_8_R3.*;

public class PathfinderGoalHold extends PathfinderGoal
{
    private final EntityCreature creature;
    private double destX;
    private double destY;
    private double destZ;
    private final double speed;
    private int chanceToNot;
    private boolean notPathing;
    
    public PathfinderGoalHold(final EntityCreature creature, final double speed) {
        this(creature, speed, 120);
    }
    
    public PathfinderGoalHold(final EntityCreature creature, final double speed, final int chanceToNot) {
        this.creature = creature;
        this.speed = speed;
        this.setChanceToNot(chanceToNot);
        this.a(1);
    }
    
    public boolean a() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public void c() {
        this.creature.getNavigation().a(this.destX, this.destY, this.destZ, this.speed);
    }
    
    public void f() {
        this.setNotPathing(true);
    }
    
    public void setTimeBetweenMovement(final int paramInt) {
        this.setChanceToNot(paramInt);
    }
    
    public int getChanceToNot() {
        return this.chanceToNot;
    }
    
    public void setChanceToNot(final int chanceToNot) {
        this.chanceToNot = chanceToNot;
    }
    
    public boolean isNotPathing() {
        return this.notPathing;
    }
    
    public void setNotPathing(final boolean notPathing) {
        this.notPathing = notPathing;
    }
}
