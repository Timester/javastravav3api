package javastrava.util;

import java.util.ArrayList;
import java.util.List;

import javastrava.config.Messages;
import javastrava.config.StravaConfig;

/**
 * <p>
 * Utilities for handling Strava paging
 * </p>
 * @author Dan Shannon
 *
 */
public class PagingUtils {

	/**
	 * <p>
	 * Utility method - give it any paging instruction and it will return a list of paging instructions that will work with the Strava API (i.e. that conform to
	 * maximum page sizes etc.)
	 * </p>
	 *
	 * @param inputPaging
	 *            The paging instruction to be converted
	 * @return List of Strava paging instructions that can be given to the Strava engine
	 */
	public static List<Paging> convertToStravaPaging(final Paging inputPaging) {
		PagingUtils.validatePagingArguments(inputPaging);
		final List<Paging> stravaPaging = new ArrayList<>();
		if (inputPaging == null) {
			stravaPaging.add(new Paging(1, StravaConfig.DEFAULT_PAGE_SIZE));
			return stravaPaging;
		}

		if (inputPaging.getPage() == 0) {
			inputPaging.setPage(1);
		}
		if (inputPaging.getPageSize() == 0) {
			inputPaging.setPageSize(StravaConfig.DEFAULT_PAGE_SIZE);
		}

		// If it's already valid for Strava purposes, just use that
		if (inputPaging.getPageSize() <= StravaConfig.MAX_PAGE_SIZE) {
			stravaPaging.add(inputPaging);
			return stravaPaging;
		}

		// Calculate the first and last elements to be returned
		final int lastElement = (inputPaging.getPage() * inputPaging.getPageSize()) - inputPaging.getIgnoreLastN();
		final int firstElement = ((inputPaging.getPage() - 1) * inputPaging.getPageSize()) + inputPaging.getIgnoreFirstN() + 1;

		// If the last element fits in one page, return one page
		if (lastElement <= StravaConfig.MAX_PAGE_SIZE) {
			stravaPaging.add(new Paging(1, lastElement, inputPaging.getIgnoreFirstN(), 0));
			return stravaPaging;
		}

		// Otherwise, return a series of pages
		int currentPage = 0;
		for (int i = 1; i <= lastElement; i = i + StravaConfig.MAX_PAGE_SIZE) {
			currentPage++;
			if (((currentPage * StravaConfig.MAX_PAGE_SIZE)) >= firstElement) {
				final int ignoreLastN = Math.max(0, (currentPage * StravaConfig.MAX_PAGE_SIZE) - lastElement);
				final int ignoreFirstN = Math.max(0, firstElement - ((currentPage - 1) * StravaConfig.MAX_PAGE_SIZE) - 1);
				stravaPaging.add(new Paging(currentPage, StravaConfig.MAX_PAGE_SIZE, ignoreFirstN, ignoreLastN));
			}
		}
		return stravaPaging;

	}

	/**
	 * <p>
	 * Removes the first N items from a list
	 * </p>
	 *
	 * @param list
	 *            List of items
	 * @param ignoreFirstN
	 *            Number of items to remove
	 * @param <T>
	 *            The class of object in the list
	 * @return The resulting list
	 */
	public static <T> List<T> ignoreFirstN(final List<T> list, final int ignoreFirstN) {
		if (ignoreFirstN < 0) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.cannotRemove") + ignoreFirstN + Messages.string("PagingUtils.itemsFromAList"));
		}
		if (list == null) {
			return null;
		}
		if (ignoreFirstN == 0) {
			return list;
		}
		if (ignoreFirstN >= list.size()) {
			return new ArrayList<>();
		}
		// return list.subList(ignoreFirstN, list.size() - 1);
		final ArrayList<T> returnList = new ArrayList<>();
		for (int i = ignoreFirstN; i < list.size(); i++) {
			returnList.add(list.get(i));
		}
		return returnList;
	}

	/**
	 * <p>
	 * Removes the last ignoreLastN items from the list
	 * </p>
	 *
	 * @param list
	 *            List of items
	 * @param ignoreLastN
	 *            Number of items to remove
	 * @param <T>
	 *            The class of the objects contained in the list
	 * @return The resulting list
	 */
	public static <T> List<T> ignoreLastN(final List<T> list, final int ignoreLastN) {
		if (ignoreLastN < 0) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.cannotRemove") + ignoreLastN + Messages.string("PagingUtils.itemsFromAList"));
		}
		if (list == null) {
			return null;
		}
		if (ignoreLastN == 0) {
			return list;
		}
		if (ignoreLastN >= list.size()) {
			return new ArrayList<>();
		}
		return list.subList(0, list.size() - ignoreLastN);
	}

	/**
	 * <p>
	 * Throw an IllegalArgumentException if the page or perPage parameters are set but are invalid
	 * </p>
	 *
	 * @param pagingInstruction
	 *            The page to be returned
	 */
	public static void validatePagingArguments(final Paging pagingInstruction) {
		if (pagingInstruction == null) {
			return;
		}
		if (pagingInstruction.getPage() < 0) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.pageArgumentTooLow")); 
		}
		if (pagingInstruction.getPageSize() < 0) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.perPageArgumentTooLow")); 
		}
		if ((pagingInstruction.getIgnoreLastN() > 0) && (pagingInstruction.getIgnoreLastN() > pagingInstruction.getPageSize())) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.IgnoreTooHigh")); 
		}
		if ((pagingInstruction.getIgnoreFirstN() > 0) && (pagingInstruction.getIgnoreFirstN() > pagingInstruction.getPageSize())) {
			throw new IllegalArgumentException(Messages.string("PagingUtils.IgnoreTooHigh")); 
		}
	}

}
