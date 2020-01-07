package cn.sxhengrun.purchase;

public class Increment {
    private int num = 0;

    public int getAndIncrement() {
        return num++;
    }

    private Increment() {}

   public static Increment newInstance() {
        return new Increment();
    }
}
