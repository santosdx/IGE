package com.dane.ige.modelo.connection;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Class that contains methods for connecting to the database.
 *
 * @author srojasm
 */
public class ConexionBd {

    private static String jndi_Sid_Alta_Disponibilidad = "java:/SidAltaDispDS";
    private static String jndi_Sid_Produccion = "java:/sidDS1";
    private static String jndi_Sid_Desarrollo = "java:/sidDS0";
    //private static String jndi_Sid_Desarrollo = "java:/IgeDs-Desarrollo";
    private Connection conexion = null;

    /**
     * Constructor de clase que permite crear un objeto de conexión directo a a
     * la base de datos de Producción.
     */
    public ConexionBd() {
        //generateConnection(getJndi_Sid_Produccion());
        //generateConnection(getJndi_Sid_Alta_Disponibilidad());
        //generateConnection(getJndi_Sid_Desarrollo());
    }

    /**
     * Constructor de clase que permite crear un objeto de conexión a partir de
     * un JNDI especifico.
     *
     * @param jndi
     */
    public ConexionBd(String jndi) {
        generateConnection(jndi);
    }

    /**
     * @return the jndi_Sid_Produccion
     */
    public String getJndi_Sid_Produccion() {
        return jndi_Sid_Produccion;
    }

    /**
     * @return the jndi_Sid_Desarrollo
     */
    public static String getJndi_Sid_Desarrollo() {
        return jndi_Sid_Desarrollo;
    }

    /**
     * @return the jndi_Sid_Alta_Disponibilidad
     */
    public static String getJndi_Sid_Alta_Disponibilidad() {
        return jndi_Sid_Alta_Disponibilidad;
    }

    /**
     * Método que retorna la conexión de la base de datos generada al instanciar
     * la clase.
     *
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    private void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Método que genera la conexión de la base de datos de acuerdo al JNDI
     * seleccionado y enviado como parámetro.
     *
     * @param theJndi Jndi que contiene los datos de conexión.
     */
    public void generateConnection(String theJndi) {

        Context ctx = null;
        DataSource dataSource = null;

        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(theJndi);

            if (dataSource != null) {
                conexion = (Connection) dataSource.getConnection();
                consoleOutput.append("Datasource found.");
                setMessage("Datasource found.");
                setStatus(true);
                setConexion(conexion);
            } else {
                consoleOutput.append("Failed to lookup datasource.");
                setMessage("Failed to lookup datasource.");
                setStatus(false);
            }
        } catch (Exception ex) {
            consoleOutput.append("Cannot get connection: ");
            consoleOutput.append(ex);
            setMessage("Cannot get connection: " + ex);
            setStatus(false);
        }
        setConsoleOutput(consoleOutput);
    }

    private boolean status = false;
    private String message = "";
    private StringBuffer consoleOutput = new StringBuffer();

    /**
     * Método que retorna el estado de la conexión de base de datos.
     *
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    private void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    private void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the consoleOutput
     */
    public StringBuffer getConsoleOutput() {
        return consoleOutput;
    }

    /**
     * @param consoleOutput the consoleOutput to set
     */
    private void setConsoleOutput(StringBuffer consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    /**
     * M�todo que permite ejecutar una sentencia SQL recibiento como parametro
     * el String de la consulta y el atributa a retornar.
     *
     * @param elSql
     * @param atributo
     * @return String
     */
    public static String ejecutarSql(String elSql, String atributo) {

        PreparedStatement theJudgment = null;
        ResultSet resultQuery = null;
        Connection myConec = null;
        String result = "";

        Context ctx = null;
        DataSource dataSource = null;

        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(jndi_Sid_Produccion);

            if (dataSource != null) {
                myConec = (Connection) dataSource.getConnection();
                try {
                    theJudgment = myConec.prepareStatement(elSql);
                    resultQuery = theJudgment.executeQuery();
                    if (!resultQuery.next()) {

                    } else {
                        result = resultQuery.getString(atributo);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (resultQuery != null) {
                        resultQuery.close();
                    }
                    if (theJudgment != null) {
                        theJudgment.close();
                    }
                    if (myConec != null) {
                        myConec.close();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void closeConexionBd() {
        try {
            getConexion().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
