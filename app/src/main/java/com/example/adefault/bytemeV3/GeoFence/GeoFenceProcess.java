package com.example.adefault.bytemeV3.GeoFence;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.*;

public class GeoFenceProcess {

    private static final String TAG = "GeoFenceProcess";

    private List<GeoFenceResponse> geoFenceResponses;
    private LatLng currentLocation;

    public boolean main(List<GeoFenceResponse> geoFenceResponses, LatLng currentLocation){
        this.geoFenceResponses = geoFenceResponses;
        this.currentLocation = currentLocation;

        for(int i = 0; i < geoFenceResponses.size(); i++){
            Points[] points = new Points[geoFenceResponses.get(i).getPoints().size()];
            for(int x = 0; x < points.length; x++){
                points[x] = new Points();
                points[x].setLat(geoFenceResponses.get(i).getPoints().get(x).getLat());
                points[x].setLng(geoFenceResponses.get(i).getPoints().get(x).getLng());
            }
            Polygon polygon = new Polygon(points);
            if(checkInside(polygon, currentLocation.latitude, currentLocation.longitude)){
                Log.d(TAG, "Inside");
                return true;
            }
        }


        return false;
    }

    public boolean checkInside(Polygon polygon, double x, double y){
        List<Line> lines = calculateLines(polygon);
        List<Line> intersectionLines = filterIntersectingLines(lines, y);
        List<Points> intersectionPoints = calculateIntersectionPoints(intersectionLines, y);
        sortPointsByX(intersectionPoints);
        return calculateInside(intersectionPoints, x);
    }

    public List<Line> calculateLines(Polygon polygon) {
        List<Line> results = new LinkedList<Line>();

        // get the polygon points
        Points[] points = polygon.getPoints();

        // form lines by connecting the points
        Points lastPoint = null;
        for (Points point : points) {
            if (lastPoint != null) {
                results.add(new Line(lastPoint, point));
            }
            lastPoint = point;
        }

        // close the polygon by connecting the last point
        // to the first point
        results.add(new Line(lastPoint, points[0]));

        return results;
    }

    public List<Line> filterIntersectingLines(List<Line> lines, double y) {
        List<Line> results = new LinkedList<Line>();
        for (Line line : lines) {
            if (isLineIntersectingAtY(line, y)) {
                results.add(line);
            }
        }
        return results;
    }

    public boolean isLineIntersectingAtY(Line line, double y) {
        double minY = Math.min(
                line.getFrom().getLng(), line.getTo().getLng()
        );
        double maxY = Math.max(
                line.getFrom().getLng(), line.getTo().getLng()
        );
        return y > minY && y <= maxY;
    }

    List<Points> calculateIntersectionPoints(
            List<Line> lines, double y) {
        List<Points> results = new LinkedList<Points>();
        for (Line line : lines) {
            double x = calculateLineXAtY(line, y);
            Points p = new Points();
            p.setLat(x);
            p.setLng(y);
            results.add(p);
        }
        return results;
    }

    double calculateLineXAtY(Line line, double y) {
        Points from = line.getFrom();
        double slope = calculateSlope(line);
        return from.getLat() + (y - from.getLng()) / slope;
    }

    double calculateSlope(Line line) {
        Points from = line.getFrom();
        Points to = line.getTo();
        return (to.getLng() - from.getLng()) / (to.getLat() - from.getLat());
    }

    void sortPointsByX(List<Points> points) {
        Collections.sort(points, new Comparator<Points>() {
            public int compare(Points p1, Points p2) {
                return Double.compare(p1.getLat(), p2.getLat());
            }
        });
    }

    boolean calculateInside(List<Points> sortedPoints, double x) {
        boolean inside = false;
        for (Points point : sortedPoints) {
            if (x < point.getLat()) {
                break;
            }
            inside = !inside;
        }
        return inside;
    }
}
