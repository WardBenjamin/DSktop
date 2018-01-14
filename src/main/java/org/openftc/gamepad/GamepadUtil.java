package org.openftc.gamepad;

import com.qualcomm.robotcore.util.Range;

public class GamepadUtil {

    /**
     * DPAD button will be considered pressed when the movement crosses this
     * threshold
     */
    protected float dpadThreshold = 0.2f;

    /**
     * If the motion value is less than the threshold, the controller will be
     * considered at rest
     */
    protected float joystickDeadzone = 0.2f; // very high, since we don't know the device type

    // clean values
    // remove values larger than max
    // apply deadzone logic
//    protected float cleanMotionValues(float number) {
//
//        // apply deadzone
//        if (number < joystickDeadzone && number > -joystickDeadzone) return 0.0f;
//
//        // apply trim
//        if (number > MAX_MOTION_RANGE) return MAX_MOTION_RANGE;
//        if (number < -MAX_MOTION_RANGE) return -MAX_MOTION_RANGE;
//
//        // scale values "between deadzone and trim" to be "between 0 and Max range"
//        if (number > 0)
//            number = (float) Range.scale(number, joystickDeadzone, MAX_MOTION_RANGE, 0, MAX_MOTION_RANGE);
//        else
//            number = (float) Range.scale(number, -joystickDeadzone, -MAX_MOTION_RANGE, 0, -MAX_MOTION_RANGE);
//
//        return number;
//    }


    /**
     * Display a summary of this gamepad, including the state of all buttons, analog sticks, and triggers
     *
     * @return a summary
     */
//    @Override
//    public String toString() {
//        String buttons = new String();
//        if (dpad_up) buttons += "dpad_up ";
//        if (dpad_down) buttons += "dpad_down ";
//        if (dpad_left) buttons += "dpad_left ";
//        if (dpad_right) buttons += "dpad_right ";
//        if (a) buttons += "a ";
//        if (b) buttons += "b ";
//        if (x) buttons += "x ";
//        if (y) buttons += "y ";
//        if (guide_button) buttons += "guide_button ";
//        if (start) buttons += "start ";
//        if (back) buttons += "back ";
//        if (left_bumper) buttons += "left_bumper ";
//        if (right_bumper) buttons += "right_bumper ";
//        if (left_stick_button) buttons += "left stick button ";
//        if (right_stick_button) buttons += "right stick button ";
//
//        return String.format("ID: %2d user: %2d lx: % 1.2f ly: % 1.2f rx: % 1.2f ry: % 1.2f lt: %1.2f rt: %1.2f %s",
//                id, user, left_stick_x, left_stick_y,
//                right_stick_x, right_stick_y, left_trigger, right_trigger, buttons);
//    }
}
