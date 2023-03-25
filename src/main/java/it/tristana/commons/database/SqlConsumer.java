package it.tristana.commons.database;

import java.sql.ResultSet;

public interface SqlConsumer {

	void accept(ResultSet resultSet);
}
