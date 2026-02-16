package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{

    //Set up motors
    private final SparkMax m_topShooter;
    private final SparkMax m_bottomShooter;
    private final SparkMax m_queuer;
    
    //Set up encoders
    private final RelativeEncoder topShooterEncoder;
    private final RelativeEncoder bottomShooterEncoder;
    private final RelativeEncoder queuerEncoder;

    //Set up config objects
    private final SparkMaxConfig topShooterConfig;
    private final SparkMaxConfig bottomShooterConfig;
    private final SparkMaxConfig queuerConfig;

    //PID Controllers for shooter and queuer
    private final SparkClosedLoopController topShooterController;
    private final SparkClosedLoopController bottomShooterController;
    private final SparkClosedLoopController queuerController;

    //PID variables for shooter & queuer
    //Starting values for now, change to meet needs
    private static final double shooter_kP = 0.0001;
    private static final double shooter_kI = 0;
    private static final double shooter_kD = 0.0001;
    
    private static final double queuer_kP = 0.0001;
    private static final double queuer_kI = 0;
    private static final double queuer_kD = 0.0001;
    
    // 1 / 5676 (Neo motor max speed)
    private static final double FF = 0.000175;

    public ShooterSubsystem(){
        //Initialize motors
        m_topShooter = new SparkMax(13, MotorType.kBrushless);
        m_bottomShooter = new SparkMax(10, MotorType.kBrushless);
        m_queuer = new SparkMax(9, MotorType.kBrushless);

        //Initialize encoders
        topShooterEncoder = m_topShooter.getEncoder();
        bottomShooterEncoder = m_bottomShooter.getEncoder();
        queuerEncoder = m_queuer.getEncoder();

        //Initialize PID controllers
        topShooterController = m_topShooter.getClosedLoopController();
        bottomShooterController = m_bottomShooter.getClosedLoopController();
        queuerController = m_queuer.getClosedLoopController();

        //Initialize configurations
        topShooterConfig = new SparkMaxConfig();
        bottomShooterConfig = new SparkMaxConfig();
        queuerConfig = new SparkMaxConfig();

        //Set up configurations, starting with idle mode and current limit
        topShooterConfig.idleMode(IdleMode.kCoast);
        topShooterConfig.smartCurrentLimit(50);
        //Now set up closed loop control configus
        topShooterConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(shooter_kP, shooter_kI, shooter_kD)
            .velocityFF(FF)
            .outputRange(-1, 1);
        
        //Do the same for the queuer config
        queuerConfig.idleMode(IdleMode.kCoast);
        queuerConfig.smartCurrentLimit(50);

        queuerConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(queuer_kP, queuer_kI, queuer_kD)
            .velocityFF(FF)
            .outputRange(-1, 1);
        
        //Apply the configurations to motors and set inverted to true if needed
        topShooterConfig.inverted(true);
        bottomShooterConfig.inverted(true);

        m_topShooter.configure(topShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_bottomShooter.configure(topShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        queuerConfig.inverted(true);
        m_queuer.configure(queuerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    //Once constructor finished, create helpful methods for simple commands or to return specific values

    //Set Shooter to specific RPM
    public void setShooterRPM(double rpm){
        topShooterController.setSetpoint(rpm, SparkMax.ControlType.kVelocity);
        bottomShooterController.setSetpoint(rpm, SparkMax.ControlType.kVelocity);
    }

    //Method to set the shooter motor speed based on percentage of speed, not rpm
    //Change number inside .set() to desired percent (range from -1 to 1) and change
    //setShooterRPM() to setShooterPower() in appropriate commands
    // public void setShooterPower(double rpm){
    //     m_shooter.set(1);
    // }
    
    //Return the current RPM of the shooter
    public double getTopShooterRPM(){
        return topShooterEncoder.getVelocity();
    }

    public double getBottomShooterRPM(){
        return bottomShooterEncoder.getVelocity();
    }
    //Return the current draw of the shooter motor
    
    // public double getShooterCurrent(){
    //     return m_shooter.getOutputCurrent();
    // }

    //Set queuer to specific RPM
    public void setQueuerRPM(double rpm){
        queuerController.setSetpoint(rpm, SparkMax.ControlType.kVelocity);
    }

    //Method to set the queuer motor speed based on percentage of speed, not rpm
    //Change number inside .set() to desired percent (range from -1 to 1) and change
    //setQueuerRPM() to setQueuerPower() in appropriate commands
    // public void setQueuerPower(double rpm){
    //     m_queuer.set(1);
    // }

    //Return the current RPM of the queuer
    public double getQueuerRPM(){
        return queuerEncoder.getVelocity();
    }

    //Return the current draw of the queuer motor
    public double getQueuerCurrent(){
        return m_queuer.getOutputCurrent();
    }

    //Stop queuer motor
    public void stopQueuer(){
        m_queuer.stopMotor();
    }

    //Stop all motors
    public void stop(){
        m_topShooter.stopMotor();
        m_bottomShooter.stopMotor();
        m_queuer.stopMotor();
    }

    //Periodic function is predefined and occurs periodically (50 times a second)
    public void periodic(){
        SmartDashboard.putNumber("Top Shooter RPM: ", getTopShooterRPM());
        SmartDashboard.putNumber("Bottom Shooter RPM", getBottomShooterRPM());
        //SmartDashboard.putNumber("Shooter Current (A): ", getShooterCurrent());
        SmartDashboard.putNumber("Queuer RPM: ", getQueuerRPM());
        SmartDashboard.putNumber("Queuer Current (A): ", getQueuerCurrent());
    }    

}