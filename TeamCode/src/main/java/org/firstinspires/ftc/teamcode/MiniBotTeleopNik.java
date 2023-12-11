package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="MiniTeleopNik")

public class MiniBotTeleopNik extends LinearOpMode{

    minibot robot = new minibot();


    private double speedControl = 0.5;
    private double turnControl = 0.5;
    double wristPosition = 0.3;
    @Override


    public void runOpMode()
    {
        robot.init(hardwareMap,this);

       // robot.autopixel.setPosition(0.05);


        waitForStart();

        while (opModeIsActive()) {




            telemetry.update();


            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0) {
                robot.motorLeft.setPower((gamepad1.left_stick_x - gamepad1.right_stick_x * 0.35) * speedControl);
                robot.motorRight.setPower((-gamepad1.left_stick_x - gamepad1.right_stick_x * 0.35) * speedControl);
                robot.motorFront.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x * 0.35) * speedControl);
                robot.motorBack.setPower((-gamepad1.left_stick_y - gamepad1.right_stick_x * 0.35) * speedControl);
            } else if (gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0) {
                robot.motorLeft.setPower(-gamepad1.right_stick_x * turnControl);
                robot.motorRight.setPower(-gamepad1.right_stick_x * turnControl);
                robot.motorFront.setPower(-gamepad1.right_stick_x * turnControl);
                robot.motorBack.setPower(-gamepad1.right_stick_x * turnControl);
            } else {

                robot.motorLeft.setPower(0);
                robot.motorRight.setPower(0);
                robot.motorFront.setPower(0);
                robot.motorBack.setPower(0);
            }


            if (gamepad1.dpad_up) speedControl = 1.0;
            if (gamepad1.dpad_left) speedControl = 0.75;
            if (gamepad1.dpad_right) speedControl = 0.5;
            if (gamepad1.dpad_down) speedControl = 0.25;


            if (gamepad1.dpad_up) turnControl = 0.75;
            if (gamepad1.dpad_left) turnControl = 0.5;
            if (gamepad1.dpad_right) turnControl = 0.35;
            if (gamepad1.dpad_down) turnControl = 0.25;


            if (gamepad2.y&& gamepad2.left_bumper) robot.plane.setPower(-1);
            else robot.plane.setPower(0);

           // if (gamepad1.b) robot.autopixel.setPosition(0.5);
            //if (gamepad1.x) robot.autopixel.setPosition(0.05);






            if (gamepad2.dpad_up)  wristPosition = 0.5;
            if (gamepad2.dpad_right) wristPosition = 0.2;
            if (gamepad2.dpad_left) wristPosition = 0.2;
            if (gamepad2.dpad_down) wristPosition = 0;
            if(gamepad2.right_bumper) wristPosition= 0.5-Math.abs(gamepad2.right_stick_y)*0.5;


            robot.wrist.setPosition(wristPosition);







        }}}