public class OptiMath
{
    static float[] sinTable, cosTable;
    final static float PI = (float) Math.PI;
    
    public static void precalculate() {
        sinTable = new float[36000];
        cosTable = new float[36000];
        for(int i = 0; i<36000; i++) {
            float deg = i/100F;
            double rad = deg/180*Math.PI;
            sinTable[i] = (float) Math.sin(rad);
            cosTable[i] = (float) Math.cos(rad);
        }      
    }
    
    public static float sin(float deg) {        
        deg = (deg%360)*100;
        deg = (deg < 0)?360-deg:deg;
        return sinTable[(int)deg];
    }
    
    public static float cos(float deg) {
        deg = (deg%360)*100;
        deg = (deg < 0)?360-deg:deg;
        return cosTable[(int)deg];
    }
}
