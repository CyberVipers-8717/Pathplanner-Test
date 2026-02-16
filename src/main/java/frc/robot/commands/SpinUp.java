package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class SpinUp extends Command {
    private final ShooterSubsystem launcher;

    private final double speed;

    public SpinUp(ShooterSubsystem launcher, double speed) {
        this.launcher = launcher;
        this.speed = speed;
        addRequirements(launcher);
    }

    public void initialize(){
        launcher.setShooterRPM(speed);
        System.out.println("***Shooter has started!***");
    }

    public void execute(){

    }

    public void end() {

    }

    public boolean isFinished(){
       return false; 
    }
}
