package ru.ezhov.test.help;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedTest {
           //до 1.6 включительно
            private void update(Storage storage, File file, Connection connection) throws SQLException {
                PreparedStatement preparedStatementOne = null;
                PreparedStatement preparedStatementTwo = null;
                try {
                    connection.setAutoCommit(false);
                    preparedStatementOne = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");
                    preparedStatementOne.setLong(1, file.getStorageId());
                    preparedStatementOne.setLong(2, file.getId());
                    boolean resFile = preparedStatementOne.execute();
                    System.out.println("Update table File " + resFile);
                    preparedStatementTwo = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");
                    preparedStatementTwo.setLong(1, storage.getStorageSize());
                    preparedStatementTwo.setLong(2, storage.getId());
                    boolean resStorage = preparedStatementTwo.execute();
                    System.out.println("Update table Storage " + resStorage);
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                } finally {
                    if (preparedStatementOne != null) {
                        preparedStatementOne.close();
                    }

                    if (preparedStatementTwo != null) {
                        preparedStatementTwo.close();
                    }
                    connection.setAutoCommit(true);
                }
            }

            //после 1.7 включительно
            private void update(Storage storage, File file, Connection connection) throws SQLException {
                connection.setAutoCommit(false);
                try (PreparedStatement preparedStatementOne = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");) {
                    preparedStatementOne.setLong(1, file.getStorageId());
                    preparedStatementOne.setLong(2, file.getId());
                    boolean resFile = preparedStatementOne.execute();
                    System.out.println("Update table File " + resFile);
                    try (PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");) {
                        preparedStatementTwo.setLong(1, storage.getStorageSize());
                        preparedStatementTwo.setLong(2, storage.getId());
                        boolean resStorage = preparedStatementTwo.execute();
                        System.out.println("Update table Storage " + resStorage);
                    }
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                } finally {
                    connection.setAutoCommit(true);
                }
            }
}
