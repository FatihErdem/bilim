package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.Gallery;
import com.decimatech.bilim.model.VisitReport;

import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import java.util.List;

public class TableDataConverter {


    public static List<VisitReport> convertGalleryToDataTable(List<VisitReport> visitReportsList, MongoTemplate mongoTemplate) {


        for (VisitReport visitReport : visitReportsList) {
            Gallery gallery = mongoTemplate.findOne(query(where("galleryName").is(visitReport.getGalleryName())), Gallery.class);
            visitReport.setGalleryId(gallery.getGalleryId());
        }

        return visitReportsList;
    }
}
