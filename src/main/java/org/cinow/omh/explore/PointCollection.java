package org.cinow.omh.explore;

import java.util.List;

public class PointCollection {
	private PointType pointType;
	private List<Point> points;
	
	public PointType getPointType() {
		return pointType;
	}
	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
