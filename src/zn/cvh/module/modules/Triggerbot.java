package zn.cvh.module.modules;

import zn.cvh.module.Mod;
import zn.cvh.module.value.values.BoolValues;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.utils.TimeUtil;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Triggerbot
        extends Mod {
    public DoubleValue minCps = new DoubleValue("Minimum CPS", 8, 1, 20);
    public DoubleValue maxCps = new DoubleValue("Maximum CPS", 12, 1, 20);
    public BoolValues onlyWhileClicking = new BoolValues("Only While Clicking", false);
    private final Field objectMouseOverField = mcClass.getField("field_71476_x");
    private final Field entityHitField;
    private final Field inGameHasFocusField;
    private final TimeUtil time = new TimeUtil();
    private final Random random = new Random();
    private final Robot robot;


    public Triggerbot() throws Exception {
        super("Triggerbot");
        this.objectMouseOverField.setAccessible(true);
        this.entityHitField = this.objectMouseOverField.getType().getField("field_72308_g");
        this.inGameHasFocusField = mcClass.getField("field_71415_G");
        this.robot = new Robot();
        addValue(minCps);
        addValue(maxCps);
        addValue(onlyWhileClicking);
    }

    public void update() throws Exception {
        Object objectMouseOverObj = this.objectMouseOverField.get(mcObj);
        Object entityHitObj = this.entityHitField.get(objectMouseOverObj);
        boolean inGameHasFocus = this.inGameHasFocusField.getBoolean(mcObj);

        if (entityHitObj != null && this.time.hasReached(getSleepTime()) && inGameHasFocus) {

            this.robot.mousePress(16);
            this.robot.mouseRelease(16);

            this.robot.mousePress(16);
            this.robot.mouseRelease(16);

            this.time.reset();

        }
    }


    private long getSleepTime() {
        double minCpsValue = minCps.getValue();
        double maxCpsValue = maxCps.getValue();
        double cps = ThreadLocalRandom.current().nextDouble(minCpsValue, maxCpsValue);
        return Math.round(1000.0 / cps);
    }


}
