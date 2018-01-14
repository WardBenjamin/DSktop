package org.openftc.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import org.openftc.app.MyApp;

import java.io.Console;

public class GamepadManager {

    Gamepad gamepad_a = new Gamepad(), gamepad_b = new Gamepad();
    int controller_a_index = -1, controller_b_index = -1;

    public void main_loop() {
        ControllerManager controllers = new ControllerManager();
        controllers.initSDLGamepad();

        // TODO: This should not be infinite
        while(true) {
            // Loop through all connected controllers and look for Start-A or Start-B
            // TODO: Better state caching?
            for (int i = 0; i < controllers.getNumControllers(); i++) {
                ControllerState state = controllers.getState(i);
                if(state.isConnected) {
                    if(state.start) {
                        if(state.a)
                            controller_a_index = i;
                        else if (state.b)
                            controller_b_index = i;
                    }
                }
            }

            // If there's a controller attached that's assigned to A or B, update the gamepad state
            if(controller_a_index != -1) {
                ControllerState state = controllers.getState(controller_a_index);
                if(!state.isConnected) {
                    System.out.println("Controller A disconnected. Old ID: " + controller_a_index);
                    controller_a_index = -1;
                }
                else {
                    setGamepad(gamepad_a, state);
                }
            }
            if(controller_b_index != -1) {
                ControllerState state = controllers.getState(controller_b_index);
                if(!state.isConnected) {
                    System.out.println("Controller B disconnected. Old ID: " + controller_b_index);
                    controller_b_index = -1;
                }
                else {
                    setGamepad(gamepad_b, state);
                }
            }

            // TODO: Actually send gamepads
        }

        // TODO: Uncomment this after the loop is no longer infinite. Necessary for cleanup.
//        controllers.quitSDLGamepad();
    }

    private void setGamepad(Gamepad gamepad, ControllerState state) {
        gamepad.left_stick_x = state.leftStickX;
        gamepad.left_stick_y = state.leftStickY;
        gamepad.right_stick_x = state.rightStickX;
        gamepad.right_stick_y = state.rightStickY;
        gamepad.left_stick_button = state.leftStickClick;
        gamepad.right_stick_button = state.rightStickClick;
        gamepad.dpad_up = state.dpadUp;
        gamepad.dpad_down = state.dpadDown;
        gamepad.dpad_left = state.dpadLeft;
        gamepad.dpad_right = state.dpadRight;
        gamepad.a = state.a;
        gamepad.b = state.b;
        gamepad.x = state.x;
        gamepad.y = state.y;
        gamepad.guide_button = state.guide;
        gamepad.start_button = state.start;
        gamepad.back_button = state.back;
        gamepad.left_bumper = state.lb;
        gamepad.right_bumper = state.rb;
        gamepad.left_trigger = state.leftTrigger;
        gamepad.right_trigger = state.rightTrigger;

        refreshTimestamp(gamepad);
    }


    /**
     * Refreshes the Gamepad's timestamp to be the current time. The timestamp
     * represents the time at which the gamepad last changed its state.
     */
    private void refreshTimestamp(Gamepad gamepad) {
        gamepad.timestamp = MyApp.Companion.getRuntime().msLong();
    }

}
