package com.safetynet.service.urls;

import java.util.Set;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;

public interface IResponseUrlsService {

	public Firestation responseFirestation(String idStation);

	public ChildAlert responseChildAlert(String address);

	public Fire responseFire(String address);

	public Flood responseFlood(String[] idsStation);

	public PersonInfo responsePersonInfo(String firstName, String lastName);

	public Set<String> responsePhoneAlert(String idStation);

	public Set<String> responseCommunityEmail(String city);
}
