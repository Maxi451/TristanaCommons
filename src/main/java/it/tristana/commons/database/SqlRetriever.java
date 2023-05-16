package it.tristana.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlRetriever<T> {

	T apply(ResultSet resultSet) throws SQLException;
}
