package edu.calstatela.jplone.arframework.graphics3d.scene;

import android.content.Context;

import edu.calstatela.jplone.arframework.graphics3d.drawable.Billboard;
import edu.calstatela.jplone.arframework.graphics3d.drawable.BillboardMaker;
import edu.calstatela.jplone.arframework.graphics3d.entity.Entity;
import edu.calstatela.jplone.arframework.graphics3d.entity.ScaleObject;

public class LandmarkScene extends ScalingCircleScene{

    public void addLandmark(Context context, int iconResourceId, String title, String description){
        Billboard bb = BillboardMaker.make(context, iconResourceId, title, description);
        ScaleObject scaleBB = new ScaleObject(bb, 2, 1, 1);
        super.addDrawable(scaleBB);
    }

    public int findClosestEntity(float targetX, float targetY, float screenWidth, float screenHeight, float percentSlop, float[] projectionMatrix, float[] viewMatrix, float[] position3d){
        float maxDistance = percentSlop * Math.min(screenWidth, screenHeight);

        float[] xy = new float[2];

        float shortestDistance = -1;
        int bestIndex = -1;

        for(int i = 0; i < mEntityList.size(); i++){
            Entity e = mEntityList.get(i);
            e.getScreenPosition(xy, projectionMatrix, viewMatrix, e.getModelMatrix(), screenWidth, screenHeight);

            float distance = distance(xy[0], xy[1], targetX, targetY);
            if(distance <= maxDistance){
                distance = distance(position3d, e.getPosition());
                if(bestIndex < 0 || distance < shortestDistance){
                    bestIndex = i;
                    shortestDistance = distance;
                }
            }
        }

        return bestIndex;
    }

    private float distance(float x1, float y1, float x2, float y2){
        float diff1 = x2 - x1;
        float diff2 = y2 - y1;
        return (float)Math.sqrt(diff1 * diff1 + diff2 * diff2);
    }

    private float distance(float[] p1, float[] p2){
        float sum = 0;
        float diff;

        diff = p1[0] - p2[0];
        sum += diff * diff;
        diff = p1[1] - p2[1];
        sum += diff * diff;
        diff = p1[2] - p2[2];
        sum += diff * diff;

        return (float)Math.sqrt(sum);
    }

}
