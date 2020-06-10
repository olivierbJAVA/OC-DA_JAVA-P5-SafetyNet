package com.safetynet.util;

/**
 * Interface to implement for managing the data initialization of the lists.
 */
public interface IInitializeLists {

	/**
	 * Get the initial data.
	 */
	public void getInitialData();

	/**
	 * Initialize the data list of persons.
	 */
	public void initializeDataPersons();

	/**
	 * Initialize the data list of firestation mappings.
	 */
	public void initializeDataFirestationMappings();

	/**
	 * Initialize the data list of medical records.
	 */
	public void initializeDataMedicalRecords();
}
