package com.bloodglucose.api.port.in;

import java.util.Date;
import java.util.List;

import com.bloodglucose.api.port.out.GlucosePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodglucose.api.core.dto.GlucoseRecord;

@Service
public class GlucosePortIn {

	@Autowired
	private GlucosePortOut portOut;

	public void saveGlucose(GlucoseRecord glucose) throws Exception {
		portOut.saveGlucose(glucose);
	}

	public List<GlucoseRecord> getGlucoseList() {
		return portOut.getGlucoseList();
	}

	public List<GlucoseRecord> getGlucoseListByMeal(String meal) {
		return portOut.getGlucoseListByMeal(meal);
	}

	public List<GlucoseRecord> getGlucoseListByDate(Date date) {
		return portOut.getGlucoseListByDate(date);
	}

	public List<GlucoseRecord> getGlucoseListByDateBetween(Date startDate, Date endDate) {
		return portOut.getGlucoseListByDateBetween(startDate, endDate);
	}
}
