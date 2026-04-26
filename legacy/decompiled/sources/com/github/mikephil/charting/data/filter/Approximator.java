package com.github.mikephil.charting.data.filter;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Approximator {
    public float[] reduceWithDouglasPeucker(float[] fArr, float f) {
        Line line = new Line(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1]);
        float f2 = 0.0f;
        int i = 0;
        for (int i2 = 2; i2 < fArr.length - 2; i2 += 2) {
            float fDistance = line.distance(fArr[i2], fArr[i2 + 1]);
            if (fDistance > f2) {
                i = i2;
                f2 = fDistance;
            }
        }
        if (f2 > f) {
            float[] fArrReduceWithDouglasPeucker = reduceWithDouglasPeucker(Arrays.copyOfRange(fArr, 0, i + 2), f);
            float[] fArrReduceWithDouglasPeucker2 = reduceWithDouglasPeucker(Arrays.copyOfRange(fArr, i, fArr.length), f);
            return concat(fArrReduceWithDouglasPeucker, Arrays.copyOfRange(fArrReduceWithDouglasPeucker2, 2, fArrReduceWithDouglasPeucker2.length));
        }
        return line.getPoints();
    }

    float[] concat(float[]... fArr) {
        int length = 0;
        for (float[] fArr2 : fArr) {
            length += fArr2.length;
        }
        float[] fArr3 = new float[length];
        int i = 0;
        for (float[] fArr4 : fArr) {
            for (float f : fArr4) {
                fArr3[i] = f;
                i++;
            }
        }
        return fArr3;
    }

    private class Line {

        /* JADX INFO: renamed from: dx */
        private float f61dx;

        /* JADX INFO: renamed from: dy */
        private float f62dy;
        private float exsy;
        private float length;
        private float[] points;
        private float sxey;

        public Line(float f, float f2, float f3, float f4) {
            this.f61dx = f - f3;
            this.f62dy = f2 - f4;
            this.sxey = f * f4;
            this.exsy = f3 * f2;
            this.length = (float) Math.sqrt((r3 * r3) + (r0 * r0));
            this.points = new float[]{f, f2, f3, f4};
        }

        public float distance(float f, float f2) {
            return Math.abs((((this.f62dy * f) - (this.f61dx * f2)) + this.sxey) - this.exsy) / this.length;
        }

        public float[] getPoints() {
            return this.points;
        }
    }
}
