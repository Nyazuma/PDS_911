package shs.simulator;

import org.apache.log4j.Level;

import shs.common.Tool;

public class SimulatorLaunch {

	public static void main(String[] args) {
		Tool.logger.getRootLogger().setLevel(Level.OFF);
		@SuppressWarnings("unused")
		Simulation simulation = new Simulation();
	}

}
