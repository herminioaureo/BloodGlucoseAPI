package com.bloodglucose.api.port.out;

import com.bloodglucose.api.adapter.out.GlucoseRepositoryAdapterOut;
import com.bloodglucose.api.core.dto.GlucoseRecord;
import com.bloodglucose.api.core.entity.GlucoseEntity;
import com.bloodglucose.api.core.util.GlucoseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GlucosePortOut {

    @Autowired
    private GlucoseConverter converter;

    @Autowired
    private GlucoseRepositoryAdapterOut repository;

    public void saveGlucose(GlucoseRecord glucose) throws Exception {
        GlucoseEntity entity = converter.convertToEntity(glucose);
        entity.setUuid(UUID.randomUUID().toString());

        repository.save(entity);
    }

    public List<GlucoseRecord> getGlucoseList() {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findAll());

        return recordList;
    }

    public List<GlucoseRecord> getGlucoseListByMeal(String meal) {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByMeal(meal));

        return recordList;
    }

    public List<GlucoseRecord> getGlucoseListByDate(Date date) {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDate(date));

        return recordList;
    }

    public List<GlucoseRecord> getGlucoseListByDateBetween(Date startDate, Date endDate) {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDateBetween(startDate, endDate));

        return recordList;
    }

    public List<GlucoseRecord> getGlucoseListByDateAndMeal(Date date, String meal) {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDateAndMeal(date, meal));

        return recordList;
    }

    public List<GlucoseRecord> getGlucoseListByDateBetweenAndMeal(Date startDate, Date endDate, String meal) {
        List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDateBetweenAndMeal(startDate, endDate, meal));

        return recordList;
    }

    public void deleteById(Long id) throws Exception {
        repository.deleteById(id);
    }
}
