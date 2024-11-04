package com.carbon.footprint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carbon.footprint.model.CarbonFootprint;

@Repository
public interface CarbonFootprintRepository extends JpaRepository<CarbonFootprint, Long> {
	
	CarbonFootprint findByCarbonFootprintId(Long carbonFootprintId);
	List<CarbonFootprint> findByUserId(String userId);
	
	void deleteByUserId(String userId);
	
	// Check if a record exists for the given userId, footprintMonth, and footprintYear
	@Query("SELECT cf FROM CarbonFootprint cf "
			+ "WHERE cf.userId = :userId AND "
			+ "cf.footprintMonth = :month AND "
			+ "cf.footprintYear = :year")
    Optional<CarbonFootprint> findByUserIdAndMonthAndYear(
    		@Param("userId") String userId, 
    		@Param("month") String month, 
    		@Param("year") int year);
	
	
	
	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
	       "cf.transportation, cf.electricity, cf.lpg, " +
	       "cf.shipping, cf.airConditioner) " +
	       "FROM CarbonFootprint cf " +
	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprint>> findSumsByMonthAndYear(
	        @Param("month") String month,
	        @Param("year") int year);

	
	
	@Query("SELECT cf FROM CarbonFootprint cf " +
		       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprint>> findAllSumsByMonthAndYear(
	        @Param("month") String month,
	        @Param("year") int year);
//	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
//	       "cf.transportation, cf.electricity, cf.lpg, " +
//	       "cf.shipping, cf.airConditioner) " +
//	       "FROM CarbonFootprint cf " +
//	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year AND cf.userId = :userId")
//	Optional<CarbonFootprint> findSumsByUserIdAndMonthAndYear(
//	        @Param("userId") String userId,
//	        @Param("month") String month,
//	        @Param("year") int year);

	
//	
//	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
//	       "SUM(cf.transportation), SUM(cf.electricity), SUM(cf.lpg), " +
//	       "SUM(cf.shipping), SUM(cf.airConditioner)) " +
//	       "FROM CarbonFootprint cf " +
//	       "WHERE cf.userId = :userId")
//	Optional<CarbonFootprint> findSumsByUserId(@Param("userId") String userId);

}