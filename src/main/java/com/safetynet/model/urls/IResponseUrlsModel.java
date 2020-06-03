package com.safetynet.model.urls;

import java.util.Set;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;

public interface IResponseUrlsModel {

	public Firestation responseFirestation(String idStation);

	public ChildAlert responseChildAlert(String address);

	public Fire responseFire(String address);

	public Flood responseFlood(String idStation);

	public PersonInfo responsePersonInfo(String firstName, String lastName);

	public Set<String> responsePhoneAlert(String idStation);
}
