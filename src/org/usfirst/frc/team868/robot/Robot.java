package org.usfirst.frc.team868.robot;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	// Controls how quick the MORSE code characters come out
	private static final double MORSE_UNIT = 0.1;
	private DigitalOutput led;

	Joystick fightPad = new Joystick(1);
	Joystick Xbone = new Joystick(0);
	Jaguar shooter1 = new Jaguar(7);
	Jaguar shooter2 = new Jaguar(6);
	Relay feeder = new Relay(0);
	Jaguar turretMotor = new Jaguar(8);
	Victor turnMotor1 = new Victor(1);
	Victor turnMotor2 = new Victor(2);
	Victor turnMotor3 = new Victor(3);
	Victor turnMotor4 = new Victor(4);
	
	public void setDriveSpeed(double left,double right){
		turnMotor2.set(left);
		turnMotor3.set(left);
		turnMotor4.set(right);
		turnMotor1.set(right);
	}
	
	public void setShooterSpeed(double speed) {
		shooter1.set(-Math.abs(speed));
		shooter2.set(Math.abs(speed));

	}
	
	public void setTurretSpeed(double speed) {
		turretMotor.set(speed);
	}

	public void setFeederOn() {
		feeder.set(Value.kReverse);
	}

	public void setFeederOff() {
		feeder.set(Value.kOff);
	}

	public Robot() {

	}

	@Override
	public void robotInit() {
		led = new DigitalOutput(1);
		turnMotor1.setInverted(true);
		turnMotor3.setInverted(true);
		LiveWindow.addActuator("shooter", "jag1", shooter1);
		LiveWindow.addActuator("shooter", "jag2", shooter2);
		LiveWindow.addActuator("feeder", "feeder", feeder);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {
		/*
		 * led.set(true); Timer.delay(3.0); led.set(false);
		 */

		SOS();

		/*
		 * ... --- ... (s.o.s.) while (isAutonomous()) { SOS(); /* // Send "S"
		 * ... blink3(dot, dot, dot); // Send "O" --- blink3(dash, dash, dash);
		 * // Send "S" ... blink3(dot, dot, dot);
		 * 
		 * // Let's wait 5 seconds before next call for help Timer.delay(3.0); }
		 */
		/*
		 * led.set(true);
		 * 
		 * Timer.delay(a); Set Timer.delay(b); Timer.delay(b); Timer.delay(b);
		 * Timer.delay(b); Timer.delay(b); Timer.delay(b); Timer.delay(b);
		 * Timer.delay(b); Timer.delay(b); Timer.delay(b); Timer.delay(b);
		 * Timer.delay(b); Timer.delay(b); Timer.delay(b); Timer.delay(b);
		 * Timer.delay(b); led.set(false); //System.out.println(DotOn);
		 */
		/* ... --- ... (s.o.s.) */

	}

	/**
	 * This method will do 3 blinks on the LED, you can control how long each
	 * blink is on for (the off time is fixed).
	 * 
	 * @param on1
	 *            How long to be on for the first blink (seconds).
	 * @param on2
	 *            How long to be on for the second blink (seconds).
	 * @param on3
	 *            How long to be on for the third blink (seconds).
	 */
	private void blink3(double on1, double on2, double on3) {
		// Should be same as dot time
		double shortOff = MORSE_UNIT;
		double longOff = 3 * shortOff;

		// Blink LED for duration of first part of letter
		blinkLed(on1);
		// Leave off short time before next blink
		Timer.delay(shortOff);
		// Blink LED for duration of second part of letter
		blinkLed(on2);
		// Leave off short time before next blink
		Timer.delay(shortOff);
		// Blink LED for duration of third part of letter
		blinkLed(on3);
		// Leave off for long time to mark end of letter
		Timer.delay(longOff);
	}

	private void SOS() {
		setShooterSpeed(.4);

		double dot = MORSE_UNIT;
		// Wikipedia Morse Code entry says days is 3 times a dot
		double dash = 3 * dot;
		// Send "S" ...
		blink3(dot, dot, dot);
		// Send "O" ---
		blink3(dash, dash, dash);
		// Send "S" ...
		blink3(dot, dot, dot);

		setShooterSpeed(0);

	}

	/**
	 * Turns LED on for the time specified then turns it back off.
	 * 
	 * @param onTime
	 *            How long to leave the LED on.
	 */
	private void blinkLed(double onTime) {
		led.set(true);
		Timer.delay(onTime);
		led.set(false);
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			// stick)digi
			Timer.delay(0.005); // wait for a motor update time
			boolean click = fightPad.getRawButton(1);
			if (click == true) {
				SOS();
			}
			double speed = Xbone.getRawAxis(3);
			setShooterSpeed(speed);
			setTurretSpeed(Xbone.getRawAxis(4));
			setDriveSpeed(Xbone.getRawAxis(1), Xbone.getRawAxis(1));
			click = Xbone.getRawButton(2);
			if (click == true) {
				if(feeder.get() == Relay.Value.kReverse) setFeederOff();
				else setFeederOn();
			}

		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
