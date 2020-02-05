package javastrava.model.reference;

import javastrava.config.Messages;
import javastrava.config.StravaConfig;
import javastrava.json.impl.serializer.ActivityTypeSerializer;

/**
 * <p>
 * These are all the available Activity types:
 * </p>
 * 
 * <ul>
 * <li>Ride</li>
 * <li>Run</li>
 * <li>Swim</li>
 * <li>Hike</li>
 * <li>Walk</li>
 * <li>AlpineSki</li>
 * <li>BackcountrySki</li>
 * <li>Caneoing</li>
 * <li>Crossfit</li>
 * <li>EBikeRide</li>
 * <li>Elliptical</li>
 * <li>IceSkate</li>
 * <li>InlineSkate</li>
 * <li>Kayaking</li>
 * <li>Kitesurf</li>
 * <li>NordicSki</li>
 * <li>RockClimbing</li>
 * <li>RollerSki</li>
 * <li>Rowing</li>
 * <li>Snowboard</li>
 * <li>Snowshoe</li>
 * <li>StairStepper</li>
 * <li>StandUpPaddling</li>
 * <li>Surfing</li>
 * <li>VirtualRide
 * <li>WeightTraining</li>
 * <li>Windsurf</li>
 * <li>Workout</li>
 * <li>Yoga</li>
 * </ul>
 * 
 * <p>
 * Activities that don’t use real GPS should utilize the {@link #VIRTUAL_RIDE}
 * type. Electronically assisted rides should use the {@link #EBIKE_RIDE} type.
 * The @link {@link #WORKOUT} type is recommended for miscellaneous activities.
 * </p>
 * 
 * <p>
 * Type is detected from file upload overrides, uses athlete's default type if
 * not specified
 * </p>
 *
 * <p>
 * NOTE: The crosscountryskiing type has been removed. Please use
 * {@link StravaActivityType#NORDIC_SKI} instead.
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public enum StravaActivityType implements StravaReferenceType<String> {
	/**
	 * Bike ride
	 */
	RIDE(StravaConfig.string("StravaActivityType.ride"), Messages.string("StravaActivityType.ride.description")),
	/**
	 * Run
	 */
	RUN(StravaConfig.string("StravaActivityType.run"), Messages.string("StravaActivityType.run.description")),
	/**
	 * Swim
	 */
	SWIM(StravaConfig.string("StravaActivityType.swim"), Messages.string("StravaActivityType.swim.description")),
	/**
	 * Hike
	 */
	HIKE(StravaConfig.string("StravaActivityType.hike"), Messages.string("StravaActivityType.hike.description")),
	/**
	 * Walk
	 */
	WALK(StravaConfig.string("StravaActivityType.walk"), Messages.string("StravaActivityType.walk.description")),
	/**
	 * Alpine skiing
	 */
	ALPINE_SKI(StravaConfig.string("StravaActivityType.alpineski"), 
			Messages.string("StravaActivityType.alpineski.description")), 
	/**
	 * Back-country skiing (off piste)
	 */
	BACKCOUNTRY_SKI(StravaConfig.string("StravaActivityType.backcountryski"), 
			Messages.string("StravaActivityType.backcountryski.description")), 
	/**
	 * Canoeing
	 */
	CANOEING(StravaConfig.string("StravaActivityType.canoeing"), 
			Messages.string("StravaActivityType.canoeing.description")), 
	/**
	 * Crossfit
	 */
	CROSSFIT(StravaConfig.string("StravaActivityType.crossfit"), 
			Messages.string("StravaActivityType.crossfit.description")), 
	/**
	 * E-Bike Ride
	 */
	EBIKE_RIDE(StravaConfig.string("StravaActivityType.ebikeride"), 
			Messages.string("StravaActivityType.ebikeride.description")), 
	/**
	 * Elliptical Trainer
	 */
	ELLIPTICAL(StravaConfig.string("StravaActivityType.elliptical"), 
			Messages.string("StravaActivityType.elliptical.description")), 
	/**
	 * Ice skating
	 */
	ICE_SKATE(StravaConfig.string("StravaActivityType.iceskate"), 
			Messages.string("StravaActivityType.iceskate.description")), 
	/**
	 * Inline skating (rollerblading)
	 */
	INLINE_SKATE(StravaConfig.string("StravaActivityType.inlineskate"), 
			Messages.string("StravaActivityType.inlineskate.description")), 
	/**
	 * Kayaking
	 */
	KAYAKING(StravaConfig.string("StravaActivityType.kayaking"), 
			Messages.string("StravaActivityType.kayaking.description")), 
	/**
	 * Kite surfing
	 */
	KITESURF(StravaConfig.string("StravaActivityType.kitesurf"), 
			Messages.string("StravaActivityType.kitesurf.description")), 
	/**
	 * Nordic skiing (telemark)
	 */
	NORDIC_SKI(StravaConfig.string("StravaActivityType.nordicski"), 
			Messages.string("StravaActivityType.nordicski.description")), 
	/**
	 * Rock climbing
	 */
	ROCK_CLIMBING(StravaConfig.string("StravaActivityType.rockclimbing"), 
			Messages.string("StravaActivityType.rockclimbing.description")), 
	/**
	 * Rollerskiing
	 */
	ROLLERSKI(StravaConfig.string("StravaActivityType.rollerski"), 
			Messages.string("StravaActivityType.rollerski.description")), 
	/**
	 * Rowing
	 */
	ROWING(StravaConfig.string("StravaActivityType.rowing"), Messages.string("StravaActivityType.rowing.description")),
	/**
	 * Snowboarding
	 */
	SNOWBOARD(StravaConfig.string("StravaActivityType.snowboard"), 
			Messages.string("StravaActivityType.snowboard.description")), 
	/**
	 * Snowshoeing
	 */
	SNOWSHOE(StravaConfig.string("StravaActivityType.snowshoe"), 
			Messages.string("StravaActivityType.snowshoe.description")), 
	/**
	 * Stair stepper
	 */
	STAIR_STEPPER(StravaConfig.string("StravaActivityType.stairstepper"), 
			Messages.string("StravaActivityType.stairstepper.description")), 
	/**
	 * Stand-up Paddling
	 */
	STAND_UP_PADDLING(StravaConfig.string("StravaActivityType.standuppaddling"), 
			Messages.string("StravaActivityType.standuppaddling.description")), 
	/**
	 * Surfing
	 */
	SURFING(StravaConfig.string("StravaActivityType.surfing"), 
			Messages.string("StravaActivityType.surfing.description")), 
	/**
	 * Virtual ride
	 */
	VIRTUAL_RIDE(StravaConfig.string("StravaActivityType.virtualride"), 
			Messages.string("StravaActivityType.virtualride.description")), 
	/**
	 * Weight training
	 */
	WEIGHT_TRAINING(StravaConfig.string("StravaActivityType.weighttraining"), 
			Messages.string("StravaActivityType.weighttraining.description")), 
	/**
	 * Windsurfing
	 */
	WINDSURF(StravaConfig.string("StravaActivityType.windsurf"), 
			Messages.string("StravaActivityType.windsurf.description")), 
	/**
	 * Workout
	 */
	WORKOUT(StravaConfig.string("StravaActivityType.workout"), 
			Messages.string("StravaActivityType.workout.description")), 
	/**
	 * Yoga
	 */
	YOGA(StravaConfig.string("StravaActivityType.yoga"), Messages.string("StravaActivityType.yoga.description")),
	/**
	 * <p>
	 * Should never occur but may if Strava API behaviour has changed
	 * </p>
	 */
	UNKNOWN(StravaConfig.string("Common.unknown"), Messages.string("Common.unknown.description"));

	/**
	 * @param id
	 *            The string representation of the activity type as returned by
	 *            the Strava API
	 * @return The {@link StravaActivityType} with the matching id, or
	 *         {@link StravaActivityType#UNKNOWN} if there is no match
	 * @see ActivityTypeSerializer#deserialize(com.google.gson.JsonElement,
	 *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public static StravaActivityType create(final String id) {
		for (final StravaActivityType type : StravaActivityType.values()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return type;
			}
		}
		return UNKNOWN;
	}

	/**
	 * Identifier
	 */
	private String id;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Private constructor used by declarations above
	 * 
	 * @param id
	 *            Identifier (used in JSON)
	 * @param description
	 *            Description
	 */
    StravaActivityType(final String id, final String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the id
	 * @see ActivityTypeSerializer#serialize(StravaActivityType,
	 *      java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * @return The id
	 */
	@Override
	public String getValue() {
		return this.id;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.id;
	}

}
