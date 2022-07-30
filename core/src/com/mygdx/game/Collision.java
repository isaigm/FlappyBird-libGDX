package com.mygdx.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collision {
    public static boolean detectCollision(Circle circle, Rectangle rectangle)
    {

        float xrect = rectangle.x;
        float yrect = rectangle.y;
        float xcirc = circle.x;
        float ycirc = circle.y;
        float r = circle.radius;
        if(circle.contains(xrect, yrect) || circle.contains(xrect, yrect + rectangle.height) ||
        circle.contains(xrect + rectangle.width, yrect) || circle.contains(xrect + rectangle.width,yrect + rectangle.height)) return true;

        if(rectangle.contains(xcirc, ycirc - r) || rectangle.contains(xcirc, ycirc + r) ||
        rectangle.contains(xcirc + r, ycirc) || rectangle.contains(xcirc - r, ycirc)) return true;

        return false;
    }

}
