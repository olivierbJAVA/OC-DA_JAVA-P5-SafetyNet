package com.safetynet.model.urls;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;

public interface IResponseUrlsModel {

	public Firestation responseFirestation(int stationNumber);

	public ChildAlert responseChildAlert(String address);

	public Fire responseFire(String address);

	public Flood responseFlood(int stationNumber);

	public PersonInfo responsePersonInfo(String firstName, String lastName);
}
