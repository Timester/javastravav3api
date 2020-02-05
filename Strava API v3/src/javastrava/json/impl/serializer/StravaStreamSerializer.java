package javastrava.json.impl.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javastrava.model.StravaMapPoint;
import javastrava.model.StravaStream;
import javastrava.model.reference.StravaStreamResolutionType;
import javastrava.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.model.reference.StravaStreamType;

/**
 * @author Dan Shannon
 *
 */
public class StravaStreamSerializer implements JsonSerializer<StravaStream>, JsonDeserializer<StravaStream> {

	/**
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public StravaStream deserialize(final JsonElement element, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		JsonObject json = null;
		try {
			json = (JsonObject) element;
		} catch (final ClassCastException e) { // happens if it's a primitive, or if it's crap data
			throw new JsonParseException(e);
		}

		// Get the type, as that will determine what the deserialization should do
		final StravaStreamType streamType = context.deserialize(json.get("type"), StravaStreamType.class);

		final JsonArray array = json.getAsJsonArray("data"); 
		List<StravaMapPoint> points = null;
		List<Float> data = null;
		List<Boolean> moving = null;
		if (streamType == StravaStreamType.MAPPOINT) {
			points = new ArrayList<>();
			for (final JsonElement arrayElement : array) {
				final Float latitude = arrayElement.getAsJsonArray().get(0).getAsFloat();
				final Float longitude = arrayElement.getAsJsonArray().get(1).getAsFloat();
				final StravaMapPoint point = new StravaMapPoint(latitude, longitude);
				points.add(point);
			}
		} else if (streamType == StravaStreamType.MOVING) {
			moving = new ArrayList<>();
			for (final JsonElement arrayElement : array) {
				final Boolean bool = arrayElement.getAsBoolean();
				moving.add(bool);
			}
		} else {
			data = new ArrayList<>();
			for (final JsonElement arrayElement : array) {
				if (arrayElement.isJsonNull()) {
					data.add(null);
				} else {
					final Float dataElement = arrayElement.getAsFloat();
					data.add(dataElement);
				}
			}
		}

		final StravaStream stream = new StravaStream();
		stream.setData(data);
		stream.setMapPoints(points);
		stream.setMoving(moving);
		final JsonElement originalSizeElement = json.get("original_size");
		if (originalSizeElement != null) {
			stream.setOriginalSize(originalSizeElement.getAsInt());
		}
		stream.setResolution(context.deserialize(json.get("resolution"),
				StravaStreamResolutionType.class));
		stream.setSeriesType(context.deserialize(json.get("series_type"),
				StravaStreamSeriesDownsamplingType.class));
		stream.setType(streamType);
		return stream;
	}

	/**
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(final StravaStream stream, final Type type, final JsonSerializationContext context) {
		final JsonObject element = new JsonObject();
		element.add("original_size", context.serialize(stream.getOriginalSize())); 
		element.add("resolution", context.serialize(stream.getResolution())); 
		element.add("series_type", context.serialize(stream.getSeriesType())); 
		element.add("type", context.serialize(stream.getType())); 
		final JsonArray dataArray = new JsonArray();
		element.add("data", dataArray); 
		if (stream.getType() == StravaStreamType.MAPPOINT) {
			for (final StravaMapPoint point : stream.getMapPoints()) {
				dataArray.add(context.serialize(point));
			}
		} else if (stream.getType() == StravaStreamType.MOVING) {
			for (final Boolean moving : stream.getMoving()) {
				dataArray.add(context.serialize(moving));
			}
		} else {
			for (final Number number : stream.getData()) {
				dataArray.add(context.serialize(number));
			}
		}
		return element;
	}

}
