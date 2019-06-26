public class FPSCounter {
    
    private static long startTime = 0;
    private static float[] lastFrameTimes = new float[30];
    
    public static void startFrame() {
        startTime = System.currentTimeMillis();
    }
    
    public static Object[] stopFrame() {
        float avgTime = 0;
        for(int i = 1;i<lastFrameTimes.length;i++){
            avgTime += lastFrameTimes[i];
            lastFrameTimes[i-1] = lastFrameTimes[i];
        }
        lastFrameTimes[lastFrameTimes.length-1] = System.currentTimeMillis()-startTime;
        avgTime += lastFrameTimes[lastFrameTimes.length-1];
        avgTime /= lastFrameTimes.length;
        
        return new Object[]{avgTime, (int) (1000.0/avgTime), lastFrameTimes[lastFrameTimes.length-1]};
    }
}
