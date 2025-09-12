package com.mycompany.creepyatlas.Utils;

public class Distance {
  public static float Manhattan(int pos1x, int pos1y, int pos2x, int pos2y) {
    return Math.abs(pos1x - pos2x) + Math.abs(pos1y - pos2y);
  }

  public static float Manhattan(float pos1x, float pos1y, float pos2x, float pos2y) {
    return Math.abs(pos1x - pos2x) + Math.abs(pos1y - pos2y);
  }

  public static float Euclidean(int pos1x, int pos1y, int pos2x, int pos2y) {
    return (float) Math.sqrt(
        Math.pow(pos1x - pos2x, 2) + Math.pow(pos1y - pos2y, 2)
    );
  }

  public static float Euclidean(float pos1x, float pos1y, float pos2x, float pos2y) {
    return (float) Math.sqrt(
        Math.pow(pos1x - pos2x, 2) + Math.pow(pos1y - pos2y, 2)
    );
  }
}
