package zn.cvh.module.modules; import zn.cvh.module.Mod;
import zn.cvh.util.TimeUtil;

import java.awt.Robot;
 import java.lang.reflect.Field;
 import java.util.Random;
 
 public class Triggerbot
   extends Mod
 {
   private Field objectMouseOverField;
   private Field entityHitField;
   private Field inGameHasFocusField;
   private TimeUtil time = new TimeUtil();
   private Random random = new Random();
   private Robot robot;
   
   public Triggerbot() throws Exception {
     super("cvh.modules.Triggerbot");
     this.objectMouseOverField = mcClass.getField("field_71476_x");
     this.objectMouseOverField.setAccessible(true);
     this.entityHitField = this.objectMouseOverField.getType().getField("field_72308_g");
     this.inGameHasFocusField = mcClass.getField("field_71415_G");
     this.robot = new Robot();
   }
   
   public void update() throws Exception {
     Object objectMouseOverObj = this.objectMouseOverField.get(mcObj);
     Object entityHitObj = this.entityHitField.get(objectMouseOverObj);
     boolean inGameHasFocus = this.inGameHasFocusField.getBoolean(mcObj);
     if (entityHitObj != null && this.time.hasReached(50L) && inGameHasFocus) {
       if (this.random.nextBoolean()) {
         this.robot.mousePress(16);
         this.robot.mouseRelease(16);
       } 
       this.time.reset();
     } 
   }
 }


