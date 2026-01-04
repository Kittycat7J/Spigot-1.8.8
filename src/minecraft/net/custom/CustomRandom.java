package net.custom;

import java.util.Random;

public class CustomRandom extends Random {

    public CustomRandom() {
        super();
    }

    public CustomRandom(long seed) {
        super(seed);
    }

    @Override
    protected int next(int bits) {
        int result = super.next(bits);
        System.out.println("[Random] next(" + bits + ") = " + result);
        return result;
    }

    @Override
    public boolean nextBoolean() {
        boolean result = super.nextBoolean();
        System.out.println("[Random] nextBoolean() = " + result);
        return result;
    }

    @Override
    public void nextBytes(byte[] bytes) {
        super.nextBytes(bytes);
        System.out.println("[Random] nextBytes(" + bytes.length + ")");
    }

    @Override
    public double nextDouble() {
        double result = super.nextDouble();
        System.out.println("[Random] nextDouble() = " + result);
        return result;
    }

    @Override
    public float nextFloat() {
        float result = super.nextFloat();
        System.out.println("[Random] nextFloat() = " + result);
        return result;
    }

    @Override
    public double nextGaussian() {
        double result = super.nextGaussian();
        System.out.println("[Random] nextGaussian() = " + result);
        return result;
    }

    @Override
    public int nextInt() {
        int result = super.nextInt();
        System.out.println("[Random] nextInt() = " + result);
        return result;
    }

    @Override
    public int nextInt(int bound) {
        int result = super.nextInt(bound);
        System.out.println("[Random] nextInt(" + bound + ") = " + result);
        return result;
    }

    @Override
    public long nextLong() {
        long result = super.nextLong();
        System.out.println("[Random] nextLong() = " + result);
        return result;
    }

    @Override
    public synchronized void setSeed(long seed) {
        System.out.println("[Random] setSeed(" + seed + ")");
        super.setSeed(seed);
    }
}
