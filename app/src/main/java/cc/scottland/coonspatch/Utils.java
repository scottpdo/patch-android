package cc.scottland.coonspatch;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class Utils {
    public static float clamp(float x, float min, float max) {
        return Math.max(min, Math.min(x, max));
    }
}
