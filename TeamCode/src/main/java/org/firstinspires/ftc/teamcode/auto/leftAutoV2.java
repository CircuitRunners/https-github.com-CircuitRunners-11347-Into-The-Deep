package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

import org.firstinspires.ftc.teamcode.subsystems.ArmCorrected;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Diffy;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.SlidesPID;
import org.firstinspires.ftc.teamcode.support.Actions;
import org.firstinspires.ftc.teamcode.support.SleepCommand;



@Autonomous
public class leftAutoV2 extends OpMode{//TODO: Add mechanisms and test pathing

    private Follower follower;
    private Timer pathTimer;
    private int pathState;
//    private Claw claw;
//    private ArmCorrected arm;
//    private SlidesPID lift;
//    private Diffy diffy;

    // Define key poses
    private Pose startPosition = new Pose(10, 81, Math.toRadians(0));
    private Pose preloadPos = new Pose(34, 81, Math.toRadians(0));
    private Pose sample1Pos = new Pose(27, 121, Math.toRadians(180));
    //private Point sample1CP = new Point(15, 105);
    private Pose placePos = new Pose(17, 126.5, Math.toRadians(135));//14.5, 128, -45
    private Pose sample2Pos = new Pose(28, 131.5, Math.toRadians(180));
    private Pose sample3Pos = new Pose(45.5, 125, Math.toRadians(90));
    //private Point sample3CP = new Point(46, 117);
    private Pose parkPos = new Pose(63, 98, Math.toRadians(270));
    private Point parkCP = new Point(67.5, 132);




    private PathChain preload, sample1Grab, sample1Place, sample2Grab, sample2Place, sample3Grab, sample3Place, park;
    public void buildPaths() {
        preload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPosition), new Point(preloadPos)))
                .setConstantHeadingInterpolation(preloadPos.getHeading())
                .build();
        sample1Grab = follower.pathBuilder()
                .addPath(new BezierLine(new Point(preloadPos), new Point(sample1Pos)))
                .setConstantHeadingInterpolation(sample1Pos.getHeading())
                .build();
        sample1Place = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample1Pos), new Point(placePos)))
                .setLinearHeadingInterpolation(sample1Pos.getHeading(), placePos.getHeading())
                .build();
        sample2Grab = follower.pathBuilder()
                .addPath(new BezierLine(new Point(placePos), new Point(sample2Pos)))
                .setLinearHeadingInterpolation(placePos.getHeading(), sample2Pos.getHeading())
                .build();
        sample2Place = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample2Pos), new Point(placePos)))
                .setLinearHeadingInterpolation(sample2Pos.getHeading(), placePos.getHeading())
                .build();
        sample3Grab = follower.pathBuilder()
                .addPath(new BezierLine(new Point(placePos), new Point(sample3Pos)))
                .setLinearHeadingInterpolation(placePos.getHeading(), sample3Pos.getHeading())
                .build();
        sample3Place = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample3Pos), new Point(placePos)))
                .setLinearHeadingInterpolation(sample3Pos.getHeading(), placePos.getHeading())
                .build();
        park = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(preloadPos), parkCP, new Point(parkPos)))
                .setLinearHeadingInterpolation(preloadPos.getHeading(), parkPos.getHeading())
                .build();

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case -2:
                //Actions.runBlocking(arm.toTopBar);
                follower.followPath(preload, true);
                setPathState(-1);
                break;
            case -1:
                if (!follower.isBusy()) {
                    //Need to place preload
//                    Actions.runBlocking(diffy.centerDiffy);
//                    Actions.runBlocking(arm.toTopBar);
//                    Actions.runBlocking(new SleepCommand(1));
//                    Actions.runBlocking(arm.toRestPos);
                    setPathState(0);
                }
                break;
            case 0:
                if (!follower.isBusy()) {
                    //Actions.runBlocking(arm.toRestPos);
                    //Actions.runBlocking(new SleepCommand(1));
                    follower.followPath(sample1Grab,true);
//                    Actions.runBlocking(new SleepCommand(1));
//                    Actions.runBlocking(arm.toGrabSample);
                    setPathState(2);
                }
                break;
            case 1:
                if (!follower.isBusy()) {
                    //Actions.runBlocking(arm.toGrabSample);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    //grab sample
                    //Actions.runBlocking(new SleepCommand(1));
                    follower.followPath(sample1Place, true);
                    //Actions.runBlocking(arm.toGrabPos);

                    //Actions.runBlocking(arm.toBasketPos);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    //place sample in bucket
                    //Actions.runBlocking(lift.scoring);
                    //Actions.runBlocking(new SleepCommand(8));
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
//                    Actions.runBlocking(new SleepCommand(3));
//                    Actions.runBlocking(lift.rest);
//                    Actions.runBlocking(new SleepCommand(1));
//                    Actions.runBlocking(claw.open);
//                    Actions.runBlocking(arm.toRestPos);
                    //move slides down
                    follower.followPath(sample2Grab, true);
                    //Actions.runBlocking(arm.toGrabSample);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    //grab sample

                    //Actions.runBlocking(new SleepCommand(1));
                    //Actions.runBlocking(claw.close);
                    //Actions.runBlocking(arm.toRestPos);
                    follower.followPath(sample2Place, true);
                    //Actions.runBlocking(arm.toBasketPos);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    //place sample in bucket
                    //Actions.runBlocking(lift.scoring);
                    //Actions.runBlocking(new SleepCommand(8));
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    //place sample in bucket
//                    Actions.runBlocking(new SleepCommand(3));
//                    Actions.runBlocking(lift.rest);
                    //move slides up
                    //follower.followPath(sample3Grab);
                    setPathState(9);//Skipping sample 3 for now
                }
                break;
            case 79:
                if (!follower.isBusy()) {
                    //grab sample
//                    Actions.runBlocking(arm.toGrabSample);
//                    Actions.runBlocking(claw.close);
                    follower.followPath(sample3Place, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    //place sample
//                    Actions.runBlocking(arm.toBasketPos);
//                    Actions.runBlocking(new SleepCommand(1));
//                    Actions.runBlocking(claw.open);
                    follower.followPath(park, true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()) {
//                    Actions.runBlocking(arm.toRestPos);
//                    //move slides down
//                    Actions.runBlocking(claw.close);
                    follower.followPath(park, true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()) {
                    //touch bar
//                    Actions.runBlocking(arm.toBasketPos);
                    //setPathState(11);
                }
                break;
            default:
                requestOpModeStop();
                break;

        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
//        arm.update();
//        lift.update();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", Math.toRadians(follower.getPose().getHeading()));
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPosition);
//        claw = new Claw(hardwareMap);
//        lift = new SlidesPID(hardwareMap);
//        arm = new ArmCorrected(hardwareMap);
//        diffy = new Diffy(hardwareMap);
        buildPaths();
        telemetry.addLine("Initialized");
        //telemetry.addData("test", lift.getLiftPosition());
        telemetry.update();
    }

    @Override
    public void start() {
        pathTimer.resetTimer();
        //lift.setLiftPower(1);
        //lift.setLiftTarget(SlidesPID.SlidePositions.STAGE_2.getPosition());
        setPathState(-2);
    }




}
