package com.autonomousvehicle;

/**
 * Created by Khemar on 2016-10-17.
 */

public interface JoystickMovedListener {
        public void OnMoved(int pan, int tilt);
        public void OnReleased();
}

