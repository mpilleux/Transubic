package cl.uchile.transubic.simpleStep;

import com.google.maps.model.DirectionsStep;

public class SimpleStep {

	public String htmlInstructions;

	public String humanReadableDuration;

	public SimpleStep(DirectionsStep directionsStep) {
		super();
		this.htmlInstructions = directionsStep.htmlInstructions.replace(",", "&#44;");
		this.humanReadableDuration = directionsStep.duration.humanReadable;
	}

	public SimpleStep() {

	}

}
