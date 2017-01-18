package com.batchjobs.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.batchjobs.entity.RawSpeedData;
import com.batchjobs.repository.SiteSpeedRepository;

public class SiteSpeedWriter implements ItemWriter<RawSpeedData> {

	@Autowired
	private SiteSpeedRepository siteSpeedRepository;
	
	public void write(List<? extends RawSpeedData> rawSpeedDataList) throws Exception {
			siteSpeedRepository.save(rawSpeedDataList);
	}
}
