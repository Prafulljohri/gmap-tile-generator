package gov.ca.maps.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ValueAsColoredShapeRenderer implements TileRenderer {
	// FIXME: specific to 38 degrees latitude...
	public static final double DEGREE_LAT_IN_FEET = 364160.86;
	public static final double DEGREE_LON_IN_FEET = 288163.56;
	public static final double LAT_WIDTH = 1000.0 / DEGREE_LAT_IN_FEET;
	public static final double LON_WIDTH = 1000.0 / DEGREE_LON_IN_FEET;
	private Color color;
	private HashMap<Object, Object> hints;

	public ValueAsColoredShapeRenderer(Color color) {
		this.color = color;
		hints = new HashMap<Object, Object>();
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	}

	public void renderData(BufferedImage image, double[] latLonBounds,
			double lat, double lon, String[] valuesAtLatLon) {
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.addRenderingHints(hints);
		double latOrigin = latLonBounds[0];
		double lonOrigin = latLonBounds[1];
		double latExtent = latLonBounds[2] - latLonBounds[0];
		double lonExtent = latLonBounds[3] - latLonBounds[1];
		double lx = (256 * (lat - latOrigin)) / latExtent;
		double ly = (256 * (lon - lonOrigin)) / lonExtent;
		double width = Math.max(3, LAT_WIDTH/ latExtent);
		double height = Math.max(3, LON_WIDTH / lonExtent);
		graphics.setColor(color);
		graphics.fill(new Ellipse2D.Double(lx, ly, width, height));
	}

}
