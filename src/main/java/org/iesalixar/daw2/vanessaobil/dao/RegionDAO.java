package org.iesalixar.daw2.vanessaobil.dao;

import org.iesalixar.daw2.vanessaobil.entity.Region;
import java.sql.SQLException;
import java.util.List;


public interface RegionDAO {


    List<Region> listAllRegions() throws SQLException;

    void insertRegion(Region region) throws SQLException;

    void updateRegion(Region region) throws SQLException;

    void deleteRegion(int id) throws SQLException;

    Region getRegionById(int id) throws SQLException;

    boolean existsRegionByCode(String code) throws SQLException;

    boolean existsRegionByCodeAndNotId(String code, int id) throws SQLException;
}