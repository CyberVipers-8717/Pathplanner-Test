package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Queuer extends Command {
    private final ShooterSubsystem feeder;

    private final double feederSpeed;

    public Queuer(ShooterSubsystem feeder, double feederSpeed){
        this.feeder = feeder;
        this.feederSpeed = feederSpeed;
        addRequirements(feeder);
    }

    public void initialize(){
        feeder.setShooterRPM(feederSpeed);
        feeder.setQueuerRPM(feederSpeed);
    }

    public void execute(){
        System.out.println("Shooter at target RPM, Queuer activated.");
        System.out.println("TopShooter is running at: " + feeder.getTopShooterRPM());
        System.out.println("BottomShooter is running at " + feeder.getBottomShooterRPM());
        System.out.println("Queuer is running at: " + feeder.getQueuerRPM());
        System.out.println("Queuer current (A): " + feeder.getQueuerCurrent());
    }

    public void end(){

    }

    public boolean isFinished(){
        return false;
    }
}
