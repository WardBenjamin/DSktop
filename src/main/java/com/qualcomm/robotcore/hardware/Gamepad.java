/*
 * Copyright (c) 2014, 2015 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.robocol.RobocolParsableBase;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Monitor a hardware gamepad.
 * <p>
 * The buttons, analog sticks, and triggers are represented a public
 * member variables that can be read from or written to directly.
 * <p>
 * Analog sticks are represented as floats that range from -1.0 to +1.0. They will be 0.0 while at
 * rest. The horizontal axis is labeled x, and the vertical axis is labeled y.
 * <p>
 * Triggers are represented as floats that range from 0.0 to 1.0. They will be at 0.0 while at
 * rest.
 * <p>
 * Buttons are boolean values. They will be true if the button is pressed, otherwise they will be
 * false.
 * <p>
 * The dpad is represented as 4 buttons, dpad_up, dpad_down, dpad_left, and dpad_right
 */
@SuppressWarnings("unused")
public class Gamepad extends RobocolParsableBase {

    public float left_stick_x = 0f; // left analog stick horizontal axis
    public float left_stick_y = 0f; // left analog stick vertical axis
    public float right_stick_x = 0f; // right analog stick horizontal axis
    public float right_stick_y = 0f; // right analog stick vertical axis

    public boolean left_stick_button = false;
    public boolean right_stick_button = false;

    public boolean dpad_up = false;
    public boolean dpad_down = false;
    public boolean dpad_left = false;
    public boolean dpad_right = false;
    public boolean a = false;
    public boolean b = false;
    public boolean x = false;
    public boolean y = false;

    /**
     * button guide - often the large button in the middle of the controller. The OS may
     * capture this button before it is sent to the app; in which case you'll never
     * receive it.
     */
    public boolean guide_button = false;
    public boolean start_button = false; // Start button
    public boolean back_button = false; // Back button

    public boolean left_bumper = false;
    public boolean right_bumper = false;

    public float left_trigger = 0f;
    public float right_trigger = 0f;

    private byte user = GamepadUser.UNASSOCIATED.id; // Which user is this gamepad used by

    public GamepadUser getUser() {
        return GamepadUser.from(user);
    }
    public void setUser(GamepadUser user) {
        this.user = user.id;
    }

    /**
     * Relative timestamp of the last time an event was detected
     */
    public long timestamp = 0;

    // private static values used for packaging the gamepad state into a byte array
    private static final short PAYLOAD_SIZE = 42;
    private static final short BUFFER_SIZE = PAYLOAD_SIZE + RobocolParsable.HEADER_LENGTH;

    private static final byte ROBOCOL_VERSION = 2;

    @Override
    public MsgType getRobocolMsgType() {
        return MsgType.GAMEPAD;
    }

    @Override
    public byte[] toByteArray() throws RobotCoreException {

        ByteBuffer buffer = getWriteBuffer(PAYLOAD_SIZE);

        try {
            int buttons = 0;

            buffer.put(ROBOCOL_VERSION);
            buffer.putInt(id);
            buffer.putLong(timestamp).array();
            buffer.putFloat(left_stick_x).array();
            buffer.putFloat(left_stick_y).array();
            buffer.putFloat(right_stick_x).array();
            buffer.putFloat(right_stick_y).array();
            buffer.putFloat(left_trigger).array();
            buffer.putFloat(right_trigger).array();

            buttons = (buttons << 1) + (left_stick_button ? 1 : 0);
            buttons = (buttons << 1) + (right_stick_button ? 1 : 0);
            buttons = (buttons << 1) + (dpad_up ? 1 : 0);
            buttons = (buttons << 1) + (dpad_down ? 1 : 0);
            buttons = (buttons << 1) + (dpad_left ? 1 : 0);
            buttons = (buttons << 1) + (dpad_right ? 1 : 0);
            buttons = (buttons << 1) + (a ? 1 : 0);
            buttons = (buttons << 1) + (b ? 1 : 0);
            buttons = (buttons << 1) + (x ? 1 : 0);
            buttons = (buttons << 1) + (y ? 1 : 0);
            buttons = (buttons << 1) + (guide_button ? 1 : 0);
            buttons = (buttons << 1) + (start_button ? 1 : 0);
            buttons = (buttons << 1) + (back_button ? 1 : 0);
            buttons = (buttons << 1) + (left_bumper ? 1 : 0);
            buttons = (buttons << 1) + (right_bumper ? 1 : 0);
            buffer.putInt(buttons);

            buffer.put(user);
        } catch (BufferOverflowException e) {
            RobotLog.logStacktrace(e);
        }

        return buffer.array();
    }

    @Override
    public void fromByteArray(byte[] byteArray) throws RobotCoreException {
        // NO-OP
    }
}
