package javastrava.api;

import javastrava.api.async.StravaAPICallback;
import javastrava.model.StravaAthlete;
import javastrava.model.StravaAthleteZones;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaStatistics;
import javastrava.model.reference.StravaGender;
import javastrava.service.AthleteService;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * <p>
 * API definitions for implementation of {@link AthleteService}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface AthleteAPI {
	/**
	 * @see javastrava.service.AthleteService#getAthlete(java.lang.Integer)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @return Details of the athlete, will be somewhat anonymised if the athlete is private
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 */
	@GET("/athletes/{id}")
    StravaAthlete getAthlete(@Path("id") final Integer athleteId) throws NotFoundException;

	/**
	 * @see javastrava.service.AthleteService#getAthlete(java.lang.Integer)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 */
	@GET("/athletes/{id}")
    void getAthlete(@Path("id") final Integer athleteId, final StravaAPICallback<StravaAthlete> callback) throws NotFoundException;

	/**
	 * @see javastrava.service.AthleteService#getAthlete(java.lang.Integer)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @return Details of the athlete, will be somewhat anonymised if the athlete is private
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 */
	@GET("/athletes/{id}")
    Response getAthleteRaw(@Path("id") final Integer athleteId) throws NotFoundException;

	/**
	 * @see javastrava.service.AthleteService#getAuthenticatedAthlete()
	 *
	 * @return Full details of the authenticated athlete
	 */
	@GET("/athlete")
    StravaAthlete getAuthenticatedAthlete();

	/**
	 * @see javastrava.service.AthleteService#getAuthenticatedAthlete()
	 *
	 * @param callback
	 *            Callback to be executed once the call is completed
	 */
	@GET("/athlete")
    void getAuthenticatedAthlete(final StravaAPICallback<StravaAthlete> callback);

	/**
	 * @see javastrava.service.AthleteService#getAuthenticatedAthlete()
	 *
	 * @return Full details of the authenticated athlete
	 */
	@GET("/athlete")
    Response getAuthenticatedAthleteRaw();

	/**
	 * <p>
	 * Returns the current athlete’s heart rate and power zones. The min for Zone 1 is always 0 and the max for Zone 5 is always -1.
	 * </p>
	 *
	 * <p>
	 * Premium members who have set a functional threshold power (ftp) will see their power zones. Power zones are a Premium-only feature. Free members will not see the power part of this endpoint.
	 * </p>
	 *
	 * @return The athlete zones object
	 */
	@GET("/athlete/zones")
    StravaAthleteZones getAuthenticatedAthleteZones();

	/**
	 * <p>
	 * Returns the current athlete’s heart rate and power zones. The min for Zone 1 is always 0 and the max for Zone 5 is always -1.
	 * </p>
	 *
	 * <p>
	 * Premium members who have set a functional threshold power (ftp) will see their power zones. Power zones are a Premium-only feature. Free members will not see the power part of this endpoint.
	 * </p>
	 *
	 * @param callback
	 *            The callback used to return the asynchronous result
	 */
	@GET("/athlete/zones")
    void getAuthenticatedAthleteZones(StravaAPICallback<StravaAthleteZones> callback);

	/**
	 * @param athleteId
	 *            Athlete identifier
	 * @return Statistics summary for the identified athlete
	 * @throws NotFoundException
	 *             If the identified athlete doesn't exist
	 */
	@GET("/athletes/{id}/stats")
    StravaStatistics getStatistics(@Path("id") final Integer athleteId) throws NotFoundException;

	/**
	 * @see javastrava.service.AthleteService#statistics(Integer)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws NotFoundException
	 *             If the identified athlete doesn't exist
	 */
	@GET("/athletes/{id}/stats")
    void getStatistics(@Path("id") final Integer athleteId, StravaAPICallback<StravaStatistics> callback) throws NotFoundException;

	/**
	 * @param athleteId
	 *            Athlete identifier
	 * @return Statistics summary for the identified athlete
	 * @throws NotFoundException
	 *             If the identified athlete doesn't exist
	 */
	@GET("/athletes/{id}/stats")
    Response getStatisticsRaw(@Path("id") final Integer athleteId) throws NotFoundException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteFriends(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who the identified athlete is following
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/friends")
    StravaAthlete[] listAthleteFriends(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteFriends(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/friends")
    void listAthleteFriends(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaAthlete[]> callback)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteFriends(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who the identified athlete is following
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/friends")
    Response listAthleteFriendsRaw(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteKOMs(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of segment efforts which represent the athlete's KOM/QOM's
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/koms")
    StravaSegmentEffort[] listAthleteKOMs(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteKOMs(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/koms")
    void listAthleteKOMs(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage,
                         final StravaAPICallback<StravaSegmentEffort[]> callback) throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthleteKOMs(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of segment efforts which represent the athlete's KOM/QOM's
	 * @throws NotFoundException
	 *             If the athlete doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/koms")
    Response listAthleteKOMsRaw(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage) throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthletesBothFollowing(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who both the identified athlete and the authenticated athlete are following
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/both-following")
    StravaAthlete[] listAthletesBothFollowing(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthletesBothFollowing(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/both-following")
    void listAthletesBothFollowing(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage,
                                   final StravaAPICallback<StravaAthlete[]> callback) throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAthletesBothFollowing(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId
	 *            Athlete identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who both the identified athlete and the authenticated athlete are following
	 * @throws NotFoundException
	 *             If the athlete with the given id doesn't exist
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/both-following")
    Response listAthletesBothFollowingRaw(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAuthenticatedAthleteFriends(javastrava.util.Paging)
	 *
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who the authenticated athlete is following
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athlete/friends")
    StravaAthlete[] listAuthenticatedAthleteFriends(@Query("page") final Integer page, @Query("per_page") final Integer perPage) throws BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAuthenticatedAthleteFriends(javastrava.util.Paging)
	 *
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @param callback
	 *            Callback to be executed once the call is completed
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athlete/friends")
    void listAuthenticatedAthleteFriends(@Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaAthlete[]> callback)
			throws BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#listAuthenticatedAthleteFriends(javastrava.util.Paging)
	 *
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who the authenticated athlete is following
	 * @throws BadRequestException
	 *             If the paging instructions are invalid
	 */
	@GET("/athlete/friends")
    Response listAuthenticatedAthleteFriendsRaw(@Query("page") final Integer page, @Query("per_page") final Integer perPage) throws BadRequestException;

	/**
	 * @see javastrava.service.AthleteService#updateAuthenticatedAthlete(String, String, String, StravaGender, Float)
	 *
	 * @param city
	 *            City the athlete is from
	 * @param state
	 *            State/county/territory/canton/departement/whatever the athlete is from
	 * @param country
	 *            Country the athlete is from
	 * @param sex
	 *            Gender of the athlete
	 * @param weight
	 *            Weight in kilograms
	 * @return Athlete details as updated on Strava
	 */
	@PUT("/athlete")
    StravaAthlete updateAuthenticatedAthlete(@Query("city") final String city, @Query("state") final String state, @Query("country") final String country, @Query("sex") final StravaGender sex,
                                             @Query("weight") final Float weight);

	/**
	 * @see javastrava.service.AthleteService#updateAuthenticatedAthlete(String, String, String, StravaGender, Float)
	 *
	 * @param city
	 *            City the athlete is from
	 * @param state
	 *            State/county/territory/canton/departement/whatever the athlete is from
	 * @param country
	 *            Country the athlete is from
	 * @param sex
	 *            Gender of the athlete
	 * @param weight
	 *            Weight in kilograms
	 * @param callback
	 *            Callback to be executed once the call is completed
	 */
	@PUT("/athlete")
    void updateAuthenticatedAthlete(@Query("city") final String city, @Query("state") final String state, @Query("country") final String country, @Query("sex") final StravaGender sex,
                                    @Query("weight") final Float weight, final StravaAPICallback<StravaAthlete> callback);
}
