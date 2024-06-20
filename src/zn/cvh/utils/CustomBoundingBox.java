package zn.cvh.utils;

public class CustomBoundingBox {
    public final double minX, minY, minZ;
    public final double maxX, maxY, maxZ;

    public CustomBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }


}
