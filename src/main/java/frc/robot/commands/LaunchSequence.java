package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class LaunchSequence extends SequentialCommandGroup {
    public LaunchSequence(ShooterSubsystem fullLaunch){
        addCommands(new SpinUp(fullLaunch, 500).withTimeout(1.5), 
            new Queuer(fullLaunch,500));
    }

}
