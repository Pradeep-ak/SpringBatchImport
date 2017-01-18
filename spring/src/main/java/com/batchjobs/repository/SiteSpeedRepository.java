package com.batchjobs.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.batchjobs.entity.RawSpeedData;

public interface SiteSpeedRepository extends CassandraRepository<RawSpeedData> {
	@Query("SELECT * FROM SITE_SPEED WHERE date=?0")
	Iterable<RawSpeedData> findByUser(String user);
	
}
