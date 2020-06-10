package com.safetynet.service.urls;

import java.util.Set;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;

/**
 * Interface to implement for managing the services for the responses to the
 * URLS requests.
 */
public interface IResponseUrlsService {

	/**
	 * Return the response to the Firestation URL request.
	 * 
	 * @param idStation The requested id
	 * 
	 * @return The Firestation response corresponding to the id requested
	 */
	public Firestation responseFirestation(String idStation);

	/**
	 * Return the response to the ChildAlert URL request.
	 * 
	 * @param address The requested address
	 * 
	 * @return The ChildAlert response corresponding to the address requested
	 */
	public ChildAlert responseChildAlert(String address);

	/**
	 * Return the response to the PhoneAlert URL request.
	 * 
	 * @param idStation The requested id
	 * 
	 * @return The PhoneAlert response corresponding to the id requested
	 */
	public Set<String> responsePhoneAlert(String idStation);

	/**
	 * Return the response to the Fire URL request.
	 * 
	 * @param address The requested address
	 * 
	 * @return The Fire response corresponding to the address requested
	 */
	public Fire responseFire(String address);

	/**
	 * Return the response to the Flood URL request.
	 * 
	 * @param idsStation An array containing the requested ids
	 * 
	 * @return The Flood response corresponding to the ids requested
	 */
	public Flood responseFlood(String[] idsStation);

	/**
	 * Return the response to the PersonInfo URL request.
	 * 
	 * @param firstName The firstName address
	 * 
	 * @param lastName  The lastName address
	 * 
	 * @return The PersonInfo response corresponding to the firstName and lastName
	 *         requested
	 */
	public PersonInfo responsePersonInfo(String firstName, String lastName);

	/**
	 * Return the response to the CommunityEmail URL request.
	 * 
	 * @param city The requested city
	 * 
	 * @return The CommunityEmail response corresponding to the city requested
	 */
	public Set<String> responseCommunityEmail(String city);
}
