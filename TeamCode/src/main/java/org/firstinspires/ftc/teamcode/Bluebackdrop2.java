package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Scalar;

@Autonomous(name="Bluebackdrop2")
public class Bluebackdrop2 extends LinearOpMode {
	private VisionPortal visionPortal;
	private ColourMassDetectionProcessor colourMassDetectionProcessor;
	minibot robot = new minibot();
	

	@Override
	public void runOpMode() throws InterruptedException{
		// the current range set by lower and upper is the full range
		// HSV takes the form: (HUE, SATURATION, VALUE)
		// which means to select our colour, only need to change HUE
		// the domains are: ([0, 180], [0, 255], [0, 255])
		// this is tuned to detect red, so you will need to experiment to fine tune it for your robot
		// and experiment to fine tune it for blue
		robot.init(hardwareMap, this);
		robot.autopixel.setPosition(0);
		//robot.wrist.setPosition(0.2);
		Scalar lower = new Scalar(100, 100, 100); // the lower hsv threshold for your detection
		Scalar upper = new Scalar(130, 255, 255); // the upper hsv threshold for your detection
		double minArea = 100; // the minimum area for the detection to consider for your prop
		
		colourMassDetectionProcessor = new ColourMassDetectionProcessor(
				lower,
				upper,
				() -> minArea, // these are lambda methods, in case we want to change them while the match is running, for us to tune them or something
				() -> 213, // the left dividing line, in this case the left third of the frame
				() -> 426 // the left dividing line, in this case the right third of the frame
		);
		visionPortal = new VisionPortal.Builder()
				.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1")) // the camera on your robot is named "Webcam 1" by default
				.addProcessor(colourMassDetectionProcessor)
				.build();
		
		// you may also want to take a look at some of the examples for instructions on
		// how to have a switchable camera (switch back and forth between two cameras)
		// or how to manually edit the exposure and gain, to account for different lighting conditions
		// these may be extra features for you to work on to ensure that your robot performs
		// consistently, even in different environments

	


	 while (!isStarted()) {
		telemetry.addData("Currently Recorded Position", colourMassDetectionProcessor.getRecordedPropPosition());
		telemetry.addData("Camera State", visionPortal.getCameraState());
		telemetry.addData("Currently Detected Mass Center", "x: " + colourMassDetectionProcessor.getLargestContourX() + ", y: " + colourMassDetectionProcessor.getLargestContourY());
		telemetry.addData("Currently Detected Mass Area", colourMassDetectionProcessor.getLargestContourArea());
		telemetry.update();
	}
	

		waitForStart();

		// shuts down the camera once the match starts, we dont need to look any more
		if (visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING) {
			visionPortal.stopLiveView();
			visionPortal.stopStreaming();
		}
		
		// gets the recorded prop position
		ColourMassDetectionProcessor.PropPositions recordedPropPosition = colourMassDetectionProcessor.getRecordedPropPosition();
		
		// now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
		// if it is UNFOUND, you can manually set it to any of the other positions to guess
		if (recordedPropPosition == ColourMassDetectionProcessor.PropPositions.UNFOUND) {
			recordedPropPosition = ColourMassDetectionProcessor.PropPositions.MIDDLE;
		}
		
		
		// now we can use recordedPropPosition in our auto code to modify where we place the purple and yellow pixels
		switch (recordedPropPosition) {
			case LEFT:
				// code to do if we saw the prop on the left
				telemetry.addData("movement: ", "I will go left");
				telemetry.update();

				robot.encoderForwardDrive(0.25,27,5,this);
				robot.encoderSideDrive(0.25,11.5,5,this);
				robot.encoderForwardDrive(-0.25,-15,5,this);

				//robot.turnLeftAngle(0.2,89,this);
				robot.turnRightAngle(0.2,89,this);


				//changed sign of speed and inches from this point forward
				robot.encoderForwardDrive(-0.25,-10,5,this);
				robot.encoderSideDrive(0.25,-3,5.,this); //change
				robot.encoderForwardDrive(-0.25,-24,5,this);
				robot.autopixel.setPosition(0.55);
				sleep(2000);
				robot.autopixel.setPosition(0);
				robot.encoderForwardDrive(0.25,2,5,this);
				robot.encoderSideDrive(-0.2,-7,5,this);


				break;


			case UNFOUND: // we can also just add the unfound case here to do fallthrough intstead of the overriding method above, whatever you prefer!


			case MIDDLE:
				// code to do if we saw the prop on the middle
				telemetry.addData("movement: ", "I will go to center");
				telemetry.update();
				robot.encoderForwardDrive(0.25,34,5,this);
				robot.encoderForwardDrive(-0.25,-12,5,this);


				robot.turnRightAngle(0.2,90,this);
				robot.encoderForwardDrive(-0.25,-30,5,this);
				robot.encoderSideDrive(-0.25,-3,5,this);
				robot.encoderForwardDrive(-0.25,-15.5,5,this);
				robot.autopixel.setPosition(0.55);
				sleep(2000);
				robot.autopixel.setPosition(0);
				robot.encoderForwardDrive(0.25,5,5,this);
				robot.encoderSideDrive(-0.2,-15,5,this);


				break;


			case RIGHT:
				// code to do if we saw the prop on the right
				telemetry.addData("movement: ", "I will go right");
				telemetry.update();
				robot.encoderForwardDrive(0.25,28,5,this);
				robot.encoderSideDrive(0.25,-13,5,this);
				robot.encoderForwardDrive(-0.25,-2.8,5,this);
				robot.encoderSideDrive(0.25,18,5,this);


				robot.turnRightAngle(0.2,89,this);
				robot.encoderForwardDrive(-0.25,-5,5,this);
				robot.encoderSideDrive(0.25,0,5,this);//edit
				robot.encoderForwardDrive(-0.25,-33,5,this);

				robot.autopixel.setPosition(0.55);
				sleep(2000);
				robot.autopixel.setPosition(0);

				robot.encoderForwardDrive(0.25,4,5,this);
				robot.encoderSideDrive(-0.2,-20,5,this);


				break;


		}

		colourMassDetectionProcessor.close();
		visionPortal.close();
	}
	




}
